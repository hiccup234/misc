package top.hiccup.misc;

/**
 * 局部性原理测试
 *
 * @author wenhy
 * @date 2019/10/24
 */
public class LocalityTest {

    /**
     * 遍历10亿次
     */
    private static final int max = 1024 * 1024 * 1024;

    private static final int[] arr = new int[max];

    private static long cacheLine() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            arr[i] += 1;
        }
        return System.currentTimeMillis() - startTime;
    }

    private static long noCacheLine() {
        long startTime = System.currentTimeMillis();
        // 这里步长比cacheLine增加了16倍，理论上“访问内存”的次数少了16倍，性能基本应该能提升16倍左右
        // 但是由于x86缓存行一般64Byte，所以对数组遍历时可以有效利用局部性原理，就算步长增加到16倍，也都还是可以直接访问缓存行，而不用去内存加载
        for (int i = 0; i < max; i += 16) {
//      for (int i = 0; i < max; i += 32) {
//      for (int i = 0; i < max; i += 64) {
            arr[i] += 1;
        }
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        long cacheTotal = 0;
        for (int i = 0; i < 20; i++) {
            cacheTotal += cacheLine();
        }
        System.out.println(cacheTotal / 20);

        long noCacheTotal = 0;
        for (int i = 0; i < 20; i++) {
            noCacheTotal += noCacheLine();
        }
        System.out.println(noCacheTotal / 20);
    }
}
