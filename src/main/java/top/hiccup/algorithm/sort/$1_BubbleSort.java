package top.hiccup.algorithm.sort;

import static top.hiccup.algorithm.sort.SortTest.swap;

/**
 * 冒泡排序：O(n^2) 稳定排序（稳定：如果a原本在b前面，而a==b，排序之后a仍然在b的前面）
 *
 * @author wenhy
 * @date 2018/10/9
 */
public class $1_BubbleSort {

    /**
     * 顺序往后冒泡
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return ;
        }
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            // 这里需要注意边界问题，已经冒过就不用再遍历了，不然浪费计算资源
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    /**
     * 逆序往前冒泡
     */
    public static void sort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return ;
        }
        int n = arr.length;
        for (int i = n-1; i >= 0; i--) {
            for (int j = n-1; j > n - i - 1; j--) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                }
            }
        }
    }

    /**
     * 增加一个标记，如果整个一趟冒下来都没有交换元素，则说明此时整个数组已经有序
     * 最好情况下：O(n)
     */
    public static void sortImprove(int[] arr) {
        if (arr == null || arr.length < 2) {
            return ;
        }
        int n = arr.length;
        boolean flag;
        for (int i = 0; i < n; i++) {
            flag = true;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
    }
}
