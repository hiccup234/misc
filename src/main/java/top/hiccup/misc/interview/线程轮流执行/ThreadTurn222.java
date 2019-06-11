package top.hiccup.misc.interview.线程轮流执行;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 两把锁实现（其实还是依靠turn来实现交替，如果不依靠turn，则会有死锁风险）
 *
 * 注意：仅仅为了验证两把锁实现线程切换执行，现实中不能这么写
 *
 * @author wenhy
 * @date 2019/5/29
 */
public class ThreadTurn222 {

    private static volatile boolean turn = false;

    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();

        AtomicInteger idx1 = new AtomicInteger(0);
        AtomicInteger idx2 = new AtomicInteger(0);

        char[] chars1 = new char[]{'a', 'e', 'i', 'o', 'u'};
        char[] chars2 = new char[]{'1', '2', '3', '4', '5'};

        new Thread(() -> {
            while (true) {
                if (turn) {
                    synchronized (lock1) {
                        System.out.println(chars1[idx1.get()]);
                        idx1.set((idx1.get() + 1) % chars1.length);
                        try {
                            Thread.sleep(1000);
                            synchronized (lock2) {
                                lock2.notifyAll();
                            }
                            turn = false;
                            lock1.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "t1").start();


        new Thread(() -> {
            while (true) {
                if (!turn) {
                    synchronized (lock2) {
                        System.out.println(chars2[idx2.get()]);
                        idx2.set((idx2.get() + 1) % chars2.length);
                        try {
                            Thread.sleep(1000);
                            synchronized (lock1) {
                                lock1.notifyAll();
                            }
                            turn = true;
                            lock2.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "t2").start();
    }
}
