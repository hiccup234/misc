package top.hiccup.jdk.concurrent.thread;

import java.util.Set;

/**
 * 启动默认线程
 *
 * @author wenhy
 * @date 2021/6/25
 */
public class ActiveCountThreadTest {

    public static void main(String[] args) {
        System.out.println("hello world");
        int activeCount = Thread.activeCount();
        System.out.println("线程活跃数：" + activeCount);
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread : threadSet) {
            System.out.println("线程" + thread.getId() + ":" + thread.getName());
        }
    }
}
