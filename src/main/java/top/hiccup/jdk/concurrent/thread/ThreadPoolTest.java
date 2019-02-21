package top.hiccup.jdk.concurrent.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池：
 * <p>
 * 【线程池工作流程】
 * 1、如果线程池中的线程小于corePoolSize时就直接创建新线程来执行任务。
 * 2、如果线程池中的线程大于corePoolSize时就会暂时把任务存储到工作队列workQueue中等待执行。
 * 3、如果工作队列workQueue也满时，当线程数小于最大线程池数maximumPoolSize时就会创建新线程来处理，
 * 而线程数大于等于最大线程池数maximumPoolSize时就会执行拒绝策略。
 *
 * 【两种工作队列】
 * 1）无界队列LinkedBlockingQueue，继续添加任务到阻塞队列中等待执行，因为LinkedBlockingQueue可以近乎认为是一个无穷大的队列，可以无限存放任务
 * 2）有界队列比如ArrayBlockingQueue，任务首先会被添加到ArrayBlockingQueue中，ArrayBlockingQueue满了，会根据maximumPoolSize的值增加线程数量，
 * 如果增加了线程数量还是处理不过来，那么则会使用拒绝策略RejectedExecutionHandler处理满了的任务，默认是AbortPolicy
 *
 * 【线程池拒绝策略】RejectedExecutionHandler
 * 1、AbortPolicy：拒绝并抛出异常
 * 2、CallerRunsPolicy：调用者执行，会导致主线程性能变慢
 * 3、DiscardPolicy：直接丢弃，什么也不做
 * 4、DiscardOldestPolicy：丢弃缓冲队列里最老的一个，并执行新的任务（放入队列）
 *
 * @author wenhy
 * @date 2019/1/9
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        // 1、常用自定义线程池
        ExecutorService threadPool1 = new ThreadPoolExecutor(8, 8 * 8,
                5 * 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r);
                    }
                }, new ThreadPoolExecutor.CallerRunsPolicy());

        // 2、单线程线程池，工作队列为LinkedBlockingQueue，不限制大小
        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();

        // 3、固定大小线程池，工作队列为LinkedBlockingQueue，
        ExecutorService threadPool3 = Executors.newFixedThreadPool(8);

        // 4、缓冲线程池，核心线程数为0，工作队列为SynchronousQueue（直接提交队列，不会阻塞线程）
        ExecutorService threadPool4 = Executors.newCachedThreadPool();

        // 5、调度线程池，按一定的周期执行任务（定时任务），工作队列为DelayedWorkQueue
        ExecutorService threadPool5 = Executors.newScheduledThreadPool(8);

    }
}
