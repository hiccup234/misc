package top.hiccup.arithmetic.sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 基数排序：
 *
 * 在学校当课代表收作业本的时候，学号末尾两位数（位数很少，非常适合基数排序）就像无序的数组，打乱的，要重新按小到大整理，可以采用基数排序的思想
 *
 * 1、基：被排序的元素的“个位”“十位”“百位”，暂且叫“基”
 * 2、桶：每个“基”都有一个取值范围，如采用十进制表示的int的基的取值范围是0~9，所以需要10个桶（bucket），来存放被排序的元素
 *
 * 基数排序的时间复杂度可以近似认为是 O(n)
 *  O(k) * (O(n) + O(k))
 *
 * @author wenhy
 * @date 2018/10/12
 */
public class RadixSort {

    public static void sort(int[] arr, int n) {
        // 一般int采用十进制表示，这里采用二维数组定义10个桶，如果数据量特别大的话会比较浪费空间
        int[][] buckets = new int[10][arr.length];
        // 统计每个桶中存放的元素个数
        int[] counts = new int[10];
        // 先从个位开始
        int divisor = 1;
        // 一个int的取值范围为-2^31~2^31-1（-2147483648~2147483647）对应10个基，需要循环10趟
        for (int i=0; i<10; i++) {
            // 第一步：遍历元素，放入对应的桶
            for (int j=0; j<arr.length; j++) {
                int radix = (arr[j]/divisor)%10;
                buckets[radix][counts[radix]++] = arr[j];
            }
            // 第二步：遍历桶，放回数组，先入桶的先出桶（很重要）
            int index = 0;
            for (int k=0; k<10; k++) {
                if (counts[k] == 0) {
                    continue;
                }
                for (int m=0; m<counts[k]; m++) {
                    arr[index++] = buckets[k][m];
                }
                // 桶中所有元素已经全部取出来了，记得要把基数归0
                counts[k] = 0;
            }
            divisor *= 10;
        }
    }

    public static void sort2(int[] arr, int n) {
        List<List<Integer>> buckets = new ArrayList<>(10);
        // 这里基的个数可以先做一次选择排序，找出最大值，则最大基个数就可以确定了
        for (int i=0; i<10; i++) {
            buckets.add(new LinkedList<>());
        }
        for (int i=0; i<10; i++) {
            buckets.clear();
            for (int m=0; m<10; m++) {
                // TODO 这里不能直接调用clear，参考方法上的注释，考虑到gc问题，这里直接new
//                buckets.get(i).clear();
//                buckets.set(i, new LinkedList<>());
                buckets.add(new LinkedList<>());
            }
            // 第一步：遍历元素，放入对应的桶
            for (int j =0; j<arr.length; j++) {
                List<Integer> bucket = buckets.get(getRadix(arr[j], i));
                bucket.add(arr[j]);
            }
            // 第二步：遍历桶，放回数组，先入桶的先出桶（很重要）
            int index = 0;
            for (List<Integer> bucket : buckets) {
                if (null == bucket) {
                    continue;
                }
                for (Iterator<Integer> itr=bucket.iterator(); itr.hasNext();) {
                    arr[index++] = itr.next();
                }
            }
        }
    }

    /**
     * 获取val对应的第i个基
     * @param val 待排序元素
     * @param i 从个位开始
     * @return
     */
    private static int getRadix(int val, int i) {
//        int pow = (int)Math.pow(10, i);
        int pow = 1;
        for (int k = 1; k <= i; k++) {
            pow = pow*10;
        }
        return (val/pow)%10;
    }
}
