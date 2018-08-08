package com.hiccup.jdk.lang;

/**
 * 内部类测试
 *
 * @author wenhy
 * @date 2018/2/2
 */
public class InnerClassTest {

    public static void main(String[] args) {
        Outer outer = new Outer();
        // 注意这里的类型申明
        Outer.Inner inner = outer.getInnerObj();
    }
}

class Outer {
    /**
     * 内部类访问限定符可以是public的，与.java文件中的public class不一样
     */
    public class Inner { }

    /**
     * 也可以是包访问权限
     */
    class Inner2 { }

    /**
     * 还可以是私有访问权限
     */
    private class Inner3 {

    }

    Inner getInnerObj() {
        return new Inner();
    }

}


