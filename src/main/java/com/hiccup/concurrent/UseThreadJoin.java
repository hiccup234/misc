package com.hiccup.concurrent;

/**
 * Created by wenhy on 2018/1/7.
 */
public class UseThreadJoin {

    /**
     * 使用Thread自带的fork/join方法示例
     */
    private static class Task implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName()+"执行结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Task(), "t1");
        t1.start();
        try {
            t1.join(1000);//方法中调用了wait方法，需要获得对象上的锁然后再等待1000ms
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"执行结束");
    }
}