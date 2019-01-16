package top.hiccup.jdk.lang;

/**
 * java.lang.System#identityHashCode不管怎样都会返回对象的内存地址的hash值
 *
 * @author wenhy
 * @date 2019/1/16
 */
public class HashCodeAndIdentityHashCodeTest {

    public static void main(String[] agrs) {
        String s1 = new String("abc");
        String s2 = new String("abc");

        System.out.println("s1 hashCode: "+ s1.hashCode());
        System.out.println("s2 hashCode: "+ s2.hashCode());
        // String重写了hashCode方法
        System.out.println("s1 identityHashCode: "+ System.identityHashCode(s1));
        System.out.println("s2 identityHashCode: "+ System.identityHashCode(s2));
    }
}
