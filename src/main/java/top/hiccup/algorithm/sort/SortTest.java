package top.hiccup.algorithm.sort;

/**
 * 排序实现方式：
 * <p>
 * 1.冒泡排序：稳定的排序算法，不会打乱原有相同元素位置
 * <p>
 * 2.选择排序：
 * <p>
 * 3.简单插入排序：
 * <p>
 * 4.希尔排序：
 * <p>
 * 3.快速排序
 * <p>
 * 4.堆排序
 * <p>
 * 5.归并排序、桶排序、基数排序
 * <p>
 * 时间复杂度为O(n)的排序，常见的有三种：
 * 基数排序(Radix Sort)
 * 计数排序(Counting Sort)
 * 桶排序(Bucket Sort)
 *
 * Algorithm	Average Time	Worst Time	    Space
 * 冒泡排序	        n^2	            n^2	        1
 * 选择排序	        n^2	            n^2	        1
 * Insertion sort	n^2	            n^2
 * Counting Sort    n+k	            n+k	        n+k
 * Quick sort	    n log(n)	    n^2
 * Merge sort	    n log(n)	    n log(n)	depends
 *
 * @author wenhy
 * @date 2018/9/26
 */
public class SortTest {

    public static void main(String[] args) {
        int[] data = {9, 275, 12, 6, 45, 999, 41, 12306, 456, 12, 532, 89};
//        BubbleSort.sortImprove(data, data.length);
//        InsertionSort.sort(data, 0, data.length-1);
//        SelectSort.sort(data, data.length);
//        RadixSort.sort(data, data.length);
//        RadixSort.sort2(data, data.length);
//        ShellSort.sort(data, data.length);
//        HeapSort.sort(data, data.length);
//        MergeSort.sort(data, data.length);
//        QuickSort.quickSort(data, 0, data.length-1);
//        QuickSort.quickSortImprove(data, 0, data.length-1);
//        QuickSort.quickSortImprove2(data, 0, data.length-1);
//        QuickSort.quickSortImprove3(data, 0, data.length-1);

        PerfectSort.sort(data, 0, 11);
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i]);
        }
    }
}
