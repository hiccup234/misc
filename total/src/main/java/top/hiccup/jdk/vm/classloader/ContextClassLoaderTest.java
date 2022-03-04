package top.hiccup.jdk.vm.classloader;

/**
 * 线程上下文类加载器（ContextClassLoader）是JDK1.2引入的，可以通过java.lang.Thread类中的getContextClassLoader()和
 * setContextClassLoader(ClassLoader cl)方法来获取和设置线程的上下文类加载器。如果没有手动设置上下文类加载器，线程将继承其父线程的上下文类加载器，
 * 初始线程（main线程）的上下文类加载器是系统类加载器（AppClassLoader），在线程中运行的代码可以通过此类加载器来加载类和资源，以破坏双亲委派模型。
 *
 * =====================================================================================================================
 * Java应用中存在很多服务提供者接口（Service Provider Interface，SPI），这些接口允许第三方为它们提供实现，如常见的 SPI 有 JDBC、JNDI等，
 * 这些SPI的接口属于Java核心库，一般存在rt.jar包中，由Bootstrap类加载器加载，而SPI的第三方实现代码则是作为Java应用所依赖的jar包被存放在classpath路径下，
 * 由于SPI接口中的代码经常需要加载具体的第三方实现类并调用其相关方法，但SPI的核心接口类是由引导类加载器来加载的，而Bootstrap类加载器无法直接加载SPI的实现类，
 * 同时由于双亲委派模式的存在，BootstrapClassLoader也无法反向委托AppClassLoader加载SPI的实现类。JDK团队给出的方案就是线程上下文类加载器。
 *
 * BootstrapClassLoader委托ContextClassLoader做第三方实现的加载，即破环了双亲委派模型。
 *
 * SPI 对应的加载器为 ServiceLoader
 *
 * @author wenhy
 * @date 2019/4/25
 */
public class ContextClassLoaderTest {

    public static void main(String[] args) {
        // 参考java.sql.DriverManager

        // DriverManager 的静态初始化块 loadInitialDrivers() >> ServiceLoader.load(Driver.class)
        // load方法中会获取线程上下文加载器 ClassLoader cl = Thread.currentThread().getContextClassLoader();
    }
}
