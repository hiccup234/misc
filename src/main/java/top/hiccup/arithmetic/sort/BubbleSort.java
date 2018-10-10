package top.hiccup.arithmetic.sort;

/**
 * 冒泡排序
 *
 * @author wenhy
 * @date 2018/10/9
 */
public class BubbleSort {

    public static void sort(int[] arr, int n) {
        int temp;
        for (int i=0; i<n; i++) {
            // 这里需要注意边界问题，已经冒过就不用再遍历了
            for (int j=0; j<n-i-1; j++) {
                if (arr[j] > arr[j+1]) {
                    temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    /**
     * 增加一个标记，如果整个一趟冒下来都没有交换元素，则说明此时整个数组已经有序
     * @param arr
     * @param n
     */
    public static void sortImprove(int[] arr, int n) {
        int temp;
        boolean flag;
        for (int i=0; i<n; i++) {
            flag = true;
            for (int j=0; j<n-i-1; j++) {
                if (arr[j] > arr[j+1]) {
                    temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
    }
}
