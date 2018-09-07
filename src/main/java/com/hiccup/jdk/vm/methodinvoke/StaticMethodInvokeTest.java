package com.hiccup.jdk.vm.methodinvoke;

/**
 * 静态方法调用采用静态绑定
 *
 * @author wenhy
 * @date 2018/8/8
 */
@SuppressWarnings("all")
public class StaticMethodInvokeTest {

    static class Super {
        static void staticMethod() {
            System.out.println(Super.class.getName());
        }
    }

    static class Sub extends Super{
        static void staticMethod() {
            System.out.println(Sub.class.getName());
        }
    }

    public static void main(String[] args) {
        Super.staticMethod();
        Super f = new Super();
        f.staticMethod();
        f = new Sub();
        f.staticMethod();

        Sub.staticMethod();
        Sub s = new Sub();
        s.staticMethod();
    }

}
