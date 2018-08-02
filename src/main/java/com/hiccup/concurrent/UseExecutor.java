package com.hiccup.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wenhy on 2018/1/6.
 */
public class UseExecutor {

    /**
     * JDK多线程框架Executors及自定义线程池使用示例
     */
    public static void main(String[] args) {
        //固定数量线程池   ThreadPoolExecutor
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        //单个线程的线程池
        executorService = Executors.newSingleThreadExecutor();
        //没有数量上限的线程池
        executorService = Executors.newCachedThreadPool();


        /**
         * 自定义线程池
         */
        //在使用有界队列时，若有新的任务需要执行，如果线程池实际线程数小于corePoolSize，则优先创建线程，
        //若大于corePoolSize，则会将任务加入队列，
        //若队列已满，则在总线程数不大于maximumPoolSize的前提下，创建新的线程，
        //若线程数大于maximumPoolSize，则执行拒绝策略，或其他自定义方式。
        ExecutorService pool = new ThreadPoolExecutor(
                1,2,
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(3)     //指定一种队列 （有界队列）
                //new LinkedBlockingQueue<Runnable>()
                , new MyRejected()
                //, new DiscardOldestPolicy()
        );

        MyTask mt1 = new MyTask(1, "任务1");
        MyTask mt2 = new MyTask(2, "任务2");
        MyTask mt3 = new MyTask(3, "任务3");
        MyTask mt4 = new MyTask(4, "任务4");
        MyTask mt5 = new MyTask(5, "任务5");
        MyTask mt6 = new MyTask(6, "任务6");

        pool.execute(mt1);
        pool.execute(mt2);
        pool.execute(mt3);
        pool.execute(mt4);
        pool.execute(mt5);
        pool.execute(mt6);
        Future<?> f = pool.submit(mt6);
        System.out.println("submit返回："+f);
        /**
         * submit与execute方法的区别：1、submit可以接收Callable对象   2、submit有返回值
         * Callable接口有返回值   Runnable接口没有返回值
         */

        pool.shutdown();

    }
}

class MyTask implements Runnable {

    private Integer taskId;
    private String taskName;

    public MyTask(Integer taskId, String taskName) {
        this.taskId = taskId;
        this.taskName = taskName;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        try {
            System.out.println("run taskId =" + this.taskId);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return new StringBuffer("任务ID：").append(taskId).append(" 任务名称：").append(taskName).toString();
    }
}

class MyRejected implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
        System.out.println("当前被拒绝任务为：" + task.toString());
    }
}