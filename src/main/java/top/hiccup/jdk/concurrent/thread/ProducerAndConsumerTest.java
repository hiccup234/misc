package top.hiccup.jdk.concurrent.thread;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

/**
 * 生产者消费者队列简单实现：参考ArrayBlockingQueue，可以用ReentrantLock的Condition来做优化
 *
 * @author wenhy
 * @date 2019/4/4
 */
public class ProducerAndConsumerTest {

    private static Object full = new Object();
    private static Object empty = new Object();
    private static final int MAX_LENGTH = 10;
    private static Queue queue = new ArrayDeque(MAX_LENGTH);

    public static void main(String[] args) {
        // 生产者
        new Thread(() -> {
            int product = 0;
            try {
                Random random = new Random(7);
                while (true) {
                    // 如果队列满了，则阻塞当前线程
                    synchronized (full) {
                        while (queue.size() == MAX_LENGTH) {
                            System.out.println("队列满，生产者阻塞，当前产品：" + product);
                            full.wait();
                        }
                    }
                    // 如果未满则加入队列，并通知消费者线程
                    synchronized (empty) {
                        System.out.println("生产：" + product);
                        // 超过队列长度回抛异常
                        queue.add(product++);
                        empty.notifyAll();
                    }
                    Thread.sleep(random.nextInt(500));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // 消费者
        new Thread(() -> {
            try {
                Random random = new Random(17);
                while (true) {
                    // 如果队空的，则阻塞当前线程
                    synchronized (empty) {
                        while (queue.size() == 0) {
                            System.out.println("队列空，消费者阻塞：" + queue.size());
                            empty.wait();
                        }
                    }
                    // 如果未满则加入队列，并通知消费者线程
                    synchronized (full) {
                        System.out.println("消费：" + queue.remove());
                        full.notifyAll();
                    }
                    Thread.sleep(random.nextInt(700));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
