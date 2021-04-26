package top.hiccup.jdk.concurrent.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * AtomicStampedReference 与 AtomicMarkableReference 的设计思想非常一致
 *
 * 前者是通过版本号final int stamp来解决CAS过程中的ABA问题
 * 而后者是通过final boolean mark来做标记
 *
 * @author wenhy
 * @date 2019/8/7
 */
public class AtomicStampedReferenceTest {

    public static void main(String[] args) {
        String s1 = "aaa";
        String s2 = "bbb";
        AtomicStampedReference<String> reference = new AtomicStampedReference<>(s1, 1);
        reference.compareAndSet(s1, s2, reference.getStamp(), reference.getStamp() + 1);
        System.out.println("reference.getReference: " + reference.getReference());

        // 修改stamp
        System.out.println("reference.attemptStamp: " + reference.attemptStamp(s2, reference.getStamp() + 1));
        System.out.println("reference.getStamp: " + reference.getStamp());

        System.out.println("reference.weakCompareAndSet: " + reference.weakCompareAndSet(s2, "ccc", 3, reference.getStamp() + 1));
        System.out.println("reference.getReference: " + reference.getReference());
    }
}
