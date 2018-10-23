package top.hiccup.jdk.vm.classloader;

/**
 * 双亲委派模型：
 *
 * BootStrap ClassLoader(启动类加载器，用C++实现)   >> rt.jar
 *
 * Extension ClassLoader extends ClassLoader    >> %JAVA_HOME%/lib/ext/*.jar
 *
 * AppCLassLoader extends ClassLoader           >> classpath
 *
 * 自定义类加载器 extends ClassLoader              >> 加载自定义路径
 *
 * @author wenhy
 * @date 2018/10/23
 */
public class ParentsDelegateTest {

    public static void main(String[] args) {
        ClassLoader classLoader = ParentsDelegateTest.class.getClassLoader();
        while (null != classLoader) {
            System.out.println(classLoader);
            classLoader = classLoader.getParent();
        }
        System.out.println(classLoader);
    }
}
