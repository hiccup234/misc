package top.hiccup.jdk.concurrent.tool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore使用示例：
 * Semaphore就是一个信号量，它的作用是限制某段代码块的并发数。Semaphore有一个构造函数，可以传入一个int型整数n，
 * 表示某段代码最多只有n个线程可以访问，如果超出了n，那么请等待，等到某个线程执行完毕这段代码块，下一个线程再进入。
 * 由此可以看出如果Semaphore构造函数中传入的int型整数n=1，相当于变成了一个synchronized了。
 *
 * @author wenhy
 * @date 2018/1/8
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        // 线程池
        ExecutorService exec = Executors.newCachedThreadPool();
        // 只能5个线程同时访问
        final Semaphore semp = new Semaphore(5);
        // 模拟20个客户端访问
        for (int index = 0; index < 20; index++) {
            final int NO = index;
            exec.execute(()-> {
                try {
                    // 获取许可
                    semp.acquire();
                    System.out.println("Accessing: " + NO);
                    //模拟实际业务逻辑
                    Thread.sleep((long) (Math.random() * 10000));
                    // 访问完后，释放
                    semp.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(semp.getQueueLength());
        // 退出线程池
        exec.shutdown();
    }
}
