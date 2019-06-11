package top.hiccup.jdk.vm.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * 一般用参数：-XX:+PrintGCDetails（JDK9已经deprecated） 来打印GC时的日志，VM进程结束后打印堆信息
 *
 * GC一般分为：
 *      Minor GC（发生在新生代，比较频繁但耗时一般很短）
 *      Major GC（发生在老年代，一般会先有至少一次的Minor GC，速度比Minor GC慢10倍以上）
 *      Full GC（对整个堆上做清理工作）
 *
 * 日志中的GC和Full GC不是区分新生代和老年代的，如果有Full GC说明是发生了STW
 *
 * -Xms / -Xmx — 堆的初始大小 / 堆的最大大小	一般设置为相同大小
 * -Xmn — 堆中年轻代的大小
 * -XX:-DisableExplicitGC — 让System.gc()不产生任何作用
 * -XX:+PrintGCDetails — 打印GC的细节
 * -XX:+PrintGCDateStamps — 打印GC操作的时间戳
 * -XX:NewSize / XX:MaxNewSize — 设置新生代大小/新生代最大大小
 * -XX:NewRatio — 可以设置老生代和新生代的比例
 * -XX:PrintTenuringDistribution — 设置每次新生代GC后输出幸存者乐园中对象年龄的分布
 * -XX:InitialTenuringThreshold / -XX:MaxTenuringThreshold：设置老年代阀值的初始值和最大值
 * -XX:TargetSurvivorRatio：设置幸存区的目标使用率
 *
 * @author wenhy
 * @date 2019/2/10
 */
public class GcLogTest {

    /**
     * @VM args: -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+PrintGCDetails
     */
    private static final int _1MB = 1024*1024;

    public static void main(String[] args) {
        // 会触发Full GC，并且在这之前还有一次Minor GC
//        System.gc();

        byte[] b1,b2,b3,b4;
        b1 = new byte[2*_1MB];
        b2 = new byte[2*_1MB];
        b3 = new byte[2*_1MB];
        // 这里会引起一次Minor GCe
        b4 = new byte[4*_1MB];
    }
}
