package com.hiccup.jdk.concurrent;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wenhy on 2018/1/7.
 */
public class UseCyclicBarrier {

    /**
     * 使用CyclicBarrier的场景：多个运动员准备就绪后就开始跑
     * 有点类似Thread的fork/join方法
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(3);  // 如果改成4前面的线程就会一直阻塞
        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.submit(new Runner(barrier, "zhangsan"));
        executor.submit(new Runner(barrier, "lisi"));
        executor.submit(new Runner(barrier, "wangwu"));

        executor.shutdown();


        int x = 1;
        int y = 5;
        int z = 7;
        System.out.println("String" + x + y + z);
        System.out.println(x + y + z + "String");
    }
}

class Runner implements Runnable {
    private CyclicBarrier barrier;
    private String name;

    public Runner(CyclicBarrier barrier, String name) {
        this.barrier = barrier;
        this.name = name;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(1000 * (new Random(47)).nextInt(5));
            System.out.println(name + " Ready!");
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(name + " Go!!");
    }
}