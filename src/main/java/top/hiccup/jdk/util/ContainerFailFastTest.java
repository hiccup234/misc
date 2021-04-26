package top.hiccup.jdk.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 非线程安全容器的快速失败 fail-fast
 *
 * @author wenhy
 * @date 2018/8/25
 */
public class ContainerFailFastTest {

    public static void main(String[] args) {
        // 1、数组下标越界
        try {
            List list = new ArrayList();
            list.add(new Object());
            list.get(-1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2、并发修改
        List<Integer> intList = new ArrayList<>();
        intList.add(2);
        intList.add(4);
        intList.add(9);
        for (Integer i : intList) {
            System.out.println(i);
            if (i.intValue() == 2) {
                intList.remove(i);
            }
        }
    }
}
