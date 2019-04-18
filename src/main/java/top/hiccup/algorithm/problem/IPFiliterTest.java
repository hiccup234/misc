package top.hiccup.algorithm.problem;

import java.util.BitSet;

import org.junit.Test;
import org.springframework.util.StringUtils;

/**
 * 实现一个IP白名单过滤算法，要求：
 * 1、占用空间尽量少
 * 2、运算效率尽量高
 * 3、在内存中完成查询及判断
 * 4、接口可能被并发询问
 * 5、尽量能存储整个IP地址空间
 * 6、代码可运行，且包含单测
 *
 * 思路：
 * 针对IPV4，转换为int类型，两个BitSet即可存储全部IPV4的地址，占用512M多的内存，支持随机访问
 *
 * 缺点：
 * 1、volatile修饰，性能会降低
 * 2、目前没考虑IPV6
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
        String ip2= "127.0.0.1";
        String ip3= "255.255.255.255";
        String ip4= "128.0.0.0";

        addWhiteIpAddress(ip1);
        System.out.println(isWhiteIpAddress(ip1));

        addWhiteIpAddress(ip2);
        System.out.println(isWhiteIpAddress(ip2));

        addWhiteIpAddress(ip3);
        System.out.println(isWhiteIpAddress(ip3));

        addWhiteIpAddress(ip4);
        System.out.println(isWhiteIpAddress(ip4));

        System.out.println(isWhiteIpAddress("0.0.0.1"));

    }
}
