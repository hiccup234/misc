package top.hiccup.jdk.lang.string;

/**
 * 重载的 + 与 concat 的区别：
 *
 * 1、+ 可以是字符串或者数字及其他基本类型数据，而concat只能接收字符串。
 * 2、+ 左右可以为null，concat为会空指针。
 * 3、如果拼接空字符串，性能concat会稍快一点，如果拼接更多字符串建议用StringBuilder。
 * 4、从字节码来看+号编译后就是使用了StringBuilder来拼接，所以一行+++的语句就会创建一个StringBuilder，多条+++语句就会创建多个，
 *    所以如果是多行+++建议用StringBuilder。
 *
 * @author wenhy
 * @date 2018/12/31
 */
public class PlusAndConcatTest {

    public static void main(String[] args) {
        // 重载操作符 +
        String s1 = "s1";
        System.out.println(s1 + 100);
        System.out.println(100 + s1);
        // concat
        String s2 = "s2";
        s2 = s2.concat("a").concat("bc");
        System.out.println(s2);

        // + 会把null转换为字符串“null”
        String s3 = "s3";
        System.out.println(s3 + null);
        System.out.println(null + s3);
        String s4 = "s4";
        // 这里会抛异常
        s4 = s4.concat(null);
        System.out.println(s4);

    }
}
