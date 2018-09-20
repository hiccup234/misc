package top.hiccup.jdk.concurrent.aqs;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 【CAS】
 * (Compare And Swap)比较和替换算法，底层原理是基于CPU指令实现的，不同的CPU架构有不同的指令集，本质是一种乐观锁
 *
 * 存在问题:
 * 1.ABA问题：如单链表表头（实现栈功能）变化后的ABA问题
 * 解决办法：增加版本号，对应Java中带版本戳的AtomicStampedReference
 *
 * @author wenhy
 * @date 2018/8/20
 */
public class CASTest {

    private static AtomicLong atomicLong = new AtomicLong(100L);
    /**
     * 如果值是在-128~127会有LongCache
     */
    private static Long num100 = new Long(100);
    private static Long num200 = new Long(200);
    /**
     * 初始版本号为0
     */
    private static AtomicStampedReference<Long> atomicStampedReference =
            new AtomicStampedReference<>(num100, 0);

    public static void main(String[] args) {

        // 模拟多线程情况
        boolean a1 = atomicLong.compareAndSet(100, 200);
        // 这里再次改回100
        boolean a2 = atomicLong.compareAndSet(200, 100);
        // 存在ABA问题，这里期望的100其实是被修改过后的
        boolean a3 = atomicLong.compareAndSet(100, 200);
        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a3);
        System.out.println(atomicLong);

        // 带版本戳的CAS，实际上是在原引用类型的基础上再封装了一个对象，见Pair.of()
        // 配合volatile的pair字段来解决ABA问题
        int originalStamp = atomicStampedReference.getStamp();
        boolean b1 = atomicStampedReference.compareAndSet(num100, num200,
                atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
        // 这里如果是直接传基础类型，Java每次回自动装箱为一个新的对象
        boolean b2 = atomicStampedReference.compareAndSet(num200, num100,
                atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);

        boolean b3 = atomicStampedReference.compareAndSet(num100, num200,
                originalStamp, originalStamp + 1);
        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b3);
        System.out.println(atomicStampedReference.getReference());
    }
}
