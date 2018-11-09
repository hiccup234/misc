package top.hiccup.jdk.lang;

/**
 * 4.静态导入，可以导入静态字段和静态方法
 */
import static top.hiccup.jdk.lang.CreateObjectTest.Foo;
import static top.hiccup.jdk.lang.CreateObjectTest.main;

/**
 * 几种常见的static用法
 *
 * @author wenhy
 * @date 2018/9/19
 */
public class StaticTest {

    /**
     * 1.修饰成员变量
     */
    private static final String s = "hiccup";

    /**
     * 2.静态代码块
     */
    static {
        System.out.println(s);
    }

    /**
     * 3.修饰方法
     */
    static void f() { }

    private Foo foo = new Foo();
    static {
        // 静态导入，可以导入静态字段和静态方法
        main(null);
    }
}
