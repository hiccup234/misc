package top.hiccup.algorithm.sort;

import static top.hiccup.algorithm.sort.SortTest.swap;

/**
 * 选择排序：每一趟从待排序的数据元素中选择最小（或最大）的一个元素作为首元素，直到所有元素排完为止，
 * 简单选择排序是不稳定排序（将当前要插入位置的元素与剩下最小的交换）
 *
 * 注意：选择排序最好情况的复杂度也是O(n^2)
 *
 * @author wenhy
 * @date 2018/10/10
 */
public class $2_SelectSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            int min = arr[i];
            int idx = i;
            for (int j = i; j < arr.length; j++) {
                if (min > arr[j]) {
                    min = arr[j];
                    idx = j;
                }
            }
            int t = arr[i];
            arr[i] = min;
            arr[idx] = t;
        }
    }

    /**
     * 更简洁的写法
     *
     * @param arr
     */
    public static void sort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                swap(arr, i, minIdx);
            }
        }
    }
}
