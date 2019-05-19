package top.hiccup.algorithm.sort;

import static top.hiccup.algorithm.sort.SortTest.swap;

/**
 * 堆排序：堆排序是一种选择排序，它的最坏，最好，平均时间复杂度均为O(nlogn)，不稳定排序算法
 * 
 * 堆排序的基本思想是：将待排序序列构造成一个大顶堆，此时，整个序列的最大值就是堆顶的根节点。将其与末尾元素进行交换，
 * 此时末尾就为最大值。然后将剩余n-1个元素重新构造成一个堆，这样会得到n个元素的次小值。如此反复执行，便能得到一个有序序列。
 * 
 * 最小堆、最大堆的特性：大顶堆：arr[i] >= arr[2i+1] && arr[i] >= arr[2i+2] 小顶堆：arr[i] <= arr[2i+1] && arr[i] <= arr[2i+2]
 * 
 * 1、构建初始堆
 * 2、交换堆顶元素和末尾元素并重建堆
 *
 * @author wenhy
 * @date 2018/10/18
 */
public class $7_HeapSort {

    /**
     * 调整堆结构
     *
     * @param arr
     * @param i
     * @param length
     */
    private static void adjustHeap(int[] arr, int i, int length) {
        int temp = arr[i];
        // 从i结点的左子结点开始，也就是2i+1处开始（这里将递归换成了for循环）
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            if (k + 1 < length && arr[k] < arr[k + 1]) {
                k++;
            }
            // 如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
            if (arr[k] > temp) {
                arr[i] = arr[k];
                i = k;
            } else {
                break;
            }
        }
        arr[i] = temp;
    }

    public static void sort(int[] arr) {
        // 1、构建大顶堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            // 从堆的最后一个非叶子结点从下至上，从右至左调整结构（参考TopKTest.test3）
            adjustHeap(arr, i, arr.length);
        }
        // 2、交换堆顶与末尾元素以及调整堆结构使n-i满足最大堆
        for (int j = arr.length - 1; j > 0; j--) {
            // 将堆顶元素与当前堆的末尾元素进行交换
            swap(arr, 0, j);
            adjustHeap(arr, 0, j);
        }
    }
}
