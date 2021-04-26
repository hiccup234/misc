package top.hiccup.jdk.lang.newfeature;

/**
 * JDK1.8允许接口中定义方法的默认实现
 *
 * @author wenhy
 * @date 2018/5/26
 */
public class Jdk8InterfaceTest implements Jdk8Intf1, Jdk8Intf2{

    @Override
    public void defaultMethod() {
        // 同时实现了含有相同默认方法的接口时编译器要求必须重写默认方法
        // 因为Java中类是可以实现多个接口的，如果是继承则没有这样的问题（单继承）
        System.out.println("Sub class`s defaultMethod.");
    }

    public static void main(String[] args) {
        // 接口中的静态方法只能通过接口名调用
        Jdk8Intf1.staticMethod();
        // 接口中的默认方法只能通过实现类的对象调用
        new Jdk8InterfaceTest().defaultMethod();
    }
}

interface Jdk8Intf1 {
    /**
     * 注意修饰关键字：static
     */
    static void staticMethod() {
        System.out.println("I`am a static method.");
    }

    /**
     * 注意修饰关键字：default
     */
    default void defaultMethod() {
        System.out.println("I`am a default method.");
    }
}

interface Jdk8Intf2 {

    static void staticMethod() {
        System.out.println("I`am a static method too.");
    }

    default void defaultMethod() {
        System.out.println("I`am a default method too.");
    }
}