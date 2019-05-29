package top.hiccup.jdk.concurrent.thread.interview;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通过线程中断机制（发送信号）实现线程交替执行
 *
 * @author wenhy
 * @date 2019/5/29
 */
public class ThreadTurn4 {

    private static class ThreadHolder {
        private static Thread t1;
        private static Thread t2;
    }

    public static void main(String[] args) {
        AtomicInteger idx1 = new AtomicInteger(0);
        AtomicInteger idx2 = new AtomicInteger(0);

        char[] chars1 = new char[]{'a', 'e', 'i', 'o', 'u'};
        char[] chars2 = new char[]{'1', '2', '3', '4', '5'};

        ThreadHolder.t1 = new Thread(() -> {
            while (true) {
                while (!Thread.interrupted()) {
                    Thread.yield();
                }
                System.out.println(chars1[idx1.get()]);
                idx1.set((idx1.get() + 1) % chars1.length);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ThreadHolder.t2.interrupt();
            }
        }, "t1");

        ThreadHolder.t2 = new Thread(() -> {
            while (true) {
                while (!Thread.interrupted()) {
                    Thread.yield();
                }
                System.out.println(chars2[idx2.get()]);
                idx2.set((idx2.get() + 1) % chars2.length);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ThreadHolder.t1.interrupt();
            }
        }, "t2");
        ThreadHolder.t1.start();
        ThreadHolder.t2.start();
        ThreadHolder.t1.interrupt();
    }
}
