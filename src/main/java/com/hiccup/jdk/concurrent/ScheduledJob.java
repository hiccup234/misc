package com.hiccup.jdk.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wenhy on 2018/1/7.
 */
public class ScheduledJob {

    /**
     * 定时任务器（Timer）
     */
    private static class Task implements Runnable {
        private static AtomicInteger count = new AtomicInteger(0);
        @Override
        public void run() {
            System.out.println("job running..." + count.getAndIncrement());
        }
    }

    public static void main(String args[]) throws Exception {
        Task task = new Task();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        // 初始化延迟3秒钟，之后每隔一秒钟执行一次task
        ScheduledFuture<?> scheduleTask = scheduler.scheduleWithFixedDelay(task, 3, 1, TimeUnit.SECONDS);
    }
}