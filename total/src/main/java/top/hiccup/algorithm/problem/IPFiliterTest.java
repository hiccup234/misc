package top.hiccup.algorithm.problem;

import java.util.BitSet;

import org.junit.Test;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 请实现一个IP白名单过滤算法，实现以下接口
 *     boolean addWhiteIpAddress(String ip);
 *     boolean isWhiteIpAddress(String ip);
 * 要求如下：
 *     占用空间尽量少
 *     运算效率尽量高
 *     在内存中完成查询及判断
 *     接口可能被并发询问
 *     尽量能存储整个IP地址空间
 *     代码可运行，且包含单测
 *
 * 思路：
 *      如题，需要实现的是一个白名单的功能而不是黑名单，且要求尽可能存储整个IP地址空间，所以如果直接存储ip地址的字符串32位JVM下需要约：
 *  2^32 * (32+32+ (7+15)/2) = 300GB（这里只是非常粗略的估算），当然如果把字符串换成char数组可以省下对象头和类型指针，预计需约44GB，
 *  这完全超出了32位JVM的堆空间，而且就算借助于文件系统和硬盘，这么大数据量的字符串比较效率也是非常低下的。
 *      所以换个思路，针对IPV4，一个IP地址为32个bit位，可以直接将IP地址转换为int类型，又因为白名单只有两种状态，
 *  要么在白名单中要不不在，所以很自然就想到位图了，用两个BitSet即可存储全部IPV4的地址，占用512M多的内存，也支持随机访问。
 *
 * 缺点：
 * 1、volatile修饰，性能会降低（修饰对象或数组，如果对象属性或数组元素被修改，可以间接保证对象属性或数组元素的线程可见性）
 * 2、目前没考虑IPV6
 * 3、测试的数据量不够，可能有些边界还没考虑到，还没做性能测试
 *
 * @author wenhy
 * @date 2019/4/18
 */
public class IPFiliterTest {

    private static volatile BitSet low = new BitSet(Integer.MAX_VALUE);
    private static volatile BitSet high = new BitSet(Integer.MAX_VALUE);

    /**
     * 固定8位，前面不足的补零
     * @param num
     * @return
     */
    private static String getByteBinaryStr(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            if ((num & 1) == 1) {
                sb.append(1);
            } else {
                sb.append(0);
            }
            num = num >> 1;
        }
        return sb.reverse().toString();
    }

    /**
     * 将IP地址字符串转换为int
     * @param ip
     * @return
     */
    private static int chgIpStrToInt(String ip) {
        if (ip == null || StringUtils.isEmpty(ip)) {
            throw new RuntimeException("Null string");
        }
        String[] arr = ip.split("\\.");
        if (arr == null || arr.length != 4) {
            throw new RuntimeException("Invalid ip");
        }
        if ("128.0.0.0".equals(ip)) {
            return Integer.MIN_VALUE;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getByteBinaryStr(new Integer(arr[0])));
        sb.append(getByteBinaryStr(new Integer(arr[1])));
        sb.append(getByteBinaryStr(new Integer(arr[2])));
        sb.append(getByteBinaryStr(new Integer(arr[3])));
        String intStr = sb.toString();
        // Integer.valueOf处理负数会有问题
        if (intStr.charAt(0) == '1') {
            char[] chars = intStr.toCharArray();
            chars[0] = '0';
            intStr = new String(chars);
            return 0 - Integer.valueOf(intStr, 2).intValue();
        }
        return Integer.valueOf(intStr, 2).intValue();
    }

    public static boolean addWhiteIpAddress(String ip){
        int ipInt = chgIpStrToInt(ip);
        if (ipInt < 0) {
            high.set(ipInt+Integer.MAX_VALUE+1);
        } else {
            low.set(ipInt);
        }
        return true;
    }

    public static boolean isWhiteIpAddress(String ip) {
        int ipInt = chgIpStrToInt(ip);
        if (ipInt < 0) {
            return high.get(ipInt+Integer.MAX_VALUE+1);
        } else {
            return low.get(ipInt);
        }
    }

    @Test
    public void test() throws InterruptedException {
        String ip1= "0.0.0.0";
        addWhiteIpAddress(ip1);
        Assert.isTrue(isWhiteIpAddress(ip1));

        String ip2= "127.0.0.1";
        addWhiteIpAddress(ip2);
        Assert.isTrue(isWhiteIpAddress(ip2));

        String ip3= "255.255.255.255";
        addWhiteIpAddress(ip3);
        Assert.isTrue(isWhiteIpAddress(ip3));

        String ip4= "128.0.0.0";
        addWhiteIpAddress(ip4);
        Assert.isTrue(isWhiteIpAddress(ip4));

        Assert.isTrue(!isWhiteIpAddress("0.0.0.1"));
        Assert.isTrue(!isWhiteIpAddress("1.1.1.1"));
        Assert.isTrue(!isWhiteIpAddress("001.001.001.001"));
    }
}
