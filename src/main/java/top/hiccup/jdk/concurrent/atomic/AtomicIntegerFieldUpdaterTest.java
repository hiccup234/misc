package top.hiccup.jdk.concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 只能原子性修改对象中的int类型字段
 *
 * byte、long、Integer等都不行，会报错：
 * java.lang.ExceptionInInitializerError
 * Caused by: java.lang.IllegalArgumentException: Must be integer type
 *
 * long 对应 AtomicLongFieldUpdater
 *
 * Integer等对象引用 对应 AtomicReferenceFieldUpdater
 *
 * @author wenhy
 * @date 2019/8/7
 */
public class AtomicIntegerFieldUpdaterTest {

    private static AtomicIntegerFieldUpdater<AtomicIntegerFieldUpdaterTest> update =
            AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdaterTest.class, "i");
    private static AtomicIntegerFieldUpdaterTest test = new AtomicIntegerFieldUpdaterTest();
    public volatile int i = 100;

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(() -> {
                if (update.compareAndSet(test, 100, 120)) {
                    System.out.print("原子性的修改");
                }
            });
            t.start();
        }
    }
}
