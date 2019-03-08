package top.hiccup.jdk.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile关键字可见性测试：
 * 1、修饰普通变量可以保证变量在线程间的可见性
 * 2、修饰数组，如果数组元素被修改，可以保证数组元素的线程可见性
 * 3、修饰List等，添加元素以及更新元素的字段都能保证线程可见性
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

    private static Object[] arr = new Object[10];
//    private static volatile Object[] arr = new Object[10];
    private static void test4() {
        arr[5] = "kkk";
        new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            arr[5] = 1;
        }).start();
        while ("kkk".equals(arr[5])) {

        }
        System.out.println("test4 end");
    }

    private static class Base {
        private String name;
    }
    private static volatile List<Base> list = new ArrayList<>();
//    private static volatile List<Base> list = new ArrayList<>();
    private static void test5() {
        list.add(new Base());
        new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            list.get(0).name = "haha";
            list.add(new Base());
        }).start();
//        while (list.get(0).name == null) {
        while (list.size() == 1) {

        }
        System.out.println("test5 end");
    }


    public static void main(String[] args) {
        // test1有volatile修饰，线程能直接结束
        new Thread(()->{test1();}).start();
        // test2线程一般需要等很久（CPU的cache刷回主内存，不同JVM实现可能不一样）
//        new Thread(()->{test2();}).start();
        // test3的变量t3没有被volatile修饰，但是线程也能很快执行结束
        // 原因是println方法带有synchronized，会导致t3在进入同步代码块时保证线程的可见性
        new Thread(()->{test3();}).start();

        // 测试数组和List等容器
//        new Thread(()->test4()).start();
        new Thread(()->test5()).start();
    }
}
