package com.hiccup.misc;

/**
 * 逻辑操作符短路测试
 *
 * @author wenhy
 * @date 2018/1/16
 */
public class ShortCircuitTest {

    private static boolean test1(int i) {
        System.out.println("test1:"+ (i < 1));
        return i < 1;
    }

    private static boolean test2(int i) {
        System.out.println("test2:"+(i < 2));
        return i < 2;
    }

    private static boolean test3(int i) {
        System.out.println("test3:"+(i < 3));
        return i < 3;
    }

    public static void main(String[] args) {
        // 不会调用test3方法
        boolean b = test1(0) && test2(2) && test3(3);
        System.out.println(b);
        System.out.println("=================================");
        // 或运算同样存在短路现象，只有有一个条件为真整个表达式就为真
        boolean b2 = test1(0) || test2(3) || test3(3);
        System.out.println(b2);
    }

}
