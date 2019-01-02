package top.hiccup.jdk.lang.sync;

/**
 * synchronized 底层原理：
 *
 * 【传统的锁】（重量级锁）依赖于系统的同步函数，在linux上是使用mutex互斥锁，最底层实现依赖于futex，
 *      这些同步函数都涉及到用户态和内核态的切换、进程的上下文切换，成本较高。
 *      对于加了synchronized关键字但运行时并没有多线程竞争，或两个线程接近于交替执行的情况，使用传统锁机制无疑效率是会比较低的。
 *
 * 在JDK 1.6之前，synchronized只有传统的锁机制，因此给开发者留下了synchronized关键字相比于其他同步机制性能不好的印象。
 *
 * 在JDK 1.6引入了两种新型锁机制：偏向锁和轻量级锁，它们的引入是为了解决在没有多线程竞争或基本没有竞争的场景下因使用传统锁机制带来的性能开销问题。
 *
 *
 * @author wenhy
 * @date 2019/1/2
 */
public class SynchronizedKeyWordTest {

    

}
