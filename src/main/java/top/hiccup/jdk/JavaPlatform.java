package top.hiccup.jdk;

/**
 * 【Java平台总结】
 * "Write once, run anywhere": 一次编写到处运行，屏蔽了处理器架构、操作系统、硬件设备等的差异
 * （C语言经常需要调用操作系统层面的API，不同操作系统API一般不一样，所以C程序需要跨平台时，源文件需要跟据不同平台进行修改）
 *
 * 也有"Compile once, run anywhere": 一次编译到处运行，
 *
 * 1.Java语言及API类库
 * 2.class文件格式
 * 3.JVM规范
 * 4.类加载体系
 * 5.JRE，JDK工具集
 *
 * JIT：即时编译，在Java程序运行时进行部分编译（Just in Time Compile） @VM args: -XX:+PrintCompilation
 * AOT：预编译为平台机器码，避免JIT的预热开销（Ahead of Time Compile）
 *
 * @author wenhy
 * @date 2018/6/18
 */
public class JavaPlatform {

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
