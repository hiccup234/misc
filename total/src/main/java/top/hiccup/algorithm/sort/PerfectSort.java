package top.hiccup.algorithm.sort;

import static top.hiccup.algorithm.sort.SortTest.swap;

/**
 * 完美排序算法：
 * 
 * 《算法导论》习题中的“完美排序”，由Howard、Fine等几个教授提出，之所以称为“完美排序”，是因为其代码实现，优雅、工整、漂亮。
 * 
 *  但是其时间复杂度是O(n^2.7)，比O(n^2)的排序都还要慢一些。
 *
 * @author wenhy
 * @date 2018/11/12
 */
public class PerfectSort {

    public static void sort(int arr[], int i, int j) {
        // 比较并替换
        if (arr[i] > arr[j]) {
            swap(arr, i, j);
        }
        // 是否结束
        if (i + 1 >= j) {
            return;
        }
        // 三等分
        int k = (j - i + 1) / 3;
        sort(arr, i, j - k);
        sort(arr, i + k, j);
        sort(arr, i, j - k);
    }
}
