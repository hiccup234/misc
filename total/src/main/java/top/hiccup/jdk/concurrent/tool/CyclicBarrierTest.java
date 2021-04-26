package top.hiccup.jdk.concurrent.tool;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用CyclicBarrier的场景：多个运动员准备就绪后就开始跑
 * 有点类似Thread的fork/join方法
 *
 * CountDownLatch和CyclicBarrier的区别：
 *
 * 1、CountDownLatch：老师（主线程）上课点名，点完所有学生后才开始上课，被阻塞的线程是老师
 *      不可重复使用
 * 2、CyclicBarrier：教官（主线程）让一排士兵（工作线程）依此报数，报完后整体右转跑步前进，被阻塞的线程是士兵
 *      可重复使用
 *
 * (01) CountDownLatch的作用是允许1或N个线程等待其他线程完成执行；而CyclicBarrier则是允许N个线程相互等待。
 * (02) CountDownLatch的计数器无法被重置；CyclicBarrier的计数器可以被重置后使用，因此它被称为是循环的barrier。
 *
 * @author wenhy
 * @date 2018/1/7
 */
public class CyclicBarrierTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        // 如果改成4前面的线程就会一直阻塞
        CyclicBarrier barrier = new CyclicBarrier(3);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(new Runner(barrier, "zhangsan"));
        executor.submit(new Runner(barrier, "lisi"));
        executor.submit(new Runner(barrier, "wangwu"));
        executor.shutdown();
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
            System.out.println(name + " Prepare!");
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