package top.hiccup.algorithm.sort;

/**
 * 归并排序：
 * <p>
 * 采用分治的思想（特别适合超大规模数据，非本地排序）
 * <p>
 * https://www.cnblogs.com/chengxiao/p/6194356.html
 *
 * @author wenhy
 * @date 2018/10/18
 */
public class MergeSort {

    public static void sort(int[] arr, int n) {
        // 归并排序辅助空间
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
        t = 0;
        // 将temp中的元素全部拷贝到原数组中
        while (left <= right) {
            arr[left++] = tempArr[t++];
        }
    }
}
