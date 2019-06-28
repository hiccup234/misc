package top.hiccup.jdk.concurrent;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import lombok.Data;

/**
 * volatile关键字可见性测试:
 * 1、修饰原始类型变量可以保证变量在线程间的可见性
 * 2、修饰对象或数组，如果对象属性或数组元素被修改，可以间接保证对象属性或数组元素的线程可见性（原因见test3分析）
 * 3、修饰List等，添加元素以及更新元素的字段都能保证线程可见性
 *
 * 守护线程上下文：
 * Map configOptions;
 * char[] configText;
 * volatile boolean initialized = false;
 *
 * // Thread A
 * configOptions = new HashMap();
 * configText = readConfigFile(fileName);
 * processConfigOptions(configText, configOptions);
 * initialized = true;
 *
 * // Thread B
 * while (!initialized)
 *   sleep();
 * // use configOptions
 *
 * 线程A对volatile变量的赋值会导致JVM强制将该变量和当时的其他变量状态都刷出CPU缓存
 * 而JMM的happens-before规则，对volatile的写指令不会被重排序到其他操作之前（线程间通信的读写模型）
 * JSR-133规范 重新定义了JMM模型，能够保证线程B获取的configOptions是更新后的值，即volatile语义得到增强，可以守护上下文
 *
 * volatile重排序规则：
 * 1、当第二个操作为volatile写操做时，不管第一个操作是什么(普通读写或者volatile读写)，都不能进行重排序。
 *      这个规则确保volatile写之前的所有操作都不会被重排序到volatile之后，所以可以用来守护上下文。
 * 2、当第一个操作为volatile读操作时，不管第二个操作是什么，都不能进行重排序。
 *      这个规则确保volatile读之后的所有操作都不会被重排序到volatile读之前，读取上面写的上下文。
 * 3、当第一个操作是volatile写操作时,第二个操作是volatile读操作,不能进行重排序。
 *      确保先写上下文，再读上下文。
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
            System.out.println(t2);
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
     * Q: volatile变量修饰对象或者数组时，当我们改变对象属性或者数组元素的时候，也能保证线程间的可见性？
     * A: volatile变量从语义上来讲只是保证了对象或数组引用的内存地址的线程可见性，
     *    如果线程通过volatile变量访问其属性时，会提示JVM不要对这个属性做缓存拷贝（是缓存整个对象还是说仅仅是访问的这个属性呢？ 答：缓存要访问的属性到高速缓存中）
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

        // 就算这里提前访问了flag，也影响不到后面的sleep(800)
        System.out.println(base.isFlag());

        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Base innerBase = s.getBase();
            // 先访问一次再设值也不会引起本地缓存
            System.out.println(innerBase.isFlag());

            // 这里的innerBase引用不是volatile的，为什么设置的值也能立即被其他线程看到呢？
            // 通过volatile根引用得到的引用都具有volatile特性吗？？（也就是说volatile有传递的特性？？）
            innerBase.setFlag(false);
            System.out.println(innerBase.isFlag());
        }).start();

        // TODO 如果让主线程休眠2秒，让子线程先设置falg，则程序1也能立即退出，难道是因为最开始主线程没有访问属性，所以没有做本地缓存？
        try {
//            Thread.sleep(100);
            // 这里的休眠时间长短也有区别
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 1、如果直接通过base访问，程序是没办法立即结束的
        while (base.isFlag()) { }

        // 2、通过volatile引用访问是可以读到最新值，可能立即结束
//        while (s.getBase().isFlag()) { }

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
