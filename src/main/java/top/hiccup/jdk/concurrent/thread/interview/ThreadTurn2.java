package top.hiccup.jdk.concurrent.thread.interview;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 利用ReentractLock的Condition来实现
 *
 * @author wenhy
 * @date 2019/5/29
 */
public class ThreadTurn2 {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        AtomicInteger idx1 = new AtomicInteger(0);
        AtomicInteger idx2 = new AtomicInteger(0);

        char[] chars1 = new char[]{'a', 'e', 'i', 'o', 'u'};
        char[] chars2 = new char[]{'1', '2', '3', '4', '5'};

        new Thread(() -> {
            while (true) {
                lock.lock();
                System.out.println(chars1[idx1.get()]);
                idx1.set((idx1.get() + 1) % chars1.length);
                try {
                    Thread.sleep(1000);
                    condition.signalAll();
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                lock.lock();
                System.out.println(chars2[idx2.get()]);
                idx2.set((idx2.get() + 1) % chars2.length);
                try {
                    Thread.sleep(1000);
                    condition.signalAll();
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }, "t2").start();
    }
}
