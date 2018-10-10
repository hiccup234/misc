package top.hiccup.arithmetic.sort;

/**
 * 选择排序
 *
 * 每一趟从待排序的数据元素中选择最小（或最大）的一个元素作为首元素，直到所有元素排完为止，简单选择排序是不稳定排序
 *
 * @author wenhy
 * @date 2018/10/10
 */
public class SelectSort {

    public static void sort(int[] arr, int n) {
        for (int i=0; i<n; i++) {
            int minIndex = i;
            for (int j=i+1; j<n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }
}
