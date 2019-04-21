package top.hiccup.jdk.concurrent.lock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 一个简单的死锁案例（100%会发生死锁，再也不怕面试官让手写死锁了）
 *
 * 【死锁的4个必要条件】
 * 1、互斥条件 -- 获取到资源的线程（进程）具有排他性，即独占资源
 * 2、不可剥夺 -- 获取到资源的线程在使用完毕之前不能被强行夺走，即只能主动释放
 * 3、请求与保持 -- 线程已经获得了至少一个资源，还要获取其他资源，但是其他资源已经被占用，线程则会阻塞，但是对已经获取的资源保持而不释放
 * 4、循环等待 -- 与死锁有区别，如果P0~Pn形成环路，假设Pn依赖于P0和Px两个资源中一个，那么如果Px空闲后，Pn会得到执行，环路也就打破了，
 *              所以循环等待只是死锁的必要条件
 *
 * 【处理死锁的方法】
 * 1、预防死锁：通过设置某些限制条件，去破坏产生死锁的四个必要条件中的一个或几个条件，来防止死锁的发生。
 * 2、避免死锁：在资源的动态分配过程中，用某种方法去防止系统进入不安全状态，从而避免死锁的发生。
 * 3、检测死锁：允许系统在运行过程中发生死锁，但可设置检测机构及时检测死锁的发生，并采取适当措施加以清除。
 * 4、解除死锁：当检测出死锁后，便采取适当措施将进程从死锁状态中解脱出来。
 *
 * 【如何避免死锁】
 * 1、按顺序加锁：如果线程对资源按1，2，3的顺序加锁就不会出现死锁的情况
 * 2、设置阻塞时长：lock.tryLock(300, TimeUnit.SECONDS); 如果超时未获取到锁则放弃本次操作
 * 3、死锁检测：mxBean.findDeadlockedThreads()可以检测死锁的存在
 * 4、避免一个线程获取多个锁
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
        }, "t1").start();

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
        }, "t2").start();

        // 检测死锁
        checkDeadLock();
        System.out.println("main thread end");
    }


    public static void checkDeadLock() {
        ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);
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
