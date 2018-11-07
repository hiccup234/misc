package top.hiccup.jdk.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

/**
 * String类相关方法的测试
 *
 * @author wenhy
 * @date 2018/11/7
 */
public class StringTest {



    public static String[] split(String strInfo, String strSplit) {
        //第1步计算数组大小
        int size    = 0;
        int index = 0;
        do{
            size++;
            index++;
            index = strInfo.indexOf(strSplit ,index);
        }while(index!=-1);
        String[] arrRtn    = new String[size]; //返回数组
        //-------------------------------------------------------
        //第2步给数组赋值(25行程序)
        int startIndex = 0;
        int endIndex     = 0;
        for(int i = 0; i < size; i++){
            endIndex = strInfo.indexOf(strSplit, startIndex);
            if(endIndex == -1) {
                arrRtn[i] = strInfo.substring(startIndex);
            } else {
                arrRtn[i] = strInfo.substring(startIndex, endIndex);
            }
            startIndex = endIndex+1;
        }
        return arrRtn;
    }

    /**
     * 分割字符串
     *
     * @param util
     *            要分割的字符串
     * @param split
     *            分割点
     * @return
     */
    public static String[] split2(String util, String split) {
        String splits[] = null;
        Vector vector = new Vector();
        int startIndex = 0;// 字符串的起始位置
        int index = 0;// 存取字符串时起始位置
        startIndex = util.indexOf(split);// 获得匹配字符串的位置
        // 如果起始字符串的位置小于字符串的长度，则证明没有取到字符串末尾 -1代表末尾
        while (startIndex < util.length() & startIndex != -1) {
            String temp = util.substring(index, startIndex);
            vector.addElement(temp);
            // 设置取字串的起始位置
            index = startIndex + split.length();
            // 获得匹配字串的位置
            startIndex = util.indexOf(split, startIndex + split.length());

        }
        // 取结束的子串
        vector.addElement(util.substring(index + 1 - split.length()));
        // 将VECTOR对象转换成数组
        splits = new String[vector.size()];
        for (int i = 0; i < splits.length; i++) {
            splits[i] = (String) vector.elementAt(i);
        }
        return splits;
    }



    public static String[] split3(String s, char split, boolean preToken) {
        if (null == s) {
            return null;
        }
        char[] chars = s.toCharArray();
        char[] temp = new char[chars.length];
        ArrayList<String> list = new ArrayList<>();
        int index = 0;
        for (char c : chars) {
            if (c == split) {
                if (!preToken) {
                    if (0 == index) {
                        list.add(new String(Arrays.copyOf(temp, index)));
                    } else {
                        list.add(new String(Arrays.copyOf(temp, index)));
                        list.add("");
                        index = 0;
                    }
                } else {
                    if (0 != index) {
                        list.add(new String(Arrays.copyOf(temp, index)));
                        index = 0;
                    }
                }
            } else {
                temp[index++] = c;
            }
        }
        String[] result = new String[list.size()];
        return list.toArray(result);
    }

    public static void main(String[] args) {
        String s = "#abc#de##f#";
        System.out.println(Arrays.toString(split3(s, '#', false)));
        System.out.println(Arrays.toString(s.split("#")));

        long start = System.currentTimeMillis();
        for (int i=0; i<50_000_000; i++) {
            split3(s, '#', false);
        }
        System.out.println("fff"+(System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (int i=0; i<50_000_000; i++) {
            s.split("#");
        }
        System.out.println(System.currentTimeMillis() - start);

        System.out.println(Arrays.toString(split3(s, '#', true)));
    }
}
