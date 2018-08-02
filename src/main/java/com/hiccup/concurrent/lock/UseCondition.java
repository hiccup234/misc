package com.hiccup.concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wenhy on 2018/1/8.
 */
public class UseCondition {

    /**
     * Condition使用示例：类似wait notify notifyAll
     */
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void method1(){
        try {
            lock.lock();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "进入等待状态..");
            Thread.sleep(3000);
            System.out.println("当前线程：" + Thread.currentThread().getName() + "释放锁..");
            condition.await();	// Object wait
            System.out.println("当前线程：" + Thread.currentThread().getName() +"继续执行...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void method2(){
        try {
            lock.lock();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "进入..");
            Thread.sleep(3000);
            System.out.println("当前线程：" + Thread.currentThread().getName() + "发出唤醒..");
            condition.signal();		//Object notify   //signal也不会释放锁
//            condition.signalAll();    //Object notifyAll
            System.out.println("当前线程：" + Thread.currentThread().getName() + "继续执行..");
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("当前线程：" + Thread.currentThread().getName() + "释放锁..");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        final UseCondition uc = new UseCondition();
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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
    }

}
