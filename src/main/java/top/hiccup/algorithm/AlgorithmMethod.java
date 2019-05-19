package top.hiccup.algorithm;

/**
 * 算法思路
 * <p>
 * 1、暴力法 -- 最简单
 * <p>
 * 2、贪心法
 * <p>
 * 3、递归回溯法
 * <p>
 * 4、动态规划
 * <p>
 * 5、
 *
 * @author wenhy
 * @date 2019/4/3
 */
public class AlgorithmMethod {
}


class Solution {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (nums[mid] == target) {
                return mid;
            }
            // 如果已经是有序数组
            if (nums[left] <= nums[right]) {
                if (nums[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else {
                // mid的左边是有序的，且target在左边
                if (nums[left] <= target && target <= nums[mid]) {
                    right = mid - 1;
                } else if (nums[mid] <= target && target <= nums[right]) {
                    left = mid + 1;
                } else if (nums[mid] <= nums[right]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{4,5,6,7,0,1,2};
        Solution solution = new Solution();
        System.out.println(solution.search(nums, 0));
    }
}