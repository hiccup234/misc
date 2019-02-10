package top.hiccup.jdk.vm.gc;

/**
 * 垃圾回收算法分类
 *
 * 【引用计数】
 * 为每个对象添加一个引用计数器，为0则可以回收该对象
 * 存在问题：1.需要额外的空间来存储引用计数器
 *         2.更新操作繁琐（引用a指向对象1，现修改为指向对象2，需要原子性的把对象1的引用计数减1和对象2的引用计数加1）
 *         3.无法处理循环引用对象（Spring Framework中也只能处理单例Bean的循环引用：采用提前暴露对象引用的方式）
 *
 * 【可达性分析】
 * 从一系列GC Roots（初始的存活对象集：live set）出发，探索所有能到达的对象并加入live set，这个过程称之为标记（mark）
 * GC Roots主要包括：1.Java方法栈帧中的局部变量
 *                 2.已加载类的静态变量
 *                 3.JNI handles
 *                 4.已启动且未停止的Java线程
 * 存在问题：1.多线程环境下，其他线程可能已经更新了对象引用，可能出现线程安全问题
 *           如：a.其他线程已将引用置为null，而没有同步到GC线程，导致这个对象未能及时被回收
 *              b.其他线程将引用指向了某个即将被回收的对象，而没有同步到GC线程，如果再访问原对象地址则可能引起JVM崩溃
 * 解决方法：Stop-the-world以及安全点（safepoint），简单粗暴，直接暂停所有其他的非垃圾回收线程直到GC完成，这样就能保证线程安全
 *         GC线程向JVM发出STW请求，JVM等待所有线程到达安全点后才允许GC进行独占的工作
 * 安全点举例：1.Java程序调用本地方法时，本地方法不会再访问Java对象和调用Java方法，那么JVM的堆栈就不会发生变化
 *              这段本地代码就可以作为同一个安全点，只要不离开当前安全点，JVM便能在GC的同时运行这段本地代码
 *          2.线程状态为阻塞时，处于JVM线程调度器的控制下，也是属于安全点
 *
 *
 * 垃圾回收三种方式：
 * 1.清除（sweep），把死亡对象所占据的内存标记为空闲内存，并记录在空闲列表里，当新建对象需要分配内存时，内存管理模块就从空闲列表里查找空闲内存区域
 *   缺点是：容易造成内存碎片（JVM中的对象必须是连续分布的），且分配效率较低（需要逐个访问空闲列表）
 * 2.压缩（compact），把存活的对象挪到连续的内存空间
 *   缺点是：压缩算法性能开销大
 * 3.复制（copy），把内存区域分为两个大小相同的区域：from & to 每次只用其中一个区域，GC时把存活的对象直接复制到另一块空闲区域
 *   缺点是：内存使用效率非常低，每次只能利用内存一半的空间
 *
 * JVM采用了分代的思想：一般发生在堆区和元数据区
 * 1、GC后少量对象存活，适合复制算法
 * 2、GC后大量对象存活，适合标记清理或者标记压缩
 *
 * 垃圾收集器分类：
 * 1.新生代：串行垃圾回收器（Serial），并行垃圾回收器（多线程版本）（Parallel New），Parallel Scavenge（更注重吞吐率，不能与CMS一起使用）
 *         这三个收集器都是采用的 标记- 复制 算法
 * 2.老年代：Serial Old（标记-压缩），Parallel Old（标记-压缩），CMS（标记-清除，并发回收，STW请求较少，Java 9已移除）
 * 3.G1(Garbage First)：横跨新生代和老年代的垃圾回收器，直接将堆分成许多区域，采用“标记-压缩”算法，也是并发的垃圾回收
 * 4.ZGC(Java 11)：主要参考Azul VM的Pauseless GC及Zing VM的C4
 *
 * JVM的GC参数设置（64位的JVM只有server模式）：
 *    在client模式下采用-XX:+UseSerialGC 串行回收器（默认），-XX:+PrintGCDetails 为 “DefNew” “Tenured” “Perm”
 *    在server模式下采用-XX:+UseParNewGC 新生代并行回收器，-XX:+PrintGCDetails 为 “ParNew” “Tenured”（老年代任为串行回收）
 *                   -XX:+UseParallelGC 并行回收器（默认），-XX:+PrintGCDetails 为 “PSYoungGen” “ParOldGen”（有Full GC）
 *                   -XX:+UseParallelOldGC 老年代并行回收器，-XX:+PrintGCDetails 为 “PSYoungGen” “ParOldGen”（有Full GC）
 *                   -XX:+UseConcMarkSweepGC CMS并发回收器，-XX:+PrintGCDetails 为 “ParNew” “CMS”
 *
 *    Parallel Scavenge收集器：
 *    PS的关注点与其他收集器不同，Parallel Scavenge收集器的目标则是达到一个可控制的吞吐量（Throughput）
 *    所谓吞吐量就是CPU用于运行用户代码的时间与CPU总消耗时间的比值，即吞吐量 = 运行用户代码时间 /（运行用户代码时间 + 垃圾收集时间）
 *    虚拟机总共运行了100分钟，其中垃圾收集花掉1分钟，那吞吐量就是99%，由于与吞吐量关系密切，Parallel Scavenge收集器也经常被称为“吞吐量优先”收集器
 *    该垃圾收集器，是JVM在server模式下的默认值，使用server模式后，JVM使用Parallel Scavenge收集器（新生代）+ Parallel Old收集器（老年代）的收集器组合进行内存回收
 *
 *    Concurrent Mark Sweep简称CMS收集器：
 *    可以同应用程序线程并发执行，大大降低系统停顿时间，采用并发的标记清除算法(不能用标记压缩算法，因为并行执行，被移动的对象可能正被使用)，只作用于老年代
 *    缺点是：并发阶段由于占用cpu资源，会导致系统吞吐量降低，由于在清理阶段，应用程序还在运行，因此清理不彻底，没有一个时间点是垃圾完全清理完的状态
 *    因为和用户线程一起运行，不能在空间快满时再清理
 *
 *    -XX:ParallelGCThreads=20 限制并行回收器线程数
 *
 *    -XX:MaxGCPauseMills 设定一次gc的最大停顿时间，jvm会尽力保证每次gc在该时间范围内(会自动调整堆大小等参数)，
 *    如果设置过小，会导致gc次数增加，谨慎使用。GC停顿时间缩短是以牺牲吞吐量和新生代空间来换取的。
 *
 *    -XX:GCTimeRatio 设定应用程序线程占用的cpu时间比例(会自动调整各以达到该设定参数)，
 *    gc占用cpu默认为1，如果设置为9，表示90%时间用于应用程序线程，10%时间用于gc线程。默认99，
 *    即默认99%的时间用于应用程序线程，1%时间用于gc线程。应用程序线程的占用cpu时间比例决定了系统的吞吐量，因此值越大，吞吐量越高。
 *
 * @author wenhy
 * @date 2018/9/2
 */
public class GcTest {

    /**
     * @VM args: -XX:+PrintGCDetails -XX:+PrintGCApplicationStoppedTime -XX:+PrintSafepointStatistics -XX:+UseCountedLoopSafepoints
     */
    private static double sum;

    public static void foo() {
        // 无安全点检测的计数循环会带来长STW
        for(long k=0; k<Integer.MAX_VALUE; k++) {
            sum += Math.sqrt(k);
        }
    }

    public static void bar() {
        for(int i=0; i<200_000_000; i++) {
            new Object().hashCode();
            byte[] bytes = new byte[20*1024*1024];
        }
    }

    public static void main(String[] args) {
//        new Thread(GcTest::foo).start();
        new Thread(GcTest::bar).start();
    }


}
