package top.hiccup.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * 常见数据结构（其实最终底层存储的时候只有数组和链表两种结构，其他结构都是通过算法来实现的）：
 *
 * 1、数组：线性表，随机访问
 *
 * 2、链表：线性表，支持快速插入和删除
 *
 * 3、栈：先进后出
 *
 * 4、队列：先进先出
 *
 * 5、散列表（哈希表）：哈希冲突：开放定址法、再哈希法、链地址法、公共溢出区法
 *
 * 6、二叉树：
 *
 * 7、树：
 *
 * 8、图：
 *
 * 常见算法思路：
 * 
 * 1、暴力法：最简单，最容易想到
 * 
 * 2、贪心法
 * 
 * 3、递归回溯法
 * 
 * 4、动态规划
 *
 * @author wenhy
 * @date 2019/5/14
 */
public class DataStructAndAlgorithm {


    public int missingNumber(int[] nums) {
        int n = nums.length;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i/4 == 0) {
                count -= n;
                count -= n;
            }
            count += nums[i];
        }
        return n - count;
    }
}
