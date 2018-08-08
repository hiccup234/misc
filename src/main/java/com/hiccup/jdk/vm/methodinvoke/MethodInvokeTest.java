package com.hiccup.jdk.vm.methodinvoke;

/**
 * JVM中方法调用测试：
 *
 * 【重载方法分为3个选取阶段】
 * 1.在不考虑基本类型自动拆装箱和可变长参数情况下选取
 * 2.在一阶段没适配的方法时，考虑自动拆装箱但不考虑可变长参数情况下选取
 * 3.在二阶段没适配的方法时，考虑自动拆装箱以及可变长参数情况下选取
 *
 * 如果在同一阶段找到多个适配的方法则选取最为贴切的那个（与参数类型最贴近的子类方法签名）
 *
 *
 * @author wenhy
 * @date 2018/8/8
 */
public class MethodInvokeTest {

    void method(Object obj, Object... agrs) {
        System.out.println("method 1");
    }

    void method(String s, Object obj, Object... args) {
        System.out.println("method 2");
    }

    public static void main(String[] args) {
        MethodInvokeTest main = new MethodInvokeTest();
        // 调用第二个method方法
        main.method(null, 1);
        // 调用第二个method方法
        main.method(null, 1, 2);
        // 手动绕开，调用第一个方法
        main.method(null, new Object[]{});
    }

}


