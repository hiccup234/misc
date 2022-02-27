package top.hiccup.jdk.lang.reference;

import java.lang.reflect.Field;

/**
 * Java所有的方法调用都是值传递，包括对象的引用地址值
 *
 * @author wenhy
 * @date 2022/2/27
 */
public class SwapTest {

    public static void swap(Integer a, Integer b) {
        // 在代码块中对引用重新赋值是没效果的
//        Integer tmp = b;
//        a = b;
//        b = tmp;

        // 使用反射
        try {
            Field field = Integer.class.getDeclaredField("value");
            field.setAccessible(true);
            // 这里运行结果是：a = 2 , b = 2，因为Integer有IntegerCache数组缓存对象
//            int tmp = a.intValue();
//            field.set(a, b.intValue());
//            System.out.println(tmp);
//            field.set(b, tmp);

            // 使用构造器new一个对象，而不是自动装箱的Integer.valueOf()，后者有缓存
            Integer tmp2 = new Integer(a.intValue());
            field.set(a, b.intValue());
            System.out.println(tmp2);
            field.set(b, tmp2);
            // 这个操作很危险，因为修改了JVM对Integer的缓存对象的值
            System.out.println(" Integer.valueOf(1) = " + Integer.valueOf(1));
            System.out.println(" Integer.valueOf(2) = " + Integer.valueOf(2));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Integer a = 1, b = 2;
        System.out.println("a = " + a + " , b = " + b);
        swap(a, b);
        System.out.println("a = " + a + " , b = " + b);
    }
}
