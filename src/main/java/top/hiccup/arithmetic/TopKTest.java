package top.hiccup.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 面试常见的TopK问题:
 *
 * 1.直接排序 O(n*lg(n))
 *   几种排序法都可以，排序后直接取前k个元素即是TopK
 *
 * 2.局部排序 O(n*k)
 *   只对最大（小）的k个元素排序，用冒泡的思想，冒出前k个元素
 *
 * 3.堆 O(n*lg(k))
 *   n个元素扫一遍，假设运气很差，每次都入堆调整，调整时间复杂度为堆的高度，即lg(k)，故整体时间复杂度是n*lg(k)
 * 4.
 *
 * 5.bitmap位图法（受限于数组值域范围以及是否有重复值）
 *
 * 6.bitmap位图法改进（允许有重复值）
 *
 * @author wenhy
 * @date 2018/1/21
 */
public class TopKTest {

    static int[] test1(int[] arr, int k) {
        int[] topk = new int[k];
        Arrays.sort(arr);
        System.arraycopy(arr, arr.length - k, topk, 0, k);
        List list = new ArrayList<>();
        return topk;
    }

    static int[] test2(int[] arr, int k) {
        int[] topk = new int[k];
        // 向后冒泡
        int temp;
        for (int i = 0; i < k && i < arr.length-1; i++) {
            for (int j = 0; j < arr.length-i-1; j++) {
                if (arr[j] > arr[j+1]) {
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        System.arraycopy(arr, arr.length - k, topk, 0, k);
//        // 选择排序
//        int max, temp;
//        for (int i = 0; i < k; i++) {
//            max = i;
//            for (int j = i + 1; j < arr.length; j++) {
//                if (arr[max] < arr[j]) {
//                    max = j;
//                }
//            }
//            if (max != i) {
//                temp = arr[max];
//                arr[max] = arr[i];
//                arr[i] = temp;
//            }
//        }
//        System.arraycopy(arr, 0, topk, 0, k);
        return topk;
    }

    private static void makeHeap(int[] arr, int k) {

    }
    static int[] test3(int[] arr, int k) {
        int[] topk = new int[k];
        System.arraycopy(arr, 0, topk, 0, k);
        // 创建最小堆
        makeHeap(topk, k);

        return topk;
    }


    public static void main(String[] args) {
        int[] arr = {9, 7, 2, 5, 8, 4, 3, 7, 3, 9};
        int[] topk;

//        topk = test1(arr, 3);
        topk = test2(arr, 4);
        Arrays.stream(topk).forEach(System.out::println);
    }




}
