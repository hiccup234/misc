package top.hiccup.arithmetic.problem;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

/**
 * 算法题：
 * 输入一个字符串，返回第一个数量为1的字符，没有返回空字符
 * 如abbce为a，aabbcc为空
 *
 * @author wenhy
 * @date 2019/2/22
 */
public class FirstCountCharTest {

    public Character getFirstCountChar(String s) {
        if (s == null) {
            return null;
        }
        char[] chars = s.toCharArray();
        // LinkedHashMap维护了一个双向链表，遍历时可以保持顺序
        Map<Character, Integer> countMap = new LinkedHashMap<>();
        for (int i = 0; i < chars.length; i++) {
            Integer count = countMap.get(chars[i]);
            if (count != null) {
                countMap.put(chars[i], ++count);
            } else {
                countMap.put(chars[i], 1);
            }
        }
        for (Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            if (entry.getValue().intValue() == 1) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Test
    public void test() {
        System.out.println(getFirstCountChar("abbce"));
        System.out.println(getFirstCountChar("aabbcc"));
    }
}
