package top.hiccup.jdk.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子性修改对象引用
 *
 * @author wenhy
 * @date 2019/8/7
 */
public class AtomicReferenceTest {

    public static void main(String[] args) {

        Student s1 = new Student(1);
        Student s2 = new Student(2);
        AtomicReference<Student> atomicReference = new AtomicReference<>(s1);
        atomicReference.compareAndSet(s1, s2);
        Student s3 = atomicReference.get();

        System.out.println("s3 is " + s3);
        System.out.println("s3.equals(s1): " + s3.equals(s1));
    }

    static class Student {
        private volatile long id;

        public Student(long id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "student: " + id;
        }
    }
}