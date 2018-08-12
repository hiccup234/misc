package com.hiccup.jdk.lang;

import java.util.Map;

/**
 * ClassNotFoundException与NoClassDefFoundError区别：
 * 在Java中错误和异常是有区别的，我们可以从异常中恢复程序但却不应该尝试从错误中恢复程序
 *
 * 【ClassNotFoundException】
 * Java支持使用Class.forName方法来动态地加载类
 * 另外还有一个导致ClassNotFoundException的原因就是：当一个类已经某个类加载器加载到内存中了，此时另一个类加载器又尝试着动态地从同一个包中加载这个类
 *
 * 【NoClassDefFoundError】
 * JVM或者ClassLoader实例尝试加载（静态方法调用或者使用new创建对象）类的时候却找不到类的定义
 * 一般是要加载的类在编译的时候是存在的，运行的时候却找不到了（打包的时候jar的问题）
 * ClassLoader.defineClass方法中preDefineClass方法会抛出此Error
 *
 * @author wenhy
 * @date 2018/8/12
 */
public class ClassExceptionAndErrorTest {

    private static final String NON_CLASS = "com.hiccup.jdk.lang.NonClass";


    public static void main(String[] args) {
        // 抛出ClassNotFoundException情况1
        try {
            Class.forName(NON_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 抛出ClassNotFoundException情况2
        try {
            new ClassLoader() { }.loadClass(NON_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 实际由这两个protected方法抛出ClassNotFoundException异常
//        new ClassLoader() { }.findClass(NON_CLASS);
//        new ClassLoader() { }.findSystemClass(NON_CLASS);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String curClassPath = System.getProperty("java.class.path");
        System.out.println(curClassPath);

        for(Map.Entry entry : System.getProperties().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

}
