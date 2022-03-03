package top.hiccup.jdk.vm.jmm;

import java.util.ArrayList;
import java.util.List;

/**
 * JVM堆大小配置测试：
 *
 * -Xms20m 设置堆初始大小（一般保持初始值跟最大值一样，避免动态分配内存，JDK的建议是：-Xms是-Xmx的一半）
 * -Xmx20m 设置堆最大大小
 *
 * -XX:NewRatio=2 设置老年代与新生代的比例（JDK7(Hotspot)默认为2）
 *  老年代:新生代=2:1，即新生代占堆的1/3
 *
 * -Xmn10m 设置新生代大小
 * JDK1.7及以前默认最小1M，eden 0，from/to各512K，from/to默认最小512K且大小是512K的整数倍
 * JDK1.8新生代默认最小1.5M，eden/from/to各512K，eden/from/to默认最小512K且大小是512K的整数倍
 *
 * -XX:SurvivorRatio=8 设置新生代中eden与survivor的比例（默认为8）
 *  eden:from:to=8:1:1，因此eden:(from+to)=8:2，即一个from占年轻代的1/10
 *
 *
 * @VM args: -Xms20m -Xmx20m -Xmn1m -XX:+PrintGCDetails
 * @VM args: -Xms20m -Xmx20m -Xmn6m -XX:SurvivorRatio=2 -XX:+PrintGCDetails
 *
 * @author wenhy
 * @date 2018/9/5
 */
public class HeapSizeTest {

    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            // 每次分配1M大小的byte数组
            list.add(new byte[2*1024*1024]);
        }
    }

}
