package top.hiccup.jdk.concurrent;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

/**
 * 一个简单的死锁案例（100%会发生死锁，再也不怕面试官让手写死锁了）
 *
 * 如何避免死锁：
 * 1、按顺序加锁：如果线程对资源按1，2，3的顺序加锁就不会出现死锁的情况
 * 2、设置阻塞时长：lock.tryLock(300, TimeUnit.SECONDS); 如果超时未获取到锁则放弃本次操作
 * 3、死锁检测：mxBean.findDeadlockedThreads()可以检测死锁的存在
 *
 * ## 可以通过jstack来查看线程栈找出死锁（工具会自动帮忙分析）
 *
 * @author wenhy
 * @date 2019/1/9
 */
public class DeadLockTest {

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    private static volatile boolean flag1 = false;
    private static volatile boolean flag2 = false;

    public static void main(String[] args) {
        checkDeadLock();

        new Thread(() -> {
            synchronized (lock1) {
                flag1 = true;
                System.out.println("thread1 acquired lock1");
//                try {
//                    Thread.sleep(200);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                while (!flag2) {
                    // 无限循环，等待thread2获取到lock2后再继续往下执行（相比使用Thread.sleep(200)在理论上是100%会出现死锁）
                }
                System.out.println("thread1 try to acquire lock2");
                synchronized (lock2) {
                    System.out.println("thread1 acquired lock2");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (lock2) {
                flag2 = true;
                System.out.println("thread2 acquired lock2");
//                try {
//                    Thread.sleep(200);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                while (!flag1) {

                }
                System.out.println("thread2 try to acquire lock1");
                synchronized (lock1) {
                    System.out.println("thread2 acquired lock1");
                }
            }
        }).start();

        System.out.println("main thread end");
    }


    public static void checkDeadLock() {
        ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
        java.util.concurrent.ScheduledExecutorService scheduled = java.util.concurrent.Executors.newScheduledThreadPool(1);
        // 初始等待5秒，每隔10秒检测一次
        scheduled.scheduleAtFixedRate(()->{
            long[] threadIds = mxBean.findDeadlockedThreads();
            if (threadIds != null) {
                System.out.println("检测到死锁线程：");
                ThreadInfo[] threadInfos = mxBean.getThreadInfo(threadIds);
                for (ThreadInfo info : threadInfos) {
                    System.out.println(info.getThreadId() + "：" + info.getThreadName());
                }
            }
        }, 5L, 10L, TimeUnit.SECONDS);
    }
}
