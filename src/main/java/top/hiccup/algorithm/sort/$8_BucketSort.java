package top.hiccup.algorithm.sort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 桶排序(Bucket Sort)：O(n) 稳定排序
 *
 * 桶排序的适用范围是，待排序的元素能够均匀分布在某一个范围[MIN, MAX]之间，极端情况，数据分布在一个桶中，则算法退化为该桶的排序算法的时间复杂度。
 * （数据结构为哈希表，类似HashMap，但链表是通过插入排序来保持有序的，插入排序为稳定排序，所以桶排序也是稳定的）
 *
 * @author wenhy
 * @date 2018/11/12
 */
public class $8_BucketSort {

    /**
     * 指定了单桶的大小，则需要处理可能存在的hash冲突
     */
    public static void sort(int[] arr, int bucketSize) {
        if (arr == null || arr.length < 2) {
            return ;
        }
        int min =  arr[0], max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
            if (arr[i] > max){
                max = arr[i];
            }
        }
        // 计算得出桶的个数
        int bucketCount = (max - min) / bucketSize + 1;
        List<Integer>[] buckets = new ArrayList[bucketCount];
        for(int i = 0; i < buckets.length; i++){
            buckets[i] = new ArrayList<Integer>();
        }
        for(int i = 0; i < arr.length; i++){
            buckets[(arr[i] - min) / bucketSize].add(arr[i]);
        }
        int p = 0;
        for(int i = 0; i < buckets.length; i++){
            Integer[] tmp = buckets[i].toArray(new Integer[buckets[i].size()]);
            int[] tmpArr = new int[tmp.length];
            for (int k = 0; k < tmp.length; k++) {
                tmpArr[k] = tmp[k];
            }
            $3_InsertionSort.sort(tmpArr, 0, tmp.length-1);
            for (int k = 0; k < tmpArr.length; k++) {
                arr[p++] = tmpArr[k];
            }
        }
    }
}
