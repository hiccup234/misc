package top.hiccup.algorithm.sort;

/**
 * 排序实现方式（一般有三个层次的排序算法）：
 * 
 * 第一层次：冒泡，选择，插入            O(n^2)
 *
 * 第二层次：快排、归并                 O(nlogn)
 *
 * 第三层次：桶排序、计数排序、基数排序    O(n)
 *
 * 
 * 【排序算法选择考虑问题】
 * 1、有没有可能包含有大量重复的元素？
 *      三路快排是更好地选择，Java默认排序就是三路快排
 * 2、是否大部分数据距离它正确的位置很近？是否近乎有序？
 *      插入排序（快排在数组小于7左右一般也改成插入排序）
 * 3、是否数据的取值范围非常有限？比如对学生高考成绩排序？
 *      桶排序，计数排序，基数排序（时间复杂度都是O(n)）
 * 4、是否需要稳定排序？
 *      冒泡，插入，归并，桶，计数，基数    （选择，快排，希尔，堆排序 不稳定）
 * 5、是否是使用链表存储的？
 *      归并排序（快排依赖于数组的随机存取）
 * 6、数据的大小是否可以装载在内存里？
 *      外排序算法（归并）
 * 
 * @author wenhy
 * @date 2018/9/26
 */
public class SortTest {

    public static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static void print(int[] arr) {
        for (int n : arr) {
            System.out.println(n);
        }
    }

    public static void main(String[] args) {
        int[] data = {49, 275, 12, 6, 45, 999, 41, 12306, 456, 12, 532, 89};

//        $1_BubbleSort.sortImprove(data);
//        $1_BubbleSort.sort2(data);

//        $2_SelectSort.sort(data);

//        $3_InsertionSort.sort(data, 0, data.length-1);

//        $4_ShellSort.sort(data);

//        $5_MergeSort.sort(data);
//        data = $5_MergeSort.sort2(data);

        $6_QuickSort.quickSort(data, 0, data.length-1);
//        $6_QuickSort.quickSortImprove(data, 0, data.length-1);
//        $6_QuickSort.quickSortImprove2(data, 0, data.length-1);
//        $6_QuickSort.quickSortImprove3(data, 0, data.length-1);

//        $7_HeapSort.sort(data);

//        $8_CountingSort.sort(data);

//        $9_BucketSort.sort(data);
//        $9_BucketSort.sort(data, 3);

//        $10_RadixSort.sort(data);
//        $10_RadixSort.sort2(data);

//        PerfectSort.sort(data, 0, 11);

        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + " ");
        }
    }
}