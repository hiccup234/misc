package top.hiccup.jdk.concurrent.thread.interview;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. 两个线程分别打印26个英文字母的元音（a,e,i,o,u）和辅音（其他），按字母序输出
 *
 * 2. 一条N个格子组成的直线道路，每次可以前进1格或2格；设计算法计算有多少种方式走到终点？
 *
 * 3. 实现一个能够生产不同类型手机（Android，iPhone）的工厂，考虑未来可能的扩展（开闭原则）
 *
 * @author wenhy
 * @date 2019/5/29
 */
public class ThreadTurn1 {

    public static void main(String[] args) {
        Object lock = new Object();

        AtomicInteger idx1 = new AtomicInteger(0);
        AtomicInteger idx2 = new AtomicInteger(0);

        char[] chars1 = new char[]{'a', 'e', 'i', 'o', 'u'};
        char[] chars2 = new char[]{'1', '2', '3', '4', '5'};

        new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    System.out.println(chars1[idx1.get()]);
                    idx1.set((idx1.get() + 1) % chars1.length);
                    try {
                        Thread.sleep(1000);
                        lock.notifyAll();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1").start();


        new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    System.out.println(chars2[idx2.get()]);
                    idx2.set((idx2.get() + 1) % chars2.length);
                    try {
                        Thread.sleep(1000);
                        lock.notifyAll();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t2").start();
    }
}
