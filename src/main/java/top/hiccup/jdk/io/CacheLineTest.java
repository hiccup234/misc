package top.hiccup.jdk.io;

/**
 * 局部性原理与缓存
 * CPU与高速缓存L1/L2/L3的映射关系：
 * 1、直接映射，通常采用mod运算（实际是h&(n-1)，类似HashMap），缓存行中用组标记（Tag）存储取模截取后留下的高位，来解决取模冲突的问题。
 * 2、全相连映射
 * 3、组相连映射
 * 高速缓存写策略：
 * 1、写直达（Write-Through），即就算Cache Block里有数据，也每次都要写入主内存（类似volatile的效果）
 * 2、写回（Write-Back），即判断是否Cache Block有数据且原数据是否为脏数据，如果是把源数据写回内存，并把当前数据写入Cache Block，并标记为脏数据
 *
 * @author wenhy
 * @date 2019/7/24
 */
public class CacheLineTest {

    public static void main(String[] args) {
        int[] arr = new int[256 * 1024 * 1024];
        // 循环 1
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] *= 3;
        }
        System.out.println(System.currentTimeMillis() - startTime);
        // 循环 2（Cache Line的大小一般为64Byte，所以这里步长为16）
        startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i += 16) {
            arr[i] *= 3;
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
