package com.hiccup.jdk.lang;

/**
 * Created by wenhy on 2018/1/26.
 * 多态性测试
 */
public class PolymorphismTest {

    public static void main(String[] args) {
        Super sup = new Sub();
        System.out.println(sup.field+" "+sup.getField());

        Sub sub = new Sub();
        System.out.println(sub.field+" "+sub.getField()+" "+sub.getSuperField());
    }
}

class Super {
    public int field = 0;
    public int getField() {
        return field;
    }
}

class Sub extends Super {
    public int field = 1;
    public int getField() {
        return field;
    }
    public int getSuperField() {
        return super.field;
    }
}