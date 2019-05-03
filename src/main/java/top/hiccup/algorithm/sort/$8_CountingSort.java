package top.hiccup.algorithm.sort;

import java.util.Arrays;

/**
 * 计数排序：计数排序的核心在于将输入的数据值转化为键存储在额外开辟的数组空间中。（有点bitmap位图的味道）
 *
 * O(n) 稳定的排序 计数排序要求输入的数据必须是有确定范围的整数。
 *
 * 计数排序使用一个额外的数组C，其中第i个元素是待排序数组arr中值等于i的元素的个数。然后根据数组C来将A中的元素排到正确的位置，只能对整数进行排序。
 *
 * @author wenhy
 * @date 2019/5/3
 */
public class $8_CountingSort {

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
        // 如果min与max之间的距离很大且分散得很开，则计数排序会浪费非常多空间，考虑最大的重复数，可以以换成byte,short等
        int[] bucket = new int[max - min + 1];
        Arrays.fill(bucket, 0);
        // 考虑负数
        int bias = 0 - min;
        for (int i = 0; i < arr.length; i++) {
            bucket[arr[i] + bias]++;
        }
        for (int i = 0, p = 0; i < arr.length; ) {
            if (bucket[p] != 0) {
                arr[i] = p - bias;
                bucket[p]--;
                i++;
            } else {
                p++;
            }
        }
    }
}
