package top.hiccup.jdk.concurrent;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import lombok.Data;

/**
 * volatile关键字可见性测试：
 * 1、修饰原始类型变量可以保证变量在线程间的可见性
 * 2、修饰对象或数组，如果数组元素被修改，可以间接保证对象成员或数组元素的线程可见性（原因见test3分析）
 * 3、修饰List等，添加元素以及更新元素的字段都能保证线程可见性
 *
 * @author wenhy
 * @date 2019/1/10
 */
public class VolatileTest {

    private static volatile int t1 = 0;

    private static int t2 = 0;
    
    /**
     * 有volatile修饰则线程能直接结束
     * 如果去掉t1的volatile，线程一般需要等很久（CPU的cache刷回主内存，不同JVM实现可能不一样）
     */
    @Test
    public void test1() {
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t1 = 1;
        }).start();
        while (t1 == 0) { }
        System.out.println("test1 end");
    }

    /**
     * 变量t2没有被volatile修饰，但是线程也能很快执行结束
     * 原因是println方法带有synchronized，会导致t2在进入同步代码块时保证线程的可见性
     */
    @Test
    public void test2() {
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t2 = 1;
        }).start();
        while (t2 == 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (VolatileTest.class) {
                int x = t2;
            }
//            System.out.println(t2);
        }
        System.out.println("test2 end");
    }

    @Data
    private class Base {
        private boolean flag = true;
    }
    private Base b = new Base();
    /**
     * 变量base加上volatile也能使线程退出循环结束
     * Q: volatile变量修饰对象或者数组时，当我们改变对象或者数组的成员的时候，也能保证线程间的可见性？
     * A: volatile变量从语义上来讲只是保证了对象或数组引用的内存地址的线程可见性，
     *    如果线程通过volatile变量访问其属性时，会提示JVM不要对这个属性做缓存拷贝（是缓存整个对象还是说仅仅是访问的这个属性呢？）
     */
    @Test
    public void test3() {
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            b.setFlag(false);
        }).start();
        while (b.isFlag()) { }
        System.out.println("test3 end");
    }

    @Data
    private class Sub {
        private Base base;
    }
    private volatile Sub s = new Sub();
    @Test
    public void test4() {
        Base base = new Base();
        // 这里设置后，子线程也能看到，不然会NPE
        s.setBase(base);

        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Base innerBase = s.getBase();
            // 这里的innerBase引用不是volatile的，为什么设置的值也能立即被其他线程看到呢？
            // 通过volatile根引用得到的引用都具有volatile特性吗？？（也就是说volatile有传播的特性？？）
            // 这里先访问一次再设值也不会引起本地缓存
            System.out.println(innerBase.isFlag());
            innerBase.setFlag(false);
            System.out.println(innerBase.isFlag());
        }).start();

        // TODO 这里有点意思，需要再研究下

        // 注意这两种访问形式，第一种并不是从volatile根引用发出的访问，无法使线程立即结束，而第二种可以
        // 因为base是在当前线程创建的，所以会直接做本地缓存？（不是，新创建的对象都是放在堆上的（逃逸分析除外，这里已经逃逸））

        // TODO 如果让主线程休眠2秒，让子线程先设置falg则程序也能立即退出，难道是因为最开始主线程没有访问属性，所以没有做本地缓存？
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        while (base.isFlag()) { }
        while (s.getBase().isFlag()) { }
        System.out.println("test4 end");
    }

    private static volatile Object[] arr = new Object[10];
    @Test
    public void test5() {
        arr[5] = "kkk";
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            arr[5] = 1;
        }).start();
        while ("kkk".equals(arr[5])) { }
        System.out.println("test5 end");
    }


    private static volatile List<Base> list = new ArrayList<>();
    @Test
    public void test6() {
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
        while (list.size() == 1) { }
        System.out.println("test6 end");
    }
}
