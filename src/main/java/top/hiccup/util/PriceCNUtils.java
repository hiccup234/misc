package top.hiccup.util;

/**
 * 价格由数字转换为中文大写
 *
 * @author unascribed
 * @date 2018/8/24
 */
public class PriceCNUtils {
	
    private static final String[] CN_UPPER_NUMBER = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static final String[] RADICES = {"", "拾", "佰", "仟"};
    private static final String[] BIG_RADICES = {"", "万", "亿", "兆"};

    /**
     * 获取大写的人名币的金额，单位精确到分
     * @param money 价格金额，单位：分
     * @return 中文大写的金额
     */
    public static String getPriceCN(long money) {
        if (money == 0) {
            return "零元整";
        }
        StringBuilder result = new StringBuilder();
        // 整数部分
        long integral = money / 100;
        int integralLen = (integral + "").length();
        // 小数部分
        int decimal = (int) (money % 100);
        if (integral > 0) {
            int zeroCount = 0;
            for (int i = 0; i < integralLen; i++) {
                int unitLen = integralLen - i - 1;
                // 当前数字的值
                int d = Integer.parseInt((integral + "").substring(i, i + 1));
                // 大单位的下标{"", "万", "亿"}
                int quotient = unitLen / 4;
                // 获取单位的下标（整数部分都是以4个数字一个大单位，比如：个、十、百、千、个万、十万、百万、千万、个亿、十亿、百亿、千亿）
                int modulus = unitLen % 4;
                if (d == 0) {
                    zeroCount++;
                } else {
                    if (zeroCount > 0) {
                        result.append(CN_UPPER_NUMBER[0]);
                    }
                    zeroCount = 0;
                    result.append(CN_UPPER_NUMBER[d]).append(RADICES[modulus]);
                }
                if (modulus == 0 && zeroCount < 4) {
                    result.append(BIG_RADICES[quotient]);
                }
            }
            result.append("元");
        }
        if (decimal > 0) {
            int j = decimal / 10;
            if (j > 0) {
                result.append(CN_UPPER_NUMBER[j]).append("角");
            }
            j = decimal % 10;
            if (j > 0) {
                result.append(CN_UPPER_NUMBER[j]).append("分");
            }
        } else {
            result.append("整");
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(1001 / 100);
        System.out.println(10010 % 100);
        System.out.println(getPriceCN(1000));
        System.out.println(getPriceCN((long) (10012.202* 100)));
        System.out.println(getPriceCN(109202431));
        System.out.println(getPriceCN(1239999999999999L));
        System.out.println(getPriceCN(21435356));
    }
}
