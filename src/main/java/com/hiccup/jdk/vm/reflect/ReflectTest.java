package com.hiccup.jdk.vm.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射机制实现原理：
 * 【动态实现】
 *
 * 【委派实现】
 * 反射慢是因为需要先调本地方法的C++代码，再调Java代码
 *
 * @author wenhy
 * @date 2018/8/12
 */
public class ReflectTest {

    /**
     * 反射调用目标方法
     * @param i
     */
    public static void target(int i) {
        // 这样可以打印栈轨迹（JVM自身创建Exception及部分不可见栈轨迹看不到）
        new Exception("#"+i).printStackTrace();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("==================================");
    }

    public static void main(String[] args) {
        try {
            // 普通方式的方法调用
            ReflectTest.target(1);
            // 通过反射调用
            Class<?> clazz = Class.forName("com.hiccup.jdk.vm.reflect.ReflectTest");
            Method method = clazz.getMethod("target", int.class);
            method.invoke(null, 0);

            System.out.println(int.class == Integer.TYPE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
