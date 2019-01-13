package top.hiccup.jdk.vm.classloader;

/**
 * 双亲委派模型：
 *
 * 1.Bootrap ClassLoader(启动ClassLoader，用C++实现)，对应rt.jar 或者用启动参数-Xbootclasspath设定
 *
 * 2.Extension ClassLoader(extends ClassLoader)，对应%JAVA_HOME%/lib/ext/*.jar
 *
 * 3.App ClassLoader(extends ClassLoader)，对应classpath
 *
 * 4.Custom ClassLoader(自定义ClassLoader)，对应自定义路径
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
