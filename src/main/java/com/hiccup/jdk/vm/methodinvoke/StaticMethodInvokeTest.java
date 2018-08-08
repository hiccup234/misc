package com.hiccup.jdk.vm.methodinvoke;

/**
 * 静态方法调用采用静态绑定
 *
 * @author wenhy
 * @date 2018/8/8
 */
public class StaticMethodInvokeTest {

    static class Father {
        static void staticMethod() {
            System.out.println(Father.class.getName());
        }
    }

    static class Sub extends Father{
        static void staticMethod() {
            System.out.println(Sub.class.getName());
        }
    }

    public static void main(String[] args) {
        Father.staticMethod();
        Father f = new Father();
        f.staticMethod();
        f = new Sub();
        f.staticMethod();

        Sub.staticMethod();
        Sub s = new Sub();
        s.staticMethod();
    }

}
