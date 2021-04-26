package top.hiccup.jdk.concurrent.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;

/**
 * 统计JVM进程中有哪些线程：初始启动时只会有5个线程
 * 1: main
 * 2: Reference Handler     计算对象引用是否可达
 * 3: Finalizer             GC线程
 * 4: Signal Dispatcher     线程调度器
 * 5: Attach Listener       负责接收外部命令，如jstat、jmap、jstack
 * 6: Monitor Ctrl-Break    由IntellijIDEA添加
 *
 * ==================================================================
 * 1、MXBean方式
 *
 * 2、线程组ThreadGroup方式
 *
 * @author wenhy
 * @date 2019/3/30
 */
public class ThreadCountTest {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] threadIds = threadMXBean.getAllThreadIds();
        ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadIds);
        Arrays.sort(threadInfos, (t1, t2) -> (int) (t1.getThreadId() - t2.getThreadId()));
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(threadInfo.getThreadId() + ": " + threadInfo.getThreadName());
        }
        System.out.println("===================================================");

        // 先创建一个空线程组
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        while (group.getParent() != null) {
            group = group.getParent();
        }
        int count = group.activeCount();
        Thread[] threads = new Thread[count];
        group.enumerate(threads);
        Arrays.sort(threads, (t1, t2) -> (int) (t1.getId() - t2.getId()));
        for (Thread thread : threads) {
            System.out.println(thread.getId() + ": " + thread.getName());
        }
        System.out.println("总计：" + count);
    }
}
