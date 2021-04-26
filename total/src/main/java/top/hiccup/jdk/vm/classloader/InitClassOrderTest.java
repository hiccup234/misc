package top.hiccup.jdk.vm.classloader;

/**
 * 类加载初始化过程：实例初始化不一定要在类初始化结束之后才开始（这是理解这个用例的要点）。
 *
 *                 |    链接     |
 * 类的生命周期：加载->验证->准备->解析->初始化->使用->卸载，只有在准备和初始化阶段才会涉及类变量的初始化和赋值。
 *
 * @author wenhy
 * @date 2019/8/13
 */
public class InitClassOrderTest {

    /**
     * TODO 注意这里是一个嵌套的静态字段（本类对象），类加载时会初始化这个变量，从而导致调用InitClassOrderTest的构造方法（对象初始化）
     * TODO 对象的初始化是先初始化成员变量再执行构造方法
     */
    static InitClassOrderTest test = new InitClassOrderTest();

    static {
        System.out.println("1");
    }

    {
        System.out.println("2");
    }

    int a = 110;
    /**
     * 准备阶段为类变量分配内存并设置默认值，0或null
     */
    static int b = 119;
    /**
     * 如果类变量由final修饰，编译时javac将会为value生成ConstantValue属性，
     * 在准备阶段虚拟机就会根据ConstantValue将变量设置为指定的值
     */
    final static int c = 911;

    InitClassOrderTest() {
        System.out.println("3");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
    }

    public static void staticFunction() {
        System.out.println("4");
    }

    public static void main(String[] args) {
        staticFunction();
    }
}