package com.hiccup.jdk.vm.reflect;

/**
 * 3种获得Class对象的方法
 *
 * @author wenhy
 * @date 2018/8/30
 */
public class ClassObjectTest {

    public static void main(String[] args) {
        try {
            // 1.反射获取
            Class<?> clazz = Class.forName("com.hiccup.jdk.vm.reflect.ClassObjectTest");
            System.out.println(clazz);

            // 2.通过类属性获取
            System.out.println(ClassObjectTest.class == clazz);

            // 3.通过对象获取
            System.out.println(new ClassObjectTest().getClass() == clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
