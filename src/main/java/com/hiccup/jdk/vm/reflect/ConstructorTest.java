package com.hiccup.jdk.vm.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 反射中构造器测试类
 *
 * @author wenhy
 * @date 2018/8/30
 */
public class ConstructorTest {

    // 如果是内部类，则 Super.class.getConstructor(Integer.TYPE, String.class) 会抛NoSuchMethodException
//    class Super {
//        public Super() { };
//        public Super(Integer i, String s) { }
//        public Super(int i, String s) { }
//    }

    public void testConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for(Constructor c : Super.class.getConstructors()) {
            System.out.println(c);
        }
        System.out.println("=====================================");
        Constructor<?> constructor = Super.class.getConstructor(int.class, String.class);
//        Constructor<?> constructor = Super.class.getConstructor(Integer.TYPE, String.class);
        System.out.println(constructor);
        constructor.newInstance(new Object[]{1, "abc"});
        constructor.newInstance(new Object[]{new Integer(2), "abc"});

    }

    public static void main(String[] args) {
        ConstructorTest main = new ConstructorTest();
        try {
            main.testConstructor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Super {
    public Super() {
        System.out.println("a");
    };
    public Super(Integer i, String s) {
        System.out.println("b");
    }
    public Super(int i, String s) {
        System.out.println("c");
    }
}