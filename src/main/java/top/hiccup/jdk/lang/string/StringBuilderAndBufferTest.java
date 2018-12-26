package top.hiccup.jdk.lang.string;

/**
 * StringBuilder与StringBuffer：
 *
 * 1、StringBuffer是StringBuilder的线程安全版本（修改数据的方法都被加上了synchronized关键字）。
 *
 * 2、JDK1.8已经对String的“+”做了优化，javac会自动优化为StringBuilder的操作。
 *
 * 3、JDK1.9对String做出了巨大改动，底层存储的数据结构有char[]改为了byte[]
 *
 * @author wenhy
 * @date 2018/12/25
 */
public class StringBuilderAndBufferTest {

    /**
     * javac -encoding utf-8 StringConcat.java
     * 再javap -v StringConcat.class查看反编译后的结果
     */
    public static void main(String[] args) {
        String strByBuilder = new StringBuilder().append("aa").append("bb").append("cc").append("dd").toString();
        // 编译器会直接优化成 “aabbccdd”
        String strByConcat = "aa" + "bb" + "cc" + "dd";
        System.out.println(strByBuilder == strByConcat);
        // JDK1.8编译器会优化成 “aabbcc”，然后 new StringBuilder("aabbcc").append(new String("dd"));
        String strByConcat2 = "aa" + "bb" + "cc" + new String("dd");
        System.out.println("strByConcat2:" + strByConcat2);
    }
}
