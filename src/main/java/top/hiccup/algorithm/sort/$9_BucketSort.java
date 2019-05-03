package top.hiccup.algorithm.sort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 桶排序(Bucket Sort)：O(n) 稳定排序
 *
 * 桶排序的适用范围是，待排序的元素能够均匀分布在某一个范围[MIN, MAX]之间。
 * （数据结构为哈希表，类似HashMap，但链表是通过插入排序来保持有序的，插入排序为稳定排序，所以桶排序也是稳定的）
 *
 * @author wenhy
 * @date 2018/11/12
 */
public class $9_BucketSort {

    public static void sort(int[] arr) {
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
        List<Integer>[] buckets = new LinkedList[max - min + 1];
        for(int i = 0; i < buckets.length; i++){
            buckets[i] = new LinkedList<Integer>();
        }
        int bias = 0 - min;
        for(int i = 0; i < arr.length; i++){
            // 找到元素在桶中的位置，并将其添加（其实这里跟计数排序思想很类似）
            buckets[arr[i] + bias].add(arr[i]);
        }
        for(int i = 0, j = 0; i < buckets.length && j < arr.length; i++){
            for(int val : buckets[i]){
                arr[j] = val;
                j++;
            }
        }
    }


    /**
     * 如果指定了桶的大小则需要处理真正的hash冲突
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
