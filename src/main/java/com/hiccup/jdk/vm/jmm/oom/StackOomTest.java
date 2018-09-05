package com.hiccup.jdk.vm.jmm.oom;

/**
 * 栈空间内存溢出测试
 *
 * @VM args: -Xss1M（JDK1.8默认为1M）   -Xss16m （M与m大小写有区别）
 *
 * @author wenhy
 * @date 2018/9/3
 */
public class StackOomTest {

    private static long count;

    public static void recursion() {
        count++;
        recursion();
    }

    public static void main(String[] args) {
        try {
            recursion();
        } catch (Throwable t) {
            System.out.println("递归调用深度：" + count);
//            t.printStackTrace();
        }
    }
}
