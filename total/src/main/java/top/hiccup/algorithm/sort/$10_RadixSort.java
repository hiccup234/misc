package top.hiccup.algorithm.sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 基数排序：非比较的稳定的排序算法，按每一个位数来排序（要求对每个基排序都是稳定的，不然对后面的基排序无法保证前面已经排过序的基的顺序）
 *         要求数据可以分成高低位，位之间有递进关系。比较完高位就不用比较低位了，同位的排序可以采用桶或者计数排序
 *
 * 在学校当课代表收作业本的时候，学号末尾两位数（位数很少，非常适合基数排序）就像无序的数组，打乱的，要重新按小到大整理，可以采用基数排序的思想
 * 
 * 1、基：被排序的元素的“个位”“十位”“百位”，暂且叫“基”
 * 2、桶：每个“基”都有一个取值范围，如采用十进制表示的int的基的取值范围是0~9，所以需要10个桶（bucket），来存放被排序的元素
 * 
 * 基数排序的时间复杂度可以近似认为是 O(n)
 * O(k) * (O(n) + O(k)) k为桶的个数
 *
 * @author wenhy
 * @date 2018/10/12
 */
public class $10_RadixSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return ;
        }
        // 一般int采用十进制表示，这里采用二维数组定义10个桶，如果数据量特别大的话会比较浪费空间（可以考虑改成动态数组）
        int[][] buckets = new int[10][arr.length];
        // 统计每个桶中存放的元素个数
        int[] counts = new int[10];
        // 先从个位开始
        int divisor = 1;
        // 一个int的取值范围为-2^31~2^31-1（-2147483648~2147483647）对应10个基，需要循环10趟
        // 如果先循环一遍找出最大数，则最大的基个数就固定了，而不用整个遍历10遍
        for (int i = 0; i < 10; i++) {
            // 第一步：遍历元素，放入对应的桶
            for (int j = 0; j < arr.length; j++) {
                int radix = (arr[j] / divisor) % 10;
                buckets[radix][counts[radix]++] = arr[j];
            }
            // 第二步：遍历桶，放回数组，先入桶的先出桶（很重要）
            int p = 0;
            for (int k = 0; k < 10; k++) {
                if (counts[k] == 0) {
                    continue;
                }
                for (int m = 0; m < counts[k]; m++) {
                    arr[p++] = buckets[k][m];
                }
                // 桶中所有元素已经全部取出来了，记得要把基数归0
                counts[k] = 0;
            }
            divisor *= 10;
        }
    }

    public static void sort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return ;
        }
        List<List<Integer>> buckets = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            buckets.add(new LinkedList<>());
        }

        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        int maxRadix = 0;
        // 负数最后也会到0
        while (max != 0) {
            max /= 10;
            maxRadix++;
        }
        // 这里先做了一次循环遍历，找出最大值，则最大基个数就可以确定了
        for (int i = 0; i < maxRadix; i++) {
            // 第一步：遍历元素，放入对应的桶
            for (int j = 0; j < arr.length; j++) {
                List<Integer> bucket = buckets.get(getRadix(arr[j], i));
                bucket.add(arr[j]);
            }
            // 第二步：遍历桶，放回数组，先入桶的先出桶（很重要）
            int index = 0;
            for (List<Integer> bucket : buckets) {
                if (null == bucket) {
                    continue;
                }
                Iterator<Integer> itr = bucket.iterator();
                while (itr.hasNext()) {
                    arr[index++] = itr.next();
                    // 这里出桶后要记得删除元素
                    itr.remove();
                }
            }
        }
    }

    /**
     * 获取val对应的第i个基
     *
     * @param val 待排序元素
     * @param i   从个位开始
     * @return
     */
    private static int getRadix(int val, int i) {
//        int pow = (int)Math.pow(10, i);
        int pow = 1;
        for (int k = 1; k <= i; k++) {
            pow = pow * 10;
        }
        return (val / pow) % 10;
    }
}
