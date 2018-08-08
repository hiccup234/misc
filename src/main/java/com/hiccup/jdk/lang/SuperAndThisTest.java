package com.hiccup.jdk.lang;

/**
 * this() super() 在一个构造器中只能使用this()或者super()之中的一个，而且调用的位置只能在构造器的第一行
 * 在子类中如果希望调用父类的构造器来初始化父类的部分，那就用合适的参数来调用super()
 *
 * @author wenhy
 * @date 2018/1/26
 */
public class SuperAndThisTest {

    public static void main(String[] args) {
        new B("aaa");
    }
}

class A
{
    public A(){
        System.out.println("class A");
    }
}

class B extends A {
    public B() {
        //这里，编译器将自动加上super();
        System.out.println("class B");
    }

    B(String str) {
        this();
//        super();
        System.out.println(str);
    }
}