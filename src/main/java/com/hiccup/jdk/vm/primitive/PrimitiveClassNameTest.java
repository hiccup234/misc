package com.hiccup.jdk.vm.primitive;

import java.lang.reflect.Array;

/**
 * 原生类型与数组对象名称测试
 *
 * @author wenhy
 * @date 2018/8/8
 */
public class PrimitiveClassNameTest {

    static class Cat {
        static {
            System.out.println("注意：直接访问自定义类的class属性不会引起JVM对类的初始化");
        }
    }

    public static void main(String[] args) {
        // 自定义类
        System.out.println("Cat：" + Cat.class);

        // 调用Class的toString方法
        System.out.println("字符串：" + String.class);

        //  [ 表示一维数组，[[ 二维数组，L 表示是对象类型
        System.out.println("数组：" + String[].class);
    }

}
