package top.hiccup.jdk.lang.langfeature;

/**
 * 操作符测试：javap反编译查看
 *
 * @author wenhy
 * @date 2019/2/16
 */
public class OperatorTest {

    public static void main(String[] args) {
        int a = 3;
        a += a -= a*a;
        // C++期望是 -12，而Java却是-3
        System.out.println(a);
    }
}
