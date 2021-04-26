package top.hiccup.jdk.util;

import java.util.ArrayList;
import java.util.List;

/**
 * ArrayList测试类
 *
 * @author wenhy
 * @date 2019/2/25
 */
public class ArrayListTest {

    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>();
        list1.add("a");
        list1.add("b");
        list1.add("c");
        list1.add("d");
        // 虽然public ArrayList(Collection<? extends E> c)是直接将elementData指向了目标数组
        // 但是ArrayList的toArray方法是拷贝数组，所以不存在相互影响
        List<String> list2 = new ArrayList<>(list1);
        System.out.print(list2);
        list1.set(0, "fff");
        System.out.print(list2);

        // Exception in thread "main" java.lang.OutOfMemoryError: Requested array size exceeds VM limit
//        int[] arr = new int[Integer.MAX_VALUE - 8];
//        list2 = new ArrayList<>(Integer.MAX_VALUE);

        list2.remove(-1);
    }
}
