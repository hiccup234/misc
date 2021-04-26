package top.hiccup.jdk.util;

import java.util.BitSet;

/**
 * 位图工具测试类：
 * 1、用来排序
 * 2、现在有1千万个随机数，随机数的范围在1到1亿之间。现在要求写出一种算法，将1到1亿之间没有在随机数中的数求出来
 * 3、统计40亿个数据中没有出现的数据，将40亿个不同数据进行排序等
 *
 * Q：为什么没有BitLongSet呢？
 *
 * @author wenhy
 * @date 2019/3/5
 */
public class BitSetTest {

    public static void main(String[] args) {

        int max = Integer.MAX_VALUE;
        System.out.println(max);
        System.out.println((1 << 31) - 1);
        System.out.println(1 << 31);

        System.out.println("===================================");
        System.out.println(max + "bit");
        System.out.println(max / 8 + "Byte");
        System.out.println(max / (8 * 1024) + "KB");
        System.out.println(max / (8 * 1024 * 1024) + "MB");
        System.out.println(max / (8L * 1024 * 1024 * 1024) + "GB");

        System.out.println("===================================");
        long maxLong = Long.MAX_VALUE;
        System.out.println(maxLong + "bit");
        System.out.println(maxLong / 8 + "Byte");
        System.out.println(maxLong / (8 * 1024) + "KB");
        System.out.println(maxLong / (8 * 1024 * 1024) + "MB");
        System.out.println(maxLong / (8L * 1024 * 1024 * 1024) + "GB");
        System.out.println("===================================");


        int[] array = new int[]{423, 700, 3333, 2323, 356, 740, 1, 234, 3, 3, 2, 2, 2};
        BitSet bitSet = new BitSet(2 << 13);
        // 虽然可以自动扩容，但尽量在构造时指定估算大小，
        // 我们要看一下数组中的最大值，然后指定大致的容量，默认为64
        System.out.println("BitSet size: " + bitSet.size());

        for (int i = 0; i < array.length; i++) {
            bitSet.set(array[i]);
        }
        //剔除重复数字后的元素个数
        int bitLen = bitSet.cardinality();
        System.out.println("要排序的数组容量：" + array.length);
        System.out.println("剔除重复数字后的元素个数：" + bitLen);

        //进行排序，即把bit为true的元素复制到另一个数组
        int[] orderedArray = new int[bitLen];
        int k = 0;
        for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1)) {
            orderedArray[k++] = i;
        }

        System.out.println("After ordering: ");
        for (int i = 0; i < bitLen; i++) {
            System.out.print(orderedArray[i] + "\t");
        }
        System.out.println("\n通过迭代器获取排序后的元素：");
        //或直接迭代BitSet中bit为true的元素
        for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1)) {
            System.out.print(i + "\t");
        }
        System.out.println("\n---------------------------");
    }
}
