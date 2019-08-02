package top.hiccup.jdk.vm.jmm;

/**
 * Java 对于伪共享的传统解决方案
 * 
 * 【伪共享】
 * CPU 缓存系统中是以缓存行（cache line）为单位存储的。目前主流的 CPU Cache 的 Cache Line 大小都是 64 Bytes。
 * 在多线程情况下，如果需要修改“共享同一个缓存行的变量”，就会无意中影响彼此的性能，这就是伪共享（False Sharing）。
 * 
 * https://mp.weixin.qq.com/s/pND6Xm7YGR2aropFApc0xA
 * 
 * 【缓存一致性协议】 MESI（由Intel提出）
 * 在 MESI 协议中，每个 Cache line 有4个状态，可用 2 个 bit 表示，它们分别是：
 * M(Modified)： 这行数据有效，数据被修改了，和内存中的数据不一致，数据只存在于本 Cache 中；
 * E(Exclusive)：这行数据有效，数据和内存中的数据一致，数据只存在于本 Cache 中；
 * S(Shared)：   这行数据有效，数据和内存中的数据一致，数据存在于很多 Cache 中；
 * I(Invalid)：  这行数据无效。
 *
 * 【如何防止伪共享？】
 * 1. 在JDK7之前会 将需要独占缓存行的变量前后添加一组long类型的变量，依靠这些无意义的数组的填充做到一个变量自己独占一个缓存行；
 * 2. 在JDK7因为jvm会将这些没有用到的变量优化掉，所以采用继承一个声明了好多long变量的类的方式来实现；
 * 3. 在JDK8中通过添加sun.misc.Contended注解来解决这个问题，若要使该注解有效必须在jvm中添加以下参数：-XX:-RestrictContended
 * sun.misc.Contended注解会在变量前面添加128字节的padding将当前变量与其他变量进行隔离；
 *
 * @author wenhy
 * @date 2019/1/17
 */
public class FalseSharingTest {

    public static void main(final String[] args) throws InterruptedException {
        final long start = System.currentTimeMillis();
        FalseSharing.runTest();
        System.out.println("duration = " + (System.currentTimeMillis() - start));
    }
}

final class FalseSharing implements Runnable {
    private final static int NUM_THREADS = 4;
    private final static long ITERATIONS = 50L * 1000L * 1000L;
    private final int arrayIndex;

    private static VolatileLong2[] longs = new VolatileLong2[NUM_THREADS];

    static {
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new VolatileLong2();
        }
    }

    public FalseSharing(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public static void runTest() throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharing(i));
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }

    @Override
    public void run() {
        for (long i = 0; i < ITERATIONS; i++) {
            longs[arrayIndex].value = i;
        }
    }

    /**
     * JDK8以后可以使用@sun.misc.Contended自动补齐缓存行，在变量前面添加128个字节
     * 需要注意的是此注解默认是无效的，需要在JVM启动时设置 -XX:-RestrictContended 才会生效。
     */
    @sun.misc.Contended
    public final static class VolatileLong3 {
        public volatile long value = 0L;
    }

    /**
     * JDK7以上使用此方法(jdk7的某个版本oracle对伪共享做了优化)
     */
    public final static class VolatileLong2 {
        public volatile long value = 0L;
        public long p1, p2, p3, p4, p5, p6;
    }

    /**
     * JDK7以下使用此方法
     */
    public final static class VolatileLong1 {
        // cache line padding
        public long p1, p2, p3, p4, p5, p6, p7;
        public volatile long value = 0L;
        // 前面7个long 后面7个long 可以保证value一定是单独占用一个缓存行
        public long p8, p9, p10, p11, p12, p13, p14;
    }

    /**
     * 不带缓存行填充
     */
    public final static class VolatileLong {
        public volatile long value = 0L;
    }
}