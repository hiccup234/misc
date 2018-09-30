package top.hiccup.arithmetic.sort;

/**
 * 快速排序的几种实现：
 *
 * 分治的思想
 *
 * 非稳定排序
 *
 * @author wenhy
 * @date 2017/1/6
 */
public class QuickSort {

    /**
     * 基础快排实现
     * @param arr
     * @param left
     * @param right
     */
    public static void quickSort(int[] arr, int left, int right) {
        // 递归的出口
        if (left >= right) {
            return ;
        }
        int i = left, j = right;
        // 默认取最左边的元素为分割的基准元素
        int partition = arr[left];
        int temp;
        while (i < j) {
            while (i < j && partition <= arr[j]) {
                j--;
            }
            while (i < j && partition >= arr[i]) {
                i++;
            }
            // 交换arr[i]与arr[j]
            if (i < j) {
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        arr[left] = arr[i];
        arr[i] = partition;
        quickSort(arr, left, i - 1);
        quickSort(arr, i + 1, right);
    }

    /**
     * 快排实现优化，
     * @param arr
     * @param left
     * @param right
     */
    public static void quickSort2(int[] arr, int left, int right) {
        if (left >= right) {
            return ;
        }
        int i = left, j = right;
        int temp = arr[left];
        while (i < j) {
            while (i < j && temp <= arr[j]) {
                j--;
            }
            arr[i] = arr[j];
            while (i < j && temp >= arr[i]) {
                i++;
            }
            arr[j] = arr[i];
        }
        arr[i] = temp;
        quickSort2(arr, left, i - 1);
        quickSort2(arr, i + 1, right);
    }


    public static void main(String[] args) {

    }

 }
