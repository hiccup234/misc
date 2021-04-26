package top.hiccup.jdk.concurrent.tool;

import java.util.concurrent.CountDownLatch;

/**
 * 使用CountDownLacth的示例场景：常用于监听某些初始化操作，等初始化完成再通知主线程继续工作
 *
 * @author wenhy
 * @date 2018/1/7
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        // 需要countDown的次数为2
        final CountDownLatch countDown = new CountDownLatch(2);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("进入线程t1，等待其他线程处理完成...");
                    countDown.await();
                    System.out.println("t1线程继续执行...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1");

        Thread t11 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("进入线程t11，等待其他线程处理完成...");
                    // TODO 同一个countDownLatch实例可以多次调用await()，这个特性很重要
                    countDown.await();
                    System.out.println("t11线程继续执行...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t11");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("t2线程进行初始化操作...");
                    Thread.sleep(2000);
                    System.out.println("t2线程初始化完毕，通知t1线程继续...");
                    countDown.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2");

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("t3线程进行初始化操作...");
                    Thread.sleep(3000);
                    System.out.println("t3线程初始化完毕，通知t1线程继续...");
                    countDown.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t3");

        t1.start();
        t11.start();
        t2.start();
        t3.start();
    }
}