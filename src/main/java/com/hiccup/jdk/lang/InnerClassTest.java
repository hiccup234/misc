package com.hiccup.jdk.lang;

/**
 * Created by wenhy on 2018/2/2.
 */
class Outer {
    // 内部类访问限定符可以是public的
    public class Inner {

    }
    // 也可以是包访问权限
    class Inner2 {

    }
    // 还可以是私有访问权限
    private class Inner3 {

    }
    Inner getInnerObj() {
        return new Inner();
    }

}

public class InnerClassTest {

    public static void main(String[] args) {
        Outer outer = new Outer();
        Outer.Inner inner = outer.getInnerObj();
    }
}
