package top.hiccup.algorithm;

import java.util.Arrays;

import org.junit.Test;

/**
 * 哈希函数应用：
 *
 * 哈希冲突：鸽巢原理：10个鸽巢，11只鸽子，必定存在冲突。
 *
 * =============================================================================================================
 * Q: 解决hash冲突的常用方法？
 *
 *  1、开放定址法：当key的哈希码hash = H(key)出现冲突时，再以hash为基础产生另外一个哈希码hash2 = H(hash)，以此类推直到不再重复
 *              也可“线性探测”，直接往后依此寻址找到空位即插入（删除时需要注意，一般标记为deleted，而不真正置空，防止其他查询搜索不到后面的结果）
 *
 *  2、再哈希法: 同时提供多个不同的哈希函数H1，H2，H3...如果发生冲突，则hash=H2(key),hash=H3(key)...
 *
 *  3、链地址法：思想同HashMap
 *
 *  4、公共溢出区法：将哈希表分为基本表和溢出表，只要发生哈希冲突就直接填入溢出区（每次查找是不是都要查公共溢出区？）
 *
 * =============================================================================================================
 *
 * 1、安全加密（摘要、签名）：MD5、PBKDF2WithHmacSHA1
 *      其他加密算法：SHA、DES、AES、RSA
 *
 * 2、唯一标识：HashMap中用来标识key，如果key的hash相同再判断equals
 *
 * 3、数据校验（签名）
 *
 * 4、掩码以及加盐salt（原始串加上固定的其他串）的散列函数
 *
 * 5、负载均衡
 *
 * 6、数据分片（MapReduce的思想）
 *
 * 7、分布式缓存的一致性哈希算法（哈希环，虚节点）
 *
 * 区块链：区块链是一块块区块组成的，每个区块分为两部分：区块头和区块体。
 *       区块头保存着 “自己区块体” 和 “上一个区块头” 的哈希值。
 *       因为这种链式关系和哈希值的唯一性，只要区块链上任意一个区块被修改过，后面所有区块保存的哈希值就不对了。
 *       区块链使用的是 SHA256 哈希算法，计算哈希值非常耗时，如果要篡改一个区块，就必须重新计算该区块后面所有的区块的哈希值，短时间内几乎不可能做到。
 *
 * @author wenhy
 * @date 2019/5/20
 */
public class Hash {

    public int getSum(int a, int b) {
        char[] ac = getIntBinaryString(a);
        char[] bc = getIntBinaryString(b);
        if (a == Integer.MIN_VALUE) {
            ac = "110000000000000000000000000000000".toCharArray();
        }
        if (b == Integer.MIN_VALUE) {
            bc = "110000000000000000000000000000000".toCharArray();
        }
        return doSum(ac, bc);
    }
    private char[] getIntBinaryString(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            if ((num & 1) == 1) {
                sb.append(1);
            } else {
                sb.append(0);
            }
            num = num >> 1;
        }
        return sb.reverse().toString().toCharArray();
    }

    private int doSum(char[] a, char[] b) {
        char[] carrys = new char[33];
        char[] result = new char[33];
        Arrays.fill(carrys, '0');
        Arrays.fill(result, '0');
        int i = a.length - 1;
        int j = b.length - 1;
        int idx = 32;
        while (i>=0 && j>=0) {
            if (a[i] == '1' && b[j] == '1' && carrys[idx] == '1') {
                result[idx] = '1';
                // 进位
                carrys[idx-1] = '1';
            } else if ((a[i] == '1' && b[j] == '1')
                    || (a[i] == '1' || b[j] == '1') && carrys[idx] == '1') {
                result[idx] = '0';
                carrys[idx-1] = '1';
            } else if (a[i] == '1' || b[j] == '1') {
                result[idx] = '1';
            } else if (carrys[idx] == '1'){
                result[idx] = '1';
            } else {
                result[idx] = '0';
            }
            i--; j--; idx--;
        }
        while (i >= 0) {
            result[idx--] = a[i--];
        }
        while (j >= 0) {
            result[idx--] = b[j--];
        }
        if (result[1] == '0') {
            return Integer.valueOf(new String(result, 2, 31), 2);
        } else {
            if ("011111111111111111111111111111111".equals(new String(result))) {
                return Integer.MIN_VALUE;
            }
            result[1] = '0';
            // 求补码（补码的补码就是原码）
            boolean converse = false;
            for (int k = result.length-1; k>1; k--) {
                if (!converse && result[k] == '0') {
                    continue;
                } else if (!converse && result[k] == '1') {
                    converse = true;
                    continue;
                } else {
                    if (result[k] == '0') {
                        result[k] = '1';
                    } else {
                        result[k] = '0';
                    }
                }
            }
            return - Integer.valueOf(new String(result, 2, 31), 2);
        }
    }

    @Test
    public void test() {
        System.out.println(getSum(Integer.MIN_VALUE, Integer.MAX_VALUE));
//        System.out.println(getSum(2, 3));
    }
}
