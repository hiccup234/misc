package top.hiccup.jdk.lang.generic;

/**
 * Java中数组不支持泛型，所以提供不了编译器类型安全检查，Effective Java建议使用List来代替Array
 *
 * @author wenhy
 * @date 2019/2/22
 */
public class ArrayGenericTest {

    public static void main(String[] args) {
        int[] ints = new int[10];
//        ints[0] = 123.23;

        Object[] objects = new Integer[5];
        objects[1] = new String("fff");
    }
}
