package top.hiccup.util;

/**
 * 翻转字符串方法
 *
 * @author wenhy
 * @date 2018/1/17
 */
public class ReversalStrUtils {

    /**
     * 通过中间变量来翻转
     * @param chars
     */
    public static void reversal(char[] chars) {
        // 第一种遍历方式：字符串长度对半
        int len = chars.length;
        char c;
        for(int i=0; i<len/2; i++) {
            c = chars[len-1-i];
            chars[len-1-i] = chars[i];
            chars[i] = c;
        }

        // 第二种遍历方式：字符串数组首尾下标向中间收拢
        int i = 0;
        int j = chars.length - 1;
        char c2;
        while(i < j) {
            c2 = chars[j];
            chars[j] = chars[i];
            chars[i] = c2;
            i++;
            j--;
        }
    }

    /**
     * 不通过中间变量来翻转字符串
     * @param chars
     */
    public static void reversalWithNoTemp(char[] chars) {
        int i = 0;
        int j = chars.length - 1;
        while(i < j) {
            // char在java中是2个字节。java采用unicode，2个字节（16位）来表示一个字符。
            chars[i] = (char)(chars[i] + chars[j]);
            chars[j] = (char)(chars[i] - chars[j]);
            chars[i] = (char)(chars[i] - chars[j]);
            i++;
            j--;
        }
    }


    public static void main(String[] args) {
        char[] chars = new char[]{'a','b','c','d','e','f'};
        reversal(chars);
        System.out.println(chars);
        reversalWithNoTemp(chars);
        System.out.println(chars);
    }

}
