package top.hiccup.jdk.container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 5种初始化List的方式
 *
 * @author wenhy
 * @date 2018/12/6
 */
public class ListInitTest {

    public static void main(String[] args) {
        // 1、最常见的new方式
        List list1 = new LinkedList();

        // 2、Arrays的asList方法，创建的List不能添加和删除等操作（Arrays工具类中的内部类ArrayList）
        List list2 = Arrays.asList("123", "234");
        // 包装成可变的list2
        list2 = new ArrayList(list2);

        // 3、
    }
}
