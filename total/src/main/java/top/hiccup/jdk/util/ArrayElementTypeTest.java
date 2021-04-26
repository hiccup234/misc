package top.hiccup.jdk.util;

/**
 * 数组元素的类型要求匹配
 *
 * @author wenhy
 * @date 2018/9/29
 */
public class ArrayElementTypeTest {

    public static void main(String[] args) {
        String[] arr = {"aaa"};
        System.out.println(arr.length);
        Object[] arr2 = arr.clone();
        System.out.println(arr);
        // 注意arr2的类型任然为[Ljava.lang.String;
        System.out.println(arr2);

        String[] arrs = {"a", "c"};
        Object[] arro = arrs;
        // java.lang.ArrayStoreException
        // 这时arro指向String[]，所以不能直接赋值一个Object对象，见ArrayList(Collection<? extends E> c)的处理
        arro[0] = new Object();
    }
}
