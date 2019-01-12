package top.hiccup.jdk.concurrent.thread;

/**
 * 线程的sleep方法与yield方法对比
 *
 * 【sleep】使当前线程休眠指定时间
 * 【yield】让出当前线程得到的 CPU 时间片，使正在运行中的线程重新变成就绪状态，并重新竞争 CPU 的调度权
 *
 * 1、yield, sleep 都能暂停当前线程，sleep 可以指定具体休眠的时间，而 yield 则依赖 CPU 的时间片划分。
 * 2、yield, sleep 两个在暂停过程中，如已经持有锁，都不会释放锁。
 * 3、yield 不能被中断，而 sleep 则可以接受中断。
 *
 * @author wenhy
 * @date 2019/1/12
 */
public class SleepAndYieldTest {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            for (int i = 0; i <= 100; i++) {
                System.out.println(Thread.currentThread().getName() + "--" + i);
                if (i % 20 == 0) {
                    // 注释掉yield方法就可对比效果
                    Thread.yield();
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        };
        new Thread(runnable, "t1").start();
        new Thread(runnable, "t2").start();
    }
}
