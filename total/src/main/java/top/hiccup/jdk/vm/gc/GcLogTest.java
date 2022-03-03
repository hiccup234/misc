package top.hiccup.jdk.vm.gc;

/**
 * 一般用参数：-XX:+PrintGCDetails（JDK9已经deprecated） 来打印GC时的日志，VM进程结束后打印堆信息
 *
 * GC一般分为：
 *      Minor GC（发生在新生代，比较频繁但耗时一般很短）
 *      Major GC（发生在老年代，一般会先有至少一次的Minor GC，速度比Minor GC慢10倍以上）
 *      Full GC（对整个堆上做清理工作--应该是整个JVM内存，包括元数据区，而不是堆）
 *
 *【JVM内存分配担保机制】
 * 1.谁进行空间担保？ 年代进行空间分配担保
 *
 * 2.什么是空间分配担保？ 在Minor GC前，虚拟机会检查老年代最大可用的连续空间是否大于新生代所有对象的总空间，如果大于，则此次Minor GC是安全的
 *                        如果小于，则虚拟机会查看HandlePromotionFailure设置值是否允许担保失败。如果HandlePromotionFailure=true，
 *                        那么会继续检查老年代最大可用连续空间是否大于历次晋升到老年代的对象的平均大小，如果大于，则尝试进行一次Minor GC，
 *                        但这次Minor GC依然是有风险的；如果小于或者HandlePromotionFailure=false，则改为进行一次Full GC。
 *
 * 3.为什么要进行空间担保？ 是因为新生代采用复制收集算法，假如大量对象在Minor GC后仍然存活（最极端情况为内存回收后新生代中所有对象均存活），而Survivor空间是比较小的，
 *                         这时就需要老年代进行分配担保，把Survivor无法容纳的对象放到老年代。老年代要进行空间分配担保，前提是老年代得有足够空间来容纳这些对象，
 *                         但一共有多少对象在内存回收后存活下来是不可预知的，因此只好取之前每次垃圾回收后晋升到老年代的对象大小的平均值作为参考。
 *                         使用这个平均值与老年代剩余空间进行比较，来决定是否进行Full GC来让老年代腾出更多空间。
 *
 * 日志中的GC和Full GC不是区分新生代和老年代的，如果有Full GC说明是发生了STW
 *
 * -Xms / -Xmx — 堆的初始大小 / 堆的最大大小	一般设置为相同大小
 * -Xmn — 堆中年轻代的大小
 * -XX:-DisableExplicitGC — 让System.gc()不产生任何作用
 * -XX:+PrintGCDetails — 打印GC的细节
 * -XX:+PrintGCDateStamps — 打印GC操作的时间戳
 * -XX:NewSize / XX:MaxNewSize — 设置新生代大小/新生代最大大小
 * -XX:NewRatio — 可以设置老生代和新生代的比例，默认为2，新生代占1/3
 * -XX:PrintTenuringDistribution — 设置每次新生代GC后输出幸存者乐园中对象年龄的分布
 * -XX:InitialTenuringThreshold / -XX:MaxTenuringThreshold：设置老年代阀值的初始值和最大值
 * -XX:TargetSurvivorRatio：设置幸存区的目标使用率
 *
 * @author wenhy
 * @date 2019/2/10
 */
public class GcLogTest {

    /**
     * @VM args: -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+PrintGCDetails
     */
    private static final int _1MB = 1024*1024;

    public static void main(String[] args) {
        // 会触发Full GC，并且在这之前还有一次Minor GC
//        System.gc();

        byte[] b1,b2,b3,b4;
        b1 = new byte[2*_1MB];
        b2 = new byte[2*_1MB];
        b3 = new byte[2*_1MB];
        // 这里会引起一次Minor GC
        b4 = new byte[4*_1MB];
    }
}
