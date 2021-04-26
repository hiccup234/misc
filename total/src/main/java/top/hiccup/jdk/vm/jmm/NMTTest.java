//package top.hiccup.jdk.vm.jmm;

/**
 * JVM内存分布分析
 *
 * // 开启NMT追踪，总结统计方式
 * @VM args: -XX:NativeMemoryTracking=summary
 * // 应用退出时打印NMT统计信息
 * @VM args: -XX:+UnlockDiagnosticVMOptions -XX:+PrintNMTStatistics
 *
 * @author wenhy
 * @date 2019/4/6
 */
public class NMTTest {

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
