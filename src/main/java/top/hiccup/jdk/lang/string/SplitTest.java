package top.hiccup.jdk.lang.string;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * String类split方法的测试
 *
 * @author wenhy
 * @date 2018/11/7
 */
public class SplitTest {

    public static String[] split(String str, String split) {
        // 第一步计算返回结果数组大小
        int size = 0;
        int index = 0;
        do{
            size++;
            index++;
            index = str.indexOf(split, index);
        }while(index != -1);
        // 返回的数组
        String[] retArr = new String[size];
        int startIndex = 0;
        int endIndex = 0;
        for(int i=0; i<size; i++){
            endIndex = str.indexOf(split, startIndex);
            if (endIndex == -1) {
                retArr[i] = str.substring(startIndex);
            } else {
                retArr[i] = str.substring(startIndex, endIndex);
            }
            startIndex = endIndex+1;
        }
        return retArr;
    }


    /**
     * 面试问题
     */
    public static String[] split3(String s, char split, boolean preToken) {
        if (null == s) {
            return null;
        }
        char[] chars = s.toCharArray();
        char[] temp = new char[chars.length];
        List<String> list = new LinkedList<>();
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
