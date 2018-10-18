package top.hiccup.arithmetic.sort;

import java.util.Arrays;

/**
 * 排序实现方式：
 *
 * 1.冒泡排序：稳定的排序算法，不会打乱原有相同元素位置
 *
 * 2.选择排序
 *
 * 3.简单插入排序：
 *
 * 4.希尔排序：
 *
 * 3.快速排序
 *
 * 4.堆排序
 *
 * 5.归并排序、桶排序、基数排序
 *
 * @author wenhy
 * @date 2018/9/26
 */
public class SortTest {

    public static void main(String[] args) {
        int[] data = {9,275,12,6,45,999,41,12306,456,12,532,89};
//        BubbleSort.sort(data, data.length);
//        InsertionSort.sort(data, data.length);
//        SelectSort.sort(data, data.length);
//        RadixSort.sort(data, data.length);
//        RadixSort.sort2(data, data.length);
//        ShellSort.sort(data, data.length);
//        HeapSort.sort(data, data.length);
        MergeSort.sort(data, data.length);

        for (int i=0; i<data.length; i++) {
            System.out.println(data[i]);
        }
    }
}
