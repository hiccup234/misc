package top.hiccup.jdk.container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 5种初始化List的方式
 *
 * ## 论茴香豆的6种写法 ^_^
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
        System.out.println(list2);

        // 3、Collections的nCopies方法返回的也是不可操作List，将第二个参数负责n遍
        List list3 = Collections.nCopies(3, "test");
        System.out.println(list3);

        // 4、匿名内部类创建的方式
        List list4 = new ArrayList() {
            {
                super.add("sss");
                super.add("ttt");
                super.add("www");
            }
        };
        System.out.println(list4);

        // 5、JDK1.8 Stream.of
        List list5 = Stream.of("aaa", "bbb", "ccc").collect(Collectors.toList());
        System.out.println(list5);


        // 6、JDK1.9 List.of Set.of等（接口默认实现：default方法）
//        List list6 = List.of("aa", "bb", "cc");
//        System.out.println(list6);
    }
}
