package top.hiccup.algorithm.problem;

import org.junit.Test;

/**
 * 求最大子数组和：
 * <p>
 * 一个整数数组中的元素有正有负，在该数组中找出一 个连续子数组，要求该连续子数组中各元素的和最大，这个连续子数组便被称作最大连续子数组。
 * <p>
 * 比如数组{2,4,-7,5,2,-1,2,-4,3}的最大连续子数组为{5,2,-1,2}，最大连续子数组的和为5+2-1+2=8。
 * <p>
 * 问题输入就是一个数组，输出该数组的“连续子数组的最大和”。
 *
 * @author wenhy
 * @date 2019/4/26
 */
public class MaxSubsequenceSumTest {

    /**
     * 1、最简单的暴力法，三层循环，时间复杂度O(n^3)
     */
    public static int maxSubSeqSum1(int[] arr) {
        int curSum = 0, maxSum = 0, len = arr.length;
        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                curSum = 0;
                // 这一层其实可以撤销，由i到j的过程在第二次循环已经有了
                for (int k = i; k <= j; k++) {
                    curSum += arr[k];
                }
                if (curSum > maxSum) {
                    maxSum = curSum;
                }
            }
        }
        return maxSum;
    }

    /**
     * 2、暴力法撤销一层循环
     */
    public static int maxSubSeqSum2(int[] arr) {
        int curSum = 0, maxSum = 0, len = arr.length;
        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                curSum = 0;
                curSum += arr[j];
                if (curSum > maxSum) {
                    maxSum = curSum;
                }
            }
        }
        return maxSum;
    }

    /**
     * 3、分治法：
     * 因为最大子序列和可能在三处出现，整个出现在数组左半部，或者整个出现在右半部，又或者跨越中间，占据左右两半部分。
     * 递归将左右子数组再分别分成两个数组，直到子数组中只含有一个元素，退出每层递归前，返回上面三种情况中的最大值。
     */
    public static int maxSubSeqSum3(int[] arr) {

        return 0;
    }

    /**
     * 4、动态规划
     */
    public static int maxSubSeqSum4(int[] arr) {
        int maxSum = 0;
        int curSum = 0;
        for (int i = 0; i < arr.length; i++) {
            curSum += arr[i];
            if (curSum > maxSum) {
                maxSum = curSum;
            } else if (curSum < 0) {
                // 按照动态规划的思路：如果累加的和出现了小于0的情况，则和最大的子序列肯定不可能包含累加和小于0的这些元素，
                // 这时将累加和置0，从下个元素重新开始累加即可。
                curSum = 0;
            }
        }
        return maxSum;
    }

    @Test
    public void test() {
        int[] arr = new int[]{2, 4, -7, 5, 2, -1, 2, -4, 3};
        System.out.println(maxSubSeqSum1(arr));
        System.out.println(maxSubSeqSum2(arr));
        System.out.println(maxSubSeqSum3(arr));
        System.out.println(maxSubSeqSum4(arr));
    }
}
