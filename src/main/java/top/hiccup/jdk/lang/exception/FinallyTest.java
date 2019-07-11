package top.hiccup.jdk.lang.exception;

/**
 * finally块在特定情况下不会被执行
 *  1.System.exit(1);
 *  2.多线程环境下线程被强制结束
 *
 * Tips: 一般情况下不要在finally块中return，因为这样会覆盖try中return的值（C#是禁止程序员从finally中return的）
 *
 * @author wenhy
 * @date 2018/9/18
 */
public class FinallyTest {

    public static int finallyTest() {
        int i = 0;
        try {
            return i;
        } finally {
            ++i;
        }
    }

    public static int finallyTest2() {
        int i = 0;
        try {
            return i;
        } finally {
            return ++i;
        }
    }

    public static void nonFinallyTest() {
        try {
            System.out.println("try block");
            System.exit(1);
        } finally {
            System.out.println("finally block");
        }
    }

    public static void main(String[] args) {
        System.out.println(finallyTest());
        System.out.println(finallyTest2());
        nonFinallyTest();
    }
}
