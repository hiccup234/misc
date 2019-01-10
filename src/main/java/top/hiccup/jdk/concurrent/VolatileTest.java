package top.hiccup.jdk.concurrent;

/**
 * volatile关键字可见性测试
 *
 * @author wenhy
 * @date 2019/1/10
 */
public class VolatileTest {

    private static volatile int t1 = 0;

    private static int t2 = 0;
    
    private static int t3 = 0;

    private static void test1() {
        new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t1 = 1;
        }).start();
        while (t1 == 0) {

        }
        System.out.println("test1 end");
    }

    private static void test2() {
        new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t2 = 1;
        }).start();
        while (t2 == 0) {
        }
        System.out.println("test2 end");
    }

    private static void test3() {
        new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t3 = 1;
        }).start();
        while (t3 == 0) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (VolatileTest.class) {
                int x = t3;
            }
//            System.out.println(t3);
        }
        System.out.println("test3 end");
    }

    public static void main(String[] args) {
        // test1有volatile修饰，线程能直接结束
        new Thread(()->{test1();}).start();
        // test2线程一般需要等很久（CPU的cache刷回主内存，不同JVM实现可能不一样）
        new Thread(()->{test2();}).start();
        // test3的变量t3没有被volatile修饰，但是线程也能很快执行结束
        // 原因是println方法带有synchronized，会导致t3在进入同步代码块时保证线程的可见性
        //     public void println(int x) {
        //        synchronized (this) {
        //            print(x);
        //            newLine();
        //        }
        //    }
        new Thread(()->{test3();}).start();
    }
}
