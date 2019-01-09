package top.hiccup.jdk.concurrent.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * JDK7新推出的Fork&Join框架测试：
 *
 * ForkJoin采用了工作窃取（work-stealing）算法，若一个工作线程的任务队列为空没有任务执行时，便从其他工作线程中获取任务主动执行。
 * 为了实现工作窃取，在工作线程中维护了双端队列，窃取任务线程从队尾获取任务，被窃取任务线程从队头获取任务。
 * 这种机制充分利用线程进行并行计算，减少了线程竞争。但是当队列中只存在一个任务了时，两个线程去取反而会造成资源浪费。
 *
 * Fork/Join框架中实际执行任务的类，有以下两种实现，一般继承这两种实现类即可（类似Runnable和Callable）
 *  1、RecursiveAction：用于无结果返回的子任务。
 *  2、RecursiveTask：用于有结果返回的子任务。
 *
 * 示例：从1+2+...10亿，每个任务只能处理1000个数相加，超过1000个的自动分解成小任务并行处理；
 *      并展示了通过不使用Fork/Join和使用时的时间损耗对比。
 *
 * @author wenhy
 * @date 2019/1/7
 */
public class ForkAndJoinTest extends RecursiveTask<Long> {

    private static final long MAX = 1000000000L;
    private static final long THRESHOLD = 1000L;
    private long start;
    private long end;

    public ForkAndJoinTest(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public static void main(String[] args) {
        test();
        System.out.println("--------------------");
        testForkJoin();
    }

    private static void test() {
        System.out.println("test");
        long start = System.currentTimeMillis();
        Long sum = 0L;
        for (long i = 0L; i <= MAX; i++) {
            sum += i;
        }
        System.out.println(sum);
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    private static void testForkJoin() {
        System.out.println("testForkJoin");
        long start = System.currentTimeMillis();
        // ForkJoinPool是ForkJoin框架中的任务调度器，和ThreadPoolExecutor一样实现了自己的线程池，提供了三种调度子任务的方法：
        //  1、execute：异步执行指定任务，无返回结果；
        //  2、invoke、invokeAll：异步执行指定任务，等待完成才返回结果；
        //  3、submit：异步执行指定任务，并立即返回一个Future对象；
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Long sum = forkJoinPool.invoke(new ForkAndJoinTest(1, MAX));
        System.out.println(sum);
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    @Override
    protected Long compute() {
        long sum = 0;
        if (end - start <= THRESHOLD) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long mid = (start + end) / 2;
            ForkJoinTask task1 = new ForkAndJoinTest(start, mid);
            task1.fork();
            ForkJoinTask task2 = new ForkAndJoinTest(mid + 1, end);
            task2.fork();
            return ((Long)task1.join()).longValue() + ((Long)task2.join()).longValue();
        }
    }
}

