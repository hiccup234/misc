package top.hiccup.jdk.lang.sync;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * F
 *
 * @author wenhy
 * @date 2019/1/14
 */
public class ReentrantLockTest {

    private static CountDownLatch latch;

    // 多线程情况下，加上volatile才可以保证线程可见性
    // 同理用synchronized关键字修饰也可以保证可见性
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
                // 查看unlock源码发现 setState(c);
                // 而private volatile int state;
                // volatile写会产生写读内存屏障（storeload），会导致这之前的所有共享变量（包括非volatile）都刷回主存，从而保证了可见性
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
