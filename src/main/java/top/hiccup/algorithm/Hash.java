package top.hiccup.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * 哈希函数应用：
 * <p>
 * 哈希冲突：鸽巢原理：10个鸽巢，11只鸽子，必定存在冲突。
 * <p>
 * =============================================================================================================
 * Q: 解决hash冲突的常用方法？
 * <p>
 * 1、开放定址法：当key的哈希码hash = H(key)出现冲突时，再以hash为基础产生另外一个哈希码hash2 = H(hash)，以此类推直到不再重复
 * 也可“线性探测”，直接往后依此寻址找到空位即插入（删除时需要注意，一般标记为deleted，而不真正置空，防止其他查询搜索不到后面的结果）
 * <p>
 * 2、再哈希法: 同时提供多个不同的哈希函数H1，H2，H3...如果发生冲突，则hash=H2(key),hash=H3(key)...
 * <p>
 * 3、链地址法：思想同HashMap
 * <p>
 * 4、公共溢出区法：将哈希表分为基本表和溢出表，只要发生哈希冲突就直接填入溢出区（每次查找是不是都要查公共溢出区？）
 * <p>
 * =============================================================================================================
 * <p>
 * 1、安全加密（摘要、签名）：MD5、PBKDF2WithHmacSHA1
 * 其他加密算法：SHA、DES、AES、RSA
 * <p>
 * 2、唯一标识：HashMap中用来标识key，如果key的hash相同再判断equals
 * <p>
 * 3、数据校验（签名）
 * <p>
 * 4、掩码以及加盐salt（原始串加上固定的其他串）的散列函数
 * <p>
 * 5、负载均衡
 * <p>
 * 6、数据分片（MapReduce的思想）
 * <p>
 * 7、分布式缓存的一致性哈希算法（哈希环，虚节点）
 * <p>
 * 区块链：区块链是一块块区块组成的，每个区块分为两部分：区块头和区块体。
 * 区块头保存着 “自己区块体” 和 “上一个区块头” 的哈希值。
 * 因为这种链式关系和哈希值的唯一性，只要区块链上任意一个区块被修改过，后面所有区块保存的哈希值就不对了。
 * 区块链使用的是 SHA256 哈希算法，计算哈希值非常耗时，如果要篡改一个区块，就必须重新计算该区块后面所有的区块的哈希值，短时间内几乎不可能做到。
 *
 * @author wenhy
 * @date 2019/5/20
 */
public class Hash {

    class Solution {
        public int[] searchRange(int[] nums, int target) {
            int[] result = new int[]{-1, -1};
            if (nums == null || nums.length == 0) {
                return result;
            }
            // 二分查找具有重复值
            // 先找到最左边的值
            int left = 0, right = nums.length - 1;
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                if (target == nums[mid] && mid == 0) {
                    result[0] = 0;
                    break;
                }
                if (target == nums[mid] && target != nums[mid - 1]) {
                    result[0] = mid;
                    break;
                }
                if (target <= nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            // 再找到右边的值
            left = 0;
            right = nums.length - 1;
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                if (target == nums[mid] && mid == nums.length - 1) {
                    result[1] = nums.length - 1;
                    break;
                }
                if (target == nums[mid] && target != nums[mid + 1]) {
                    result[1] = mid;
                    break;
                }
                if (target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            return result;
        }
    }


    @Test
    public void test() {
        System.out.println(Arrays.toString(new Solution().searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8)));
    }
}
