package top.hiccup.jdk.concurrent.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * 【并发】
 * Concurrency，即一段时间内多个任务在执行，但不一定是同时在执行，它们可能是交替在运行，也有可能是串行运行的。
 *
 * 【并行】
 * Parallelism，多个任务在同时执行，单核CPU不会有并行操作，因为一个CPU一次只能执行一条指令，并行操作只存在于多核CPU中。
 *
 * 1、一个线程如果出现了运行时异常会怎么样？
 * 一个线程如果出现了运行时异常并且没被捕获的话，线程就会停止执行并退出，
 * 如果线程持有某个对象的监视器锁，那么这个锁也会被立即释放（反编译可见两条monitorexit指令），但是如果是Lock需要在finally块里手动释放。
 *
 * 2、如何在两个线程之间共享数据？
 * 同JVM进程中，通过线程共享对象，然后使用wait/notify/notifyAll 或者 await/signal/signalAll进行同步就可以实现共享数据了。
 *
 * 3、怎么检测一个线程是否持有对象锁（监视器）？
 * 可以利用静态方法查看当前线程是否持有obj对象的锁：Thread.holdsLock(obj);
 *
 * 4、Java中用到的线程调度算法是什么？
 * 抢占式：一个线程用完CPU之后，操作系统会根据线程优先级、线程饥饿情况等数据算出一个总的优先级并分配下一个时间片给某个线程执行。
 *
 * 【线程阻塞】
 * 1、等待阻塞 -- 调用wait()方法等（等待阻塞池）。
 * 2、同步阻塞 -- 线程获取synchronized同步锁失败时会进入同步阻塞状态（锁阻塞池）。
 * 3、其他阻塞 -- 调用线程的sleep()或join()或发出了I/O请求时，线程会进入到阻塞状态。
 *
 * 【线程状态切换】
 * Thread类中定义了NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED六种线程状态
 * 注意：JavaAPI中线程并没有RUNNING状态，所以jstack打印出来的线程栈也没有RUNNING状态的线程（见thread-status.jpg）
 *
 *
 * @author wenhy
 * @date 2017/12/23
 */
public class ThreadTest {

    /**
     * volatile关键字保证线程间的可见性
     */
    private static volatile List list = new ArrayList();

    // 去掉volatile则不能保证t2被及时通知到
//    private static List list = new ArrayList();

    public static void main(String[] args) {
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
