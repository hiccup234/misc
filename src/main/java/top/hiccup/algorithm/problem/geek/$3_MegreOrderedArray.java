package top.hiccup.algorithm.problem.geek;

import java.lang.reflect.Array;

/**
 * 实现两个有序数组合并为一个有序数组
 *
 * @author wenhy
 * @date 2019/5/26
 */
public class $3_MegreOrderedArray {

    public static <T extends Comparable> T[] megre(T[] t1, T[] t2) {
        T[] ret = (T[]) Array.newInstance(t1.getClass().getComponentType(), t1.length + t2.length);
        int idx = 0;
        int i = 0, j = 0;
        while (i < t1.length && j < t2.length) {
            if (t1[i].compareTo(t2) <= 0) {
                ret[idx] = t1[i];
                idx++;
                i++;
            } else {
                ret[idx] = t2[j];
                idx++;
                j++;
            }
        }
        while (i < t1.length) {
            ret[idx] = t1[i];
            idx++;
            i++;
        }
        while (j < t2.length) {
            ret[idx] = t2[j];
            idx++;
            j++;
        }
        return ret;
    }
}
