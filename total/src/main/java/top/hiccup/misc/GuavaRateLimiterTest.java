package top.hiccup.misc;

import com.google.common.util.concurrent.RateLimiter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Guava的RateLimiter使用的是令牌桶的限流算法
 *
 * @author wenhy
 * @date 2019/4/25
 */
public class GuavaRateLimiterTest {

    public static void main(String[] args) {
//        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(100));
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        // 指定每秒放10个令牌
        RateLimiter limiter = RateLimiter.create(10);
        for (int i = 1; i <= 50; i++) {
            // 请求RateLimiter, 超过permits会被阻塞
            // acquire(int permits)函数主要用于获取permits个令牌，并计算需要等待多长时间，进而挂起等待，并将该值返回
            Double acquire = null;
            if (i % 5 == 1) {
                acquire = limiter.acquire(15);
            } else if (i % 5 == 2) {
                acquire = limiter.acquire(10);
            } else if (i % 5 == 3) {
                acquire = limiter.acquire(5);
            } else if (i % 5 == 4) {
                acquire = limiter.acquire(20);
            } else {
                acquire = limiter.acquire(2);
            }
            executorService.submit(new Task("获取令牌成功，耗时：" + acquire + " 第 " + i + " 个任务执行"));
        }
        executorService.shutdown();
    }
}

class Task implements Runnable {
    String str;

    public Task(String str) {
        this.str = str;
    }

    @Override
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(sdf.format(new Date()) + " | " + Thread.currentThread().getName() + str);
    }
}
