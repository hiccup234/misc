package top.hiccup.jdk.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * 【并发】
 * Concurrency，即一段时间内多个任务在执行，但不一定是同时在执行，它们可能是交替在运行，也有可能是串行运行的。
 * <p>
 * 【并行】
 * Parallelism，多个任务在同时执行，单核CPU不会有并行操作，因为一个CPU一次只能执行一条指令，并行操作只存在于多核CPU中。
 *
 * @author wenhy
 * @date 2017/12/23
 */
public class ThreadMain {

    /**
     * volatile关键字保证线程间的可见性
     */
    private static volatile List list = new ArrayList();

    // 去掉volatile则不能保证t2被及时通知到
//    private static List list = new ArrayList();

    public static void main(String[] args) {
        final ThreadMain list1 = new ThreadMain();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        list.add("test" + i);
                        System.out.println("当前线程：" + Thread.currentThread().getName() + "添加了一个元素..");
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (list.size() == 5) {
                        System.out.println("当前线程收到通知：" + Thread.currentThread().getName() + " list size = " + list.size() + " 线程停止..");
                        throw new RuntimeException();
                    }
                }
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
