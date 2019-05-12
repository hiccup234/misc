package top.hiccup.algorithm;

import java.util.HashMap;

import org.junit.Test;

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

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] ret = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            int max = nums1[i];
            for (int j = 1; j < nums2.length; j++) {
                if (nums2[j] == nums1[i]) {
                    for (int k = j+1; k < nums2.length; k++) {
                        if (nums2[k] > max) {
                            max = nums2[j];
                            ret[i] = nums2[j];
                            break;
                        }
                    }
                    if (max == nums1[i]) {
                        ret[i] = -1;
                    }
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {
//        System.out.println(calculate("(1+(4+5+2)-3)+(6+8)"));
//        System.out.println(calculate(" 2-1 + 2 "));

//        System.out.println(calPoints(new String[]{"5", "2", "C", "D", "+"}));
    }
}

