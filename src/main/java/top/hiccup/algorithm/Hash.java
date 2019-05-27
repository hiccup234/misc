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


    //    Definition for singly-linked list.
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    class Solution {
        public ListNode mergeKLists(ListNode[] lists) {
            if (lists == null || lists.length == 0) {
                return null;
            }
            int n = lists.length;
            int[] idxs = new int[n];
            Arrays.fill(idxs, 0);
            ListNode dummy = new ListNode(0);
            ListNode tail = dummy;
            int minIdx;
            while ((minIdx = getMinIdx(lists)) != -1) {
                ListNode minNode = lists[minIdx];
                tail.next = minNode;
                lists[minIdx] = lists[minIdx].next;
            }
            return dummy.next;
        }

        private int getMinIdx(ListNode[] lists) {
            int n = lists.length;
            // 取得当前最小值，如果都为空则返回-1
            int minIdx = -1;
            int min = Integer.MAX_VALUE;
            for (int i = n-1; i >= 0; i--) {
                if (lists[i] != null && lists[i].val <= min) {
                    minIdx = i;
                    min = lists[i].val;
                }
            }
            return minIdx;
        }
    }

    @Test
    public void test() {
    }
}
