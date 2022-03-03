package top.hiccup.misc;

/**
 * 局部性原理测试
 *
 * @author wenhy
 * @date 2019/10/24
 * @VM -Xms1024m -Xmx8192m
 */
public class LocalityTest {

    private static final int MAX = 1024 * 1024 * 1024;
    private static final int[] arr = new int[MAX];

    private static long fill1() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            arr[i] = 1;
        }
        return System.currentTimeMillis() - startTime;
    }

    private static long fill2() {
        long startTime = System.currentTimeMillis();
        // 这里步长比fill1增加了16倍，理论上“访问内存”的次数少了16倍，性能基本应该能提升16倍左右
        // 但是由于x86缓存行一般64Byte，所以对数组遍历时可以有效利用局部性原理，一次性从内存中加载64字节
        // 就算步长增加到16倍，也都还是可以直接访问缓存行，而不用去内存加载
        for (int i = 0; i < MAX; i += 16) {
//      for (int i = 0; i < MAX; i += 32) {
//      for (int i = 0; i < MAX; i += 64) {
            arr[i] = 1;
        }
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        long cacheTotal = 0;
        for (int i = 0; i < 10; i++) {
            long time = fill1();
            System.out.println("测试1耗时：" + time);
            cacheTotal += time;
        }
        System.out.println("测试1平均耗时：" + cacheTotal / 10);
        System.out.println();

        long noCacheTotal = 0;
        for (int i = 0; i < 10; i++) {
            long time = fill2();
            System.out.println("测试2耗时：" + time);
            noCacheTotal += time;
        }
        System.out.println("测试2平均耗时：" + noCacheTotal / 10);
    }
}
