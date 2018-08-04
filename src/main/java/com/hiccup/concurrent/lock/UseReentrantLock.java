package com.hiccup.concurrent.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock可重入锁使用示例
 *
 * @author wenhy
 * @date 2018/1/8
 */
public class UseReentrantLock {

    private Lock lock = new ReentrantLock();
//    private Lock lock = new ReentrantLock(true);    //公平锁，先请求锁的线程先获得锁
    public void method1(){
        try {
            lock.lock();
            System.out.println("当前线程:" + Thread.currentThread().getName() + "进入method1..");
            Thread.sleep(1000);
            System.out.println("当前线程:" + Thread.currentThread().getName() + "退出method1..");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            lock.unlock();
        }
    }

    public void method2(){
        try {
            lock.lock();
            System.out.println("当前线程:" + Thread.currentThread().getName() + "进入method2..");
            Thread.sleep(2000);
            System.out.println("当前线程:" + Thread.currentThread().getName() + "退出method2..");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            lock.unlock();
        }
    }

    public void m1(){
        try {
            lock.lock();
            System.out.println("进入m1方法，holdCount数为：" + ((ReentrantLock)lock).getHoldCount());
            m2();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void m2(){
        try {
            lock.lock();
            System.out.println("进入m2方法，holdCount数为：" +  ((ReentrantLock)lock).getHoldCount());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final UseReentrantLock ur = new UseReentrantLock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                ur.method1();
                ur.method2();
            }
        }, "t1");

        t1.start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println(ur.lock.getQueueLength());

        //重入次数测试
        ur.m1();

    }

}
