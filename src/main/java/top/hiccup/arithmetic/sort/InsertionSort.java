package top.hiccup.arithmetic.sort;

/**
 * 直接插入排序：向一个有序的数组中插入元素
 *
 * @author wenhy
 * @date 2018/10/9
 */
public class InsertionSort {

    public static void sort(int[] arr, int n) {
         for (int i=1; i<n; i++) {
            int temp = arr[i];
            int j = i;
            // 这里直接跟arr[i]做比较，而不是arr[j]与arr[j-1]比较，
            // 这样可以每次比较时不用swap，也更符合插入的思想（如果挨个挨个比较和替换就更像是冒泡了）
            for ( ; j>0 && temp<arr[j-1]; j--) {
                arr[j] = arr[j-1];
            }
            arr[j] = temp;
        }
    }
}
