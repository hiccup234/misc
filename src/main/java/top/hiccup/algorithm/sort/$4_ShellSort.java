package top.hiccup.algorithm.sort;

import static top.hiccup.algorithm.sort.SortTest.swap;

/**
 * 希尔排序：基于简单插入排序改进而来，本身还是插入排序的思想，O(nlogn)
 * 
 * 希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序；随着增量逐渐减少，
 * 每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止。
 * 
 * https://www.cnblogs.com/chengxiao/p/6104371.html
 *
 * @author wenhy
 * @date 2018/10/10
 */
public class $4_ShellSort {

    public static void sort(int[] arr) {
        // 增量gap，逐步缩小增量直至gap==1，这里的缩小速度是O(logn)
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            // 从gap个元素开始，逐个对其所在的分组进行直接插入排序
            for (int i = gap; i < arr.length; i++) {
                for (int j = i; j - gap >= 0 && arr[j] < arr[j - gap]; j -= gap) {
                    swap(arr, j, j - gap);
                }
            }
        }
    }
}
