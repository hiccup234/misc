package top.hiccup.jdk.concurrent.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition使用示例：类似wait notify notifyAll
 * <p>
 * Condition除了支持await和signal之外，它更强大的地方在于：能够更加精细的控制多线程的休眠与唤醒。
 * 对于同一个锁，我们可以创建多个Condition，在不同的情况下使用不同的Condition。
 * <p>
 * 例如，假如多线程读/写同一个缓冲区：当向缓冲区中写入数据之后，唤醒"读线程"；当从缓冲区读出数据之后，唤醒"写线程"；
 * 并且当缓冲区满的时候，"写线程"需要等待；当缓冲区为空时，"读线程"需要等待。
 * 如果采用Object类中的wait(), notify(), notifyAll()实现该缓冲区，当向缓冲区写入数据之后需要唤醒"读线程"时，
 * 不可能通过notify()或notifyAll()明确的指定唤醒"读线程"，而只能通过notifyAll唤醒所有线程（但是notifyAll无法区分唤醒的线程是读线程，还是写线程）。
 * 但是通过Condition，就能明确的指定唤醒读线程。
 *
 * @author wenhy
 * @date 2018/1/8
 */
public class ConditionTest {

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void method1() {
        try {
            lock.lock();
            System.out.println("当前线程：" + Thread.currentThread().getName() + " 进入");
            Thread.sleep(3000);
            System.out.println("当前线程：" + Thread.currentThread().getName() + " await释放锁");
            // await()会立即释放锁
            condition.await();
            System.out.println("当前线程：" + Thread.currentThread().getName() + " 继续执行");
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("当前线程：" + Thread.currentThread().getName() + " 释放锁");
            lock.unlock();
        }
    }

    public void method2() {
        try {
            lock.lock();
            System.out.println("当前线程：" + Thread.currentThread().getName() + " 进入");
            Thread.sleep(3000);
            System.out.println("当前线程：" + Thread.currentThread().getName() + " 发起唤醒");
            // signal也不会释放锁，而是等当前同步快执行完成后释放
            condition.signal();
//            condition.signalAll();
            Thread.sleep(3000);
            System.out.println("当前线程：" + Thread.currentThread().getName() + " 继续执行");
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("当前线程：" + Thread.currentThread().getName() + " 释放锁");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        final ConditionTest uc = new ConditionTest();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                uc.method1();
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                uc.method2();
            }
        }, "t2");
        t1.start();
        try {
            // 保证程序先执行t1线程
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
    }
}