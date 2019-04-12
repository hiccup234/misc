package top.hiccup.jdk.vm;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

/**
 * 性能测试>>基准测试>>JMH
 *
 * @author wenhy
 * @date 2019/4/12
 */
public class PerformanceTest {

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public static void m1() {
        for (int i = 0; i< 1000000; i++) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        m1();
    }
}
