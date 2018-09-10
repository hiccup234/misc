package top.hiccup.jdk.lang.reflect;

import java.lang.reflect.Array;

/**
 * 反射数组测试类（主要是通过本地方法实现）
 *
 * @author wenhy
 * @date 2018/8/30
 */
public class ArrayTest {

    public static void main(String[] args) throws Exception {
        Class<?> element = Class.forName("top.hiccup.jdk.lang.reflect.ArrayElement");
        // 通过反射创建数组
        Object arr = Array.newInstance(element, 5);

        // 设置数组元素
        Array.set(arr, 0, element.newInstance());
        Array.set(arr, 1, element.newInstance());

        // 获取数组元素
        System.out.println(Array.get(arr, 0));
        System.out.println(arr);

    }
}

class ArrayElement { }