package top.hiccup.jdk.vm.classloader;

/**
 * JVM装载class文件：加载>>链接（验证，准备，解析）>>初始化
 *
 * 【加载】
 * 把*.class文件的二进制流加载到元数据区，并在堆上创建对应类型的Class对象
 * 用参数-verbose:class 或者 -XX:+TraceClassLoading 可以看到类加载过程
 * 启动类加载器：BootstrapClassLoader由C++实现，没有对应的Java对象
 * 双亲委派模型：避免重复加载Java类
 *
 * 【链接】
 * 验证：验证class文件是否符合JVM规范（类似Spring加载完Resouce配置文件后，对xml的DTD和Schema验证）
 * 准备：为类和接口的静态字段分配内存[初始值]（初始化在稍后的初始化阶段），如int、long设置为0，boolean设置为false，Object设置为null
 *      创建方法表（静态绑定、动态绑定）
 * 解析：将常量池中的符号引用替换为直接引用（偏移地址）
 *
 * 【初始化】
 * <clinit>方法：初始化类的静态字段并执行static代码块
 *              a:为static成员变量赋设置的值
 *              b:执行static{}块
 * （对象的初始化类似，先为父类成员赋设置的值，执行父类构造函数，然后是子类的，即子类的<clinit>调用前保证父类的<clinit>被调用）
 *
 * ===================================================================================
 * 【引申】
 * Q: 在加载一个类之后，如果对类的字节码进行修改，如何在不重新启动JVM的情况下加载已经修改过的类？
 * A: 新创建一个ClassLoader并loadClass，原来加载的ClassLoader不能重新加载（无法直接卸载已装载的类）
 * ===================================================================================
 *
 * 破坏双亲委派模型：
 * 很多情况下需要在顶层ClassLoader中加载下层ClassLoader的类，
 * 比如顶层的一些类方法工厂需要创建下层ClassLoader的类对象等，而根据双亲模式这是不允许加载的
 * 双亲模式是默认的模式，但不是必须这么做
 * 采用上下文加载器可以实现parent加载下级的类，原理是在parent加载器中传入下级ClassLoader的实现
 * Tomcat的WebappClassLoader 就会先加载自己的Class，找不到再委托parent
 * OSGi的ClassLoader形成网状结构，根据需要自由加载Class
 *
 * Class.forName与ClassLoader的loadClass类似，都是加载类，不过loadClass只做加载，不过链接(link)和初始化(initialize)，
 * 而Class.forName默认是3步操作都做，也可以设置只做第一步
 * （forName第二个参数设置为false：Class.forName("fff", false, ClassLoaderTest.class.getClassLoader());）
 *
 * @author wenhy
 * @date 2018/8/4
 */
public class ClassLoaderTest {


    /**
     * 新建数组会加载元素类，但是不会链接和初始化：可以用字节码工具amstools.jar破环class文件格式来验证
     */
    static class InnerClass {
        static {
            System.out.println("InnerClass.<clinit>");
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        InnerClass[] icArr = new InnerClass[10];
        System.out.println("===========================");
        InnerClass ic = new InnerClass();

        Class clazz = OtherClass.class;
        System.out.println("===========================");
        System.out.println(clazz);
        System.out.println("OtherClass.class 不会引起初始化");
        Class.forName("top.hiccup.jdk.vm.classloader.OtherClass");
//        try {
//            clazz.newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
    }

}

/**
 * OtherClass.class 不会引起初始化
 */
class OtherClass {
    static {
        System.out.println("OtherClass.<clinit>");
    }
}
