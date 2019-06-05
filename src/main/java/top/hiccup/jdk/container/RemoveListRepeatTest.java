package top.hiccup.jdk.container;

import java.util.*;

/**
 * 几种集合去重的方法
 *
 * @author wenhy
 * @date 2018/1/3
 */
public class RemoveListRepeatTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("aaa");
        list.add("ccc");

        // 1、通过HashSet去重（推荐）
        List newList = new ArrayList(new HashSet(list));

        // 2、通过TreeSet去重并排序
        List newList2 = new ArrayList(new TreeSet(list));

        // 3、通过Set去重并保持原顺序
        List newList3 = new ArrayList(10);
        Set set = new HashSet();
        for (String s : list) {
            if (set.add(s)) {
                newList3.add(s);
            }
        }

        // 4、通过新List手动去重
        List newList4 = new ArrayList(10);
        for (String s : list) {
            if (!newList4.contains(s)) {
                newList4.add(s);
            }
        }
    }
}
