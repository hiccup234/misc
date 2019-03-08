package top.hiccup.jdk.concurrent.thread;

/**
 * 【sleep】使当前线程休眠指定时间，不会释放锁，线程是变成阻塞状态
 * 【yield】主动让出当前线程的CPU时间片，使正在运行中的线程重新变成就绪状态，并重新竞争CPU的调度权，避免一个线程长时间占有CPU资源，也不释放锁
 * <p>
 * 1、yield, sleep 都能暂停当前线程，sleep可以指定具体休眠的时间，而yield则依赖CPU的时间片划分。
 * 2、yield, sleep 两个在暂停过程中，如已经持有锁，都不会释放锁。（wait方法是会放弃当前锁的）
 * 3、yield 不能被中断，而 sleep 则可以接受中断。
 * 4、sleep(0)有类似yield的功能。
 *
 * @author wenhy
 * @date 2019/1/12
 */
public class SleepAndYieldTest {
    public static void main(String[] args) {
        Object obj = new Object();
        Runnable runnable = () -> {
            synchronized (obj) {
                for (int i = 0; i <= 100; i++) {
                    System.out.println(Thread.currentThread().getName() + "--" + i);
                    if (i % 20 == 0) {
                        // 注释掉yield方法就可对比效果
//                    Thread.yield();
                        try {
                            // sleep方法不会释放锁，所以当放在同步块里执行时，打印结果不会出现线程间的切换
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        new Thread(runnable, "t1").start();
        new Thread(runnable, "t2").start();
    }
}
