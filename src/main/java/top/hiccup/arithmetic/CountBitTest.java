package top.hiccup.arithmetic;

/**
 * 求一个正整数的二进制表示包含多少个1
 * 包含多少个0呢?（先求出1的个数，再相减）
 *
 * 1.位移法：
 *  每次判断最低位是否为1，然后在做向右位移，每个整数都要循环计算32次，比较耗时
 *
 * 2.求与法：
 *  通过n&(n-1)来消除最后一个1（任何一个整数减1都会导致从最后一个1开始退位），循环执行直到n变为0则说明所有的1都去掉了
 *  所有整数二进制表示平均有16个1，则平均要计算16次
 *  ==============================================================================================
 *  引申：Q：如何快速判断一个正整数n是不是2的x次幂？
 *       A：如果n是2的x次幂，（参考二进制转十进制）则二进制表示只有一个1（2的0次方等于1）， return n&(n-1) == 0
 *  ==============================================================================================
 *
 * 3.查表法：（离线计算，缓存结果）
 *  把所有的计算结果都缓存起来，这样每个整数直接查找对应的结果有多少个1
 *  时间复杂度为O(1)，但需要很大的内存空间（空间换时间）
 *
 * 4.多次查表法：
 *  把所有结算结果都按32位整数结果缓存会非常耗费空间
 *  可以采用分治法，分成高低各16位（或者更小，看时间复杂度和空间复杂度的折衷）
 *
 * @author wenhy
 * @date 2018/9/27
 */
public class CountBitTest {

    static int test1(int num) {
        int count =  0;
        for (int i=0; i<32; i++) {
            // 1除了最低位为1外，其他位都为0
            if ((num & 1) == 1) {
                count++;
            }
            // 左移高位补0
            num = num >> 1;
        }
        return count;
    }

    static int test2(int num) {
        int count = 0;
        while (num != 0) {
            count++;
            num = num&(num-1);
        }
        return count;
    }

    static int test3(int num) {
        // 一个int是4字节32位，需要缓存2^32个正整数的结果（无符号整型）
        // int中二进制表示最多32个1，需要5bit来表示有多少个1
        // 共需要2^32*5bit = 20Gb = 2.5GB（Java中没法直接按bit分配内存，则只有用byte）
        // @VM args: -Xms12G -Xmx12G -XX:+PrintGCDetails
        byte[] cache = new byte[Integer.MAX_VALUE-2];
        for (int i=0; i<Integer.MAX_VALUE-2; i++) {
            cache[i] = (byte)test2(i);
        }
        if (Integer.MAX_VALUE == num) {
            return 31;
        } else if (Integer.MAX_VALUE -1 == num) {
            return 30;
        }
        return cache[num];
    }

    static int test4(int num) {
        // 一个int32位，高低16位各自最多有16个1，则只需要4bit即可表示有多少个1
        // 只用缓存0-2^16整数的计算结果就可以了
        byte[] cache = new byte[0xFFFF];
        for (int i=0; i<0xFFFF; i++) {
            // 还可以考虑优化byte的高4位和低4位分别存储不同整数结算结果
            cache[i] = (byte)test2(i);
        }
        int low = num & 0xFFFF;
        // 右移高位是补0
        int high = num >> 16;
        return cache[low] + cache[high];
    }

    public static void main(String[] args) {
        int num = 234234234;
        System.out.println(Integer.toBinaryString(num));
        System.out.println(test1(num));
        System.out.println(test2(num));
//        System.out.println(test3(num));
        System.out.println(test4(num));
    }
}
