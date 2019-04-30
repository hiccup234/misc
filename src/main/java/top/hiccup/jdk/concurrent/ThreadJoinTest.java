package top.hiccup.jdk.concurrent;

/**
 * 使用Thread的join方法示例
 *
 * Created by wenhy on 2018/1/7.
 */
public class ThreadJoinTest {

    private static class Task implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName()+"执行结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Task(), "t1");
        t1.start();
        try {
            t1.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"执行结束");
    }
}