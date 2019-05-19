package top.hiccup.algorithm;

import org.junit.Test;

/**
 * 二分查找：最简单基础的算法，要求给定的数组必须有序，而且元素不存在重复的情况
 *
 * 一般只能用在数组上（支持随机访问，而链表则不行）
 *
 * @author wenhy
 * @date 2019/5/1
 */
public class BinarySearch {

    /**
     * 递归二分查找
     */
    public static int binSearch(int[] arr, int left, int right, int target) {
        if(arr == null || left > right) {
            return -1;
        }
        int mid = (left + right)/2;
        if (arr[mid] == target) {
            return mid;
        } else if (arr[mid] > target) {
            return binSearch(arr, left, mid-1, target);
        } else {
            return binSearch(arr, mid+1, right, target);
        }
    }

    /**
     * 循环二分查找
     */
    public static int binSearch2(int[] arr, int target) {
        if (arr == null) {
            return -1;
        }
        int left = 0;
        int right = arr.length-1;
        while (left <= right) {
//             int mid = (left + right)/2;
            // 优化
//            int mid = (left + right) >> 1;
            // 再优化，防止left和right都很大的时候，计算移除
            int mid = left + ((right - left) >> 1);

            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] > target) {
                right = mid-1;
            } else {
                left = mid+1;
            }
        }
        return -1;
    }


    @Test
    public void test() {
        int[] arr = new int[]{1, 2, 5, 7, 9, 13, 17, 20};
        System.out.println(binSearch(arr, 0, arr.length-1, 5));
        System.out.println(binSearch2(arr, 5));
    }
}
