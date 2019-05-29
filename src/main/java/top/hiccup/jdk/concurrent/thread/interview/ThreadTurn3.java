package top.hiccup.jdk.concurrent.thread.interview;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用volatile变量来控制线程的交替执行
 *
 * @author wenhy
 * @date 2019/5/29
 */
public class ThreadTurn3 {

    private static volatile boolean turn = false;

    public static void main(String[] args) {
        AtomicInteger idx1 = new AtomicInteger(0);
        AtomicInteger idx2 = new AtomicInteger(0);

        char[] chars1 = new char[]{'a', 'e', 'i', 'o', 'u'};
        char[] chars2 = new char[]{'1', '2', '3', '4', '5'};

        new Thread(() -> {
            while (true) {
                if (!turn) {
                    System.out.println(chars1[idx1.get()]);
                    idx1.set((idx1.get() + 1) % chars1.length);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    turn = true;
                }
            }
        }, "t1").start();


        new Thread(() -> {
            while (true) {
                if (turn) {
                    System.out.println(chars2[idx2.get()]);
                    idx2.set((idx2.get() + 1) % chars2.length);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    turn = false;
                }
            }
        }, "t2").start();
    }
}
