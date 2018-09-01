package com.hiccup.jdk.vm.reflect;

/**
 * 常见3种获得Class对象的方法
 *
 * 【反射常用案例】
 * 1.通过Class对象的newInstance()创建实例，要求类中由无参构造器
 * 2.通过isInstance(Object)判断是否为该类实例，语法上等同instanceof
 * 3.Array.newInstance(Class, int)创建该类型数组
 * 4.getFields(), getConstructors(), getMethods()
 *
 * 5.Field, Constructor, Method 的setAccessible(true)来改变访问限制
 * 6.Field.get/set来访问成员变量的值
 * 7.Constructor.newInstance(Object[])创建实例
 * 8.Method.invoke(Object, Object[])来调用方法
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
            System.out.println(int.class);
            System.out.println(Integer.TYPE);
            System.out.println(int[].class);

            // 3.通过对象获取
            System.out.println(new ClassObjectTest().getClass() == clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
