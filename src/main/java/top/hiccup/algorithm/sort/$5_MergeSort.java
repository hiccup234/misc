package top.hiccup.algorithm.sort;

import java.util.Arrays;

/**
 * 归并排序：采用分治的思想（特别适合超大规模数据，非本地稳定排序）
 *
 * @see https://www.cnblogs.com/chengxiao/p/6194356.html
 *
 * @author wenhy
 * @date 2018/10/18
 */
public class $5_MergeSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 归并排序辅助空间，与原数组等长，不是每次把辅助空间用完
        int[] tempArr = new int[arr.length];
        doSort(arr, 0, arr.length - 1, tempArr);
    }

    private static void doSort(int[] arr, int left, int right, int[] tempArr) {
        // 递归出口
        if (left < right) {
            int mid = (left + right) / 2;
            // 左边归并排序，使得左子序列有序
            doSort(arr, left, mid, tempArr);
            // 右边归并排序，使得右子序列有序
            doSort(arr, mid + 1, right, tempArr);
            // 然后将两个有序子数组合并成一个数组
            merge(arr, left, mid, right, tempArr);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right, int[] tempArr) {
        int i = left;
        int j = mid + 1;
        // 注意这里是从下标0开始
        int t = 0;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                tempArr[t++] = arr[i++];
            } else {
                tempArr[t++] = arr[j++];
            }
        }
        // 将左边剩余元素填充进temp中
        while (i <= mid) {
            tempArr[t++] = arr[i++];
        }
        // 将右序列剩余元素填充进temp中
        while (j <= right) {
            tempArr[t++] = arr[j++];
        }
        // 将temp中的元素全部拷贝到原数组中
        System.arraycopy(tempArr, 0, arr, left, right - left + 1);
    }


    //==================================================================================================================
    /**
     * 更清晰的描述，但是会浪费更多的空间，所以只用来描述算法
     * 注意：回参才是排好序的数组，入参数组不会修改
     */
    public static int[] sort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return arr;
        }
        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);
        return merge2(sort2(left), sort2(right));
    }

    private static int[] merge2(int[] left, int[] right) {
        int[] ret = new int[left.length + right.length];
        for (int p = 0, i = 0, j = 0; p < ret.length; p++) {
            if (i >= left.length) {
                ret[p] = right[j++];
            } else if (j >= right.length) {
                ret[p] = left[i++];
            } else if (left[i] < right[j]) {
                ret[p] = left[i++];
            } else {
                ret[p] = right[j++];
            }
        }
        return ret;
    }
}
