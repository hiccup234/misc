package com.hiccup.jdk.vm.methodinvoke;

/**
 * JVM虚方法调用性能优化：
 *
 * Java字节码中与方法调用相关有5个指令
 * 1.invokestatic：用于调用静态方法
 * 2.invokespecial：用于调用私有实例方法、构造器，以及使用super关键字调用父类的实例方法或构造器（这个时候已经可以确认目标方法）和所有实现的接口中的默认方法（JDK1.8）
 * 3.invokevirtual：用于调用菲斯有实例方法
 * 4.invokeinterface：用于调用接口方法
 * 5.invokedynamic：用于调用动态方法
 *
 * 其中1，2为静态绑定
 * 其中3，4为动态绑定（final方法可以不通过动态类型而直接确定目标方法）
 *         动态绑定的调用指令的符号引用存放在常量池（可以用：javap -v VirtualMethodInvokeTest.class 打印常量池）
 *
 *
 * @author wenhy
 * @date 2018/8/8
 */
public class VirtualMethodInvokeTest {

    /**
     * Run with: java -XX:CompileCommand=dontinline,*.* VirtualMethodInvokeTest
     *
     * C:\Users\haiyang.wen\Desktop>java VirtualMethodInvokeTest
     * 990
     * 1782
     *
     * C:\Users\haiyang.wen\Desktop>java -XX:CompileCommand=dontinline,*.* VirtualMethodInvokeTest
     * CompilerOracle: dontinline *.*
     * 3191
     * 3554
     *
     */

    abstract class Father {

        abstract void method();
    }

    class Sub1 extends Father {
        @Override
        void method() { }
    }

    class Sub2 extends Father {
        @Override
        void method() { }
    }

    public static void main(String[] args) {
        VirtualMethodInvokeTest main = new VirtualMethodInvokeTest();
        Sub1 sub1 = main.new Sub1();
        Sub2 sub2 = main.new Sub2();

        long current = System.currentTimeMillis();
        for(int i = 1; i <= 2_000_000_000; i++) {
            if(i % 1_000_000_000 == 0) {
                long temp = System.currentTimeMillis();
                System.out.println(temp - current);
                current = temp;
            }
            Father f = i < 1_000_000_000 ? sub1 : sub2;
            f.method();
        }

    }

}


