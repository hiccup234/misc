package top.hiccup.util;

import java.util.List;

/**
 * List相关工具集
 *
 * @author wenhy
 * @date 2019/1/17
 */
public class ListUtils {

    /**
     * 判断给定的List集合是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    /**
     * 判断两个List的元素是否一样，不判断顺序
     *
     * @param list1
     * @param list2
     * @param <T>
     * @return
     */
    public static <T> boolean isEquals(List<T> list1, List<T> list2) {
        // 如果都为空返回true
        if (list1 == null && list2 == null) {
            return true;
        }
        if (list1 == null || list2 == null) {
            return false;
        }
        if (list1.size() != list2.size()) {
            return false;
        }
        for (T t : list1) {
            if (!list2.contains(t)) {
                return false;
            }
        }
        return true;
    }
}
