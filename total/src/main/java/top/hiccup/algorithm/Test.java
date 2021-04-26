package top.hiccup.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 测试
 *
 * @author wenhy
 * @date 2019/6/2
 */
public class Test {
    private static void swap(int[] arr, int s, int t) {
        int tmp = arr[s];
        arr[s] = arr[t];
        arr[t] = tmp;
    }

    public void bubble(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    public void select(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int minIdx = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                swap(arr, minIdx, i);
            }
        }
    }

    public void insert(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int tmp = arr[i];
            int j = i;
            for (; j > 0; j--) {
                if (arr[j - 1] > tmp) {
                    arr[j] = arr[j - 1];
                } else {
                    break;
                }
            }
            arr[j] = tmp;
        }
    }


    public void heap(int[] arr) {
        int n = arr.length;
        int[] tmpArr = new int[n + 1];
        // 转换一下，方便计算
        System.arraycopy(arr, 0, tmpArr, 1, n);
        // 第一步，建堆（两种思路：1、初始堆只有一个元素，依此添加2~n的元素并调整 2、从最后一个非叶子节点（n/2）开始依此往上调整即可）
        for (int i = (n + 1) / 2; i > 0; i--) {
            int j = i;
            // 向下调整
            while (true) {
                int maxIdx = j;
                if (j * 2 <= n && tmpArr[j] < tmpArr[j * 2]) {
                    maxIdx = j * 2;
                }
                if (j * 2 + 1 <= n && tmpArr[maxIdx] < tmpArr[j * 2 + 1]) {
                    maxIdx = j * 2 + 1;
                }
                if (j == maxIdx) {
                    break;
                }
                swap(tmpArr, j, maxIdx);
                j = maxIdx;
            }
        }
        swap(tmpArr, 1, n);
        // 第二步，建堆后，堆顶元素则是最大元素
        for (int i = n - 1; i > 1; i--) {
            // 再次调整堆
            int j = 1;
            while (true) {
                int maxIdx = j;
                if (j * 2 <= i && tmpArr[j] < tmpArr[j * 2]) {
                    maxIdx = j * 2;
                }
                if (j * 2 + 1 <= i && tmpArr[maxIdx] < tmpArr[j * 2 + 1]) {
                    maxIdx = j * 2 + 1;
                }
                if (j == maxIdx) {
                    break;
                }
                swap(tmpArr, j, maxIdx);
                j = maxIdx;
            }
            swap(tmpArr, 1, i);
        }
        System.arraycopy(tmpArr, 1, arr, 0, n);
    }


    @org.junit.Test
    public void test() {
        int[] arr = new int[]{5, 2, 12, 7, 9};
//        bubble(arr);
//        select(arr);
//        insert(arr);
        heap(arr);
        System.out.println(Arrays.toString(arr));
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    class Solution {
        public char findTheDifference(String s, String t) {
            if (s == null || t == null) {
                throw new RuntimeException("invalid input");
            }
            char[] chars = t.toCharArray();
            Map<Character, Integer> map = new HashMap<>(32);
            for (char c : chars) {
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                } else {
                    map.put(c, 1);
                }
            }
            for (char c : s.toCharArray()) {
                Integer count = map.get(c);
                if (count != null && count > 1) {
                    map.put(c, count - 1);
                } else {
                    map.remove(c);
                }
            }
            return map.entrySet().iterator().next().getKey();
        }
    }

    @org.junit.Test
    public void test2() {
        Solution solution = new Solution();
//        System.out.println(solution.isPowerOfTwo(32));
    }
}