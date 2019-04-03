package top.hiccup.algorithm.sort;

import java.util.Random;

/**
 * 快速排序：
 * <p>
 * 采用分治的思想
 * <p>
 * 【优化一】（随机化快排）
 * 方法：随机选取元素与首（尾）元素交换，然后选取首（尾）元素为pivot
 * 意义：加强随机性，使得其成为一个真正的随机化算法，即使在元素全部逆序的情况下也能保持Θ(nlgn)的速度
 * <p>
 * 【优化二】（与插入排序结合）
 * 方法：当n≤k(k)时候，改用插入排序（O(n)~O(n²)）
 * 意义：减少递归的次数，且当数组"几乎有序"时，插入排序较快
 * <p>
 * 【优化三】（三数取中划分）
 * 方法：取数组的left,mid,right三个数的中位数作为pivot
 * 意义：增大"好的划分"的概率，最佳的划分是将待排序的序列分成等长的子序列，最佳的状态我们可以使用序列的中间的值，也就是第N/2个数。
 * <p>
 * 【优化四】（聚集重复元素）
 * 方法：
 * 意义：
 * <p>
 * 【优化五】（尾递归优化，编译器默认会做尾递归优化）
 * 方法：快排函数在函数尾部有两次递归操作，可以优化为一次递归，减少调用栈的深度
 * 意义：如果待排序的序列划分极端不平衡，递归的深度将趋近于n，而栈的大小是很有限的，
 * 每次递归调用都会耗费一定的栈空间，函数的参数越多，每次递归耗费的空间也越多。
 * 优化后，可以缩减堆栈深度，由原来的O(n)缩减为O(logn)，将会提高性能。
 *
 * @author wenhy
 * @date 2017/1/6
 */
public class QuickSort {

    /**
     * 基础快排实现
     */
    public static int partition(int[] arr, int left, int right) {
        int i = left, j = right;
        // 默认取最左边的元素为分割的基准元素（也可以取最右边的）
        int partition = arr[left];
        while (i < j) {
            while (i < j && partition <= arr[j]) {
                j--;
            }
            while (i < j && partition >= arr[i]) {
                i++;
            }
            // 交换arr[i]与arr[j]
            if (i < j) {
                swap(arr, i, j);
            }
        }
        swap(arr, left, i);
        // while结束时i==j
        return i;
    }

    public static void quickSort(int[] arr, int left, int right) {
        // 递归的出口，需要判断>=不能只判=
        if (left >= right) {
            return;
        }
        int pivot = partition(arr, left, right);
        quickSort(arr, left, pivot - 1);
        quickSort(arr, pivot + 1, right);
    }

    /**
     * 快排实现优化一：随机化快排
     */
    public static int randPartition(int[] arr, int left, int right) {
        int i = left, j = right;
        Random random = new Random();
        int pivot = random.nextInt(right) % (right - left) + left;
        swap(arr, left, pivot);
        return partition(arr, left, right);
    }

    public static void quickSortImprove(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int pivot = randPartition(arr, left, right);
        quickSortImprove(arr, left, pivot - 1);
        quickSortImprove(arr, pivot + 1, right);
    }

    /**
     * 快排实现优化二：插入排序 + 随机化快排
     */
    public static void quickSortImprove2(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        if (right - left < 7) {
            InsertionSort.sort(arr, left, right);
            return;
        }
        int pivot = randPartition(arr, left, right);
        quickSortImprove2(arr, left, pivot - 1);
        quickSortImprove2(arr, pivot + 1, right);
    }

    /**
     * 快排实现优化三：三数取中 + 插入排序
     */
    private static int medianPartition(int[] arr, int left, int right) {
        int median = left + (left + right) / 2;
        if (arr[median] > arr[right]) {
            swap(arr, median, right);
        }
        if (arr[left] > arr[right]) {
            swap(arr, left, right);
        }
        if (arr[left] > arr[median]) {
            swap(arr, left, median);
        }
        return partition(arr, left, right);
    }

    public static void quickSortImprove3(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        if (right - left < 7) {
            InsertionSort.sort(arr, left, right);
            return;
        }
        int pivot = medianPartition(arr, left, right);
        quickSortImprove3(arr, left, pivot - 1);
        quickSortImprove3(arr, pivot + 1, right);
    }

    /**
     * 快排实现优化四：聚集重复元素
     */
    public static void quickSortImprove4(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        if (right - left < 7) {
            InsertionSort.sort(arr, left, right);
            return;
        }
        int pivot = medianPartition(arr, left, right);
        quickSortImprove4(arr, left, pivot - 1);
        quickSortImprove4(arr, pivot + 1, right);
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
