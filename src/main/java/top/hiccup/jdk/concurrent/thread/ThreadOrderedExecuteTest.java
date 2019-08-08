package top.hiccup.jdk.concurrent.thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多个线程轮流循环执行示例：
 *
 * 一般采用Object的wait和notifyAll即可实现线程依此轮流执行
 * 也可用ReentrantLock的Condition来改写，同时如果使用多个Condition还可以更加精细的控制
 *
 *
 * @author wenhy
 * @date 2019/4/11
 */
public class ThreadOrderedExecuteTest {
    // 线程数
    private static final int T_SIZE = 10;

    public static void main(String[] args) {
        // TODO 这里不能使用LongAdder，它的sum不是精确值
//        LongAdder adder = new LongAdder();
        final Object lock = new Object();
        AtomicInteger atomic = new AtomicInteger();
        for (int i = 0; i < T_SIZE; i++) {
            new Thread(() -> {
                while (true) {
                    synchronized (lock) {
                        // 这里内部类依赖了外部的递增变量i，且有时序要求，因为Java的闭包问题，所以只能通过线程名传入进来
                        String threadName = Thread.currentThread().getName();
                        long p = Long.valueOf(threadName.substring(1));
                        if (atomic.get() % T_SIZE == p) {
                            System.out.print(Thread.currentThread().getName() + " -> ");
                            atomic.incrementAndGet();
                            // TODO 这里要使用notifyAll，不然容易造成死锁
//                            lock.notify();
                            lock.notifyAll();
                        } else {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "t" + i).start();
        }



//        LongAdder adder = new LongAdder();
//        final ReentrantLock reentrantLock = new ReentrantLock();
//        final Condition condition = reentrantLock.newCondition();
//        for (int i = 0; i < T_SIZE; i++) {
//            new Thread(() -> {
//                while (true) {
//                    try {
//                        reentrantLock.lock();
//                        // 这里内部类依赖了外部的递增变量i，因为Java的闭包问题，所以只能通过线程名传入进来
//                        String threadName = Thread.currentThread().getName();
//                        long p = Long.valueOf(threadName.substring(1));
//                        if (adder.longValue() % T_SIZE == p) {
//                            System.out.print(Thread.currentThread().getName() + " -> ");
//                            adder.increment();
//                            // TODO 这里要使用notifyAll，不然容易造成死锁
////                            lock.notify();
//                            condition.signalAll();
//                        } else {
//                            try {
//                                condition.await();
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    } finally {
//                        reentrantLock.unlock();
//                    }
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, "t" + i).start();
//        }
    }
}
