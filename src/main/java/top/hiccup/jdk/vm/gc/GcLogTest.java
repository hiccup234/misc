package top.hiccup.jdk.vm.gc;

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
