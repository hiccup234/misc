package top.hiccup.jdk.concurrent.thread;

/**
 * 如何停止一个线程？
 *
 * 1.stop方法：stop会释放目标线程的锁并强制终止目标线程，可能会造成数据一致性问题（类似kill - 9），已被标记为@Deprecated
 *
 * 2.利用volatile变量来控制目标线程的运行
 *
 * 3.interrupt方法：利用线程中断来停止目标线程（kill - 15）
 *
 * @author wenhy
 * @date 2018/9/22
 */
public class StopThreadTest {

    public static void test1() {
        Thread t = new Thread(() -> {
            for (int i=0; i<10000000; i++) {
                System.out.println(i);
            }
        });
        t.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.stop();
    }

    private static volatile boolean stop = false;
    public static void test2() {
        Thread t = new Thread(() -> {
            for (int i=1; i<=10000000; i++) {
                System.out.println(i);
                if (stop && i % 100000 == 0) {
                    // 如果处理到10W就可以响应停止信号了
                    System.out.println("子线程停止");
                    return ;
                }
            }
        });
        t.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop = true;
        System.out.println("主线程停止");
    }

    public static void test3() {
        Thread t = new Thread(() -> {
            for (int i=1; i<=10000000; i++) {
                System.out.println(i);
//                if (Thread.interrupted() && i % 100000 == 0) {
                if (Thread.currentThread().isInterrupted() && i % 100000 == 0) {
                    // 如果处理到10W就可以响应停止信号了
                    System.out.println("子线程停止");
                    return ;
                }
            }
        });
        t.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
    }

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }
}
