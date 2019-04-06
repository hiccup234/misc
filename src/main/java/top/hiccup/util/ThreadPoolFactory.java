package top.hiccup.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 公共线程池--单例
 *
 * @author wenhy
 * @date 2018/4/7
 */
public class ThreadPoolFactory {

    private static int processors = Runtime.getRuntime().availableProcessors();
    /**
     * 公共线程池，核心线程不宜太多
     */
    private static int corePoolSize = processors / 4;

    private static int maxPoolSize = processors * 8;
    /**
     * 10分钟
     */
    private static long keepAliveTime = 600;

    private static TimeUnit timeUnit = TimeUnit.SECONDS;
    /**
     * 任务队列长度
     */
    private static int maxQueueSize = processors * 20;
    /**
     * 阻塞队列
     */
    private static ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(maxQueueSize);

    /**
     * 单例模式，这里记得要隐藏构造器
     */
    private ThreadPoolFactory() {}

    /**
     * 采用静态内部类方式实现
     */
    private static class InnerThreadPoolHolder {
        private static final ThreadPoolExecutor INSTANCE = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                keepAliveTime, timeUnit, blockingQueue, new ThreadPoolExecutor.CallerRunsPolicy());
        static {
            System.out.println("thread pool started.. ");
        }
    }

    public static ThreadPoolExecutor getInstance() {
        // 第一次访问InnerThreadPoolHolder时，才会触发其加载链接和初始化，由JVM保证线程安全（不同ClassLoader下无法保证）
        return InnerThreadPoolHolder.INSTANCE;
    }

}
