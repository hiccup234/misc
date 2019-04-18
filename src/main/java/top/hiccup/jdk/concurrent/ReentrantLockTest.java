package top.hiccup.jdk.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁测试：
 * =====================================================================================================================
 * synchronized 与 ReentrantLock区别？
 * 1、synchronized 是Java语言原生的关键字，依赖VM指令monitorenter和monitorexit，底层基于操作系统的互斥量mutex。
 *   ReentrantLock 是JDK的API，基于AQS，底层原理是cas，依赖于CPU的硬件支持（IA64、x86架构中汇编指令为：cmpxchg），不会导致线程的阻塞和挂起
 *                 锁竞争激烈的情况下，性能表现更好
 *
 * 2、ReentrantLock还有这些优点：
 *      a、阻塞等待可中断（带超时的获取锁尝试）
 *      b、可实现公平锁
 *      c、可绑定多个条件（Condition）
 *      d、可以判断某个线程是否在排队等待锁
 *      e、可以判断锁是否被占用，而synchronized无法直接做到（可以通过Unsafe判断对象头）
 *
 * =====================================================================================================================
 * 为什么lock.lock();和lock.unlock();能保证共享变量的线程可见性？
 *
 * 查看unlock源码发现 setState(c); 而private volatile int state;
 * volatile写会产生写读内存屏障（storeload），会导致这之前的所有共享变量（包括非volatile）都刷回主存，从而保证了可见性
 * （JDK5增强的语义，所以JUC才得以开发出来）
 *
 * =====================================================================================================================
 *
 * @author wenhy
 * @date 2019/1/14
 */
public class ReentrantLockTest {

    private static CountDownLatch latch;

    /**
     * 多线程情况下，加上volatile才可以保证线程可见性
     * 同理用synchronized关键字修饰也可以保证可见性
     */
    public static int inc = 0;

    private static void test1() {
        for (int j = 0; j < 1000000; j++) {
            // 不能保证原子性
            inc++;
        }
    }

    private static void test2() {
        for (int j = 0; j < 1000000; j++) {
            synchronized (ReentrantLockTest.class) {
                inc++;
            }
        }
    }

    private static ReentrantLock lock = new ReentrantLock();
    private static void test3() {
        for (int j = 0; j < 1000000; j++) {
            try {
                // 为什么lock和unlock也能保证线程的可见性呢？
                lock.lock();
                inc++;
            } finally {
                lock.unlock();
            }
        }
    }


    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(50);
        for (int k = 0; k < 100; k++) {
            inc = 0;
            latch = new CountDownLatch(50);
            for (int i = 0; i < 50; i++) {
                service.execute(() -> {
//                    test1();
//                    test2();
                    test3();
                    latch.countDown();
                });
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("inc = " + inc);
        }
        // 关闭线程池
        service.shutdown();
    }
}
