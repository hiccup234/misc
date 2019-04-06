package top.hiccup.jdk.concurrent;

/**
 * synchronized 底层原理：
 *
 * 【传统的锁】（重量级锁）依赖于系统的同步函数，在linux上是使用mutex互斥锁，最底层实现依赖于futex，
 *      这些同步函数都涉及到用户态和内核态的切换、进程的上下文切换，开销成本较高。
 *      对于加了synchronized关键字但运行时并没有或较少多线程竞争，或两个线程接近于交替执行的情况，使用传统锁机制无疑效率是会比较低的。
 *
 * 在JDK 1.6之前，synchronized只有传统的锁机制，因此给开发者留下了synchronized关键字相比于其他同步机制性能不好的印象。
 *
 * 在JDK 1.6引入了两种新型锁机制：偏向锁和轻量级锁，它们的引入是为了解决在没有多线程竞争或基本没有竞争的场景下因使用传统锁机制带来的性能开销问题。
 *
 *
 * 锁升级顺序：偏向锁 --> 轻量级锁 --> 重量级锁 （分别对应了锁只被一个线程持有、不同线程交替持有锁、多线程竞争锁三种情况）
 *
 * ##JVM中的锁也是能降级的，只不过条件很苛刻
 *
 * @author wenhy
 * @date 2019/1/2
 */
public class SynchronizedKeyWordTest {

    /**
     * 对于synchronized方法而言，javac会为其生成一个ACC_SYNCHRONIZED关键字，
     * 在JVM进行方法调用时，发现调用的方法被ACC_SYNCHRONIZED修饰，则会先尝试获得锁。
     *
     * javap -v SynchronizedKeyWordTest（javap -v 看不到private修饰的方法）
     */
    public synchronized void sync1() {
        System.out.println("Hello");
    }

    /**
     * 关键字修饰的代码块在javac编译时，会生成对应的monitorenter和monitorexit指令分别对应synchronized同步块的进入和退出，
     * 注意，反编译后发现有两个monitorexit指令的原因是：为了保证抛异常的情况下也能释放锁，
     * 所以javac为同步代码块添加了一个隐式的try-finally，在finally中会调用monitorexit命令释放锁
     */
    public void sync2() {
        synchronized (this){
            System.out.println("Hello2");
        }
    }
}
