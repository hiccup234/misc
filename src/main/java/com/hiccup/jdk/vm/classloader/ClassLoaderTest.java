package com.hiccup.jdk.vm.classloader;

/**
 * JVM装载class文件：加载>>链接（验证，准备，解析）>>初始化
 *
 * 【加载】
 * 用参数-verbose:class 或者 -XX:+TraceClassLoading 可以看到类加载过程
 * 启动类加载器：BootstrapClassLoader由C++实现，没有对应的Java对象
 * 双亲委派模型：避免重复加载Java类型
 *
 * 【链接】
 * 验证：验证class文件是否符合JVM规范（类似Spring加载完Resouce配置文件后，对xml的DTD和Schema验证）
 * 准备：为类和接口的静态字段分配内存[初始值]（初始化在稍后的初始化阶段），创建方法表（静态绑定、动态绑定）
 * 解析：将常量池中的符号引用替换为直接引用
 *
 * 【初始化】
 * <clinit>方法：初始化类的静态字段并执行static代码块
 *
 * ===================================================================================
 * 【引申】
 * Q: 在加载一个类之后，如果对类的字节码进行修改，如何在不重新启动JVM的情况下加载已经修改过的类？
 * A: 新创建一个ClassLoader并loadClass，原来加载的ClassLoader不能重新加载（无法直接卸载已装载的类）
 * ===================================================================================
 *
 * @author wenhy
 * @date 2018/8/4
 */
public class ClassLoaderTest extends ClassLoader {


    /**
     * 新建数组会加载元素类，但是不会链接和初始化：可以用字节码工具amstools.jar破环class文件格式来验证
     */
    static class InnerClass {
        static {
            System.out.println("InnerClass.<clinit>");
        }
    }

    public static void main(String[] args) {
        InnerClass[] icArr = new InnerClass[10];
        System.out.println("===========================");
        InnerClass ic = new InnerClass();
    }

}
