package com.hiccup.jdk.vm.jmm;

/**
 * 栈上分配（逃逸分析）:
 * JDK1.7以后JVM默认开启逃逸分析（方法内创建的对象会不会被在方法外被引用：返回对象引用，将对象引用赋值给全局变量）
 * 栈上分配会受限于栈空间的大小
 *
 * @VM args: -XX:+DoEscapeAnalysis -XX:+PrintGC  （开启逃逸分析，观察GC情况及执行时间）
 * @VM args: -XX:-DoEscapeAnalysis -XX:+PrintGC  （关闭逃逸分析，观察GC情况及执行时间）
 *
 * @author wenhy
 * @date 2018/9/3
 */
public class OnStackTest {

    public static void alloc(){
        new Object();
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        // 循环一亿次
        for(int i=0;i<100_000_000;i++){
            alloc();
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }

}