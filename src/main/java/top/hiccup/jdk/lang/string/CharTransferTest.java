package top.hiccup.jdk.lang.string;

/**
 * 转换字符串大小写，数字不变，其他变为*
 *
 * 大小写转换，ASCLL 相差32
 *
 * @author wenhy
 * @date 2018/11/26
 */
public class CharTransferTest {

    public static void transfer() {
        String str = "ABC123abcfadfjbJ##BHJHJDsa##";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= str.length() - 1; i++) {
            char ch = str.charAt(i);
            // 通过str.charAt(i)遍历出字符串中每个字符
            if (ch >= 'a' && ch <= 'z') {
                // 判断字符是否在a-z之间（小写）
                ch = (char) (ch - 32);
            } else if (ch >= 'A' && ch <= 'Z') {
                // 判断字符是否在A-Z之间（大写）
                ch = (char) (ch + 32);
            } else if (ch >= '0' && ch <= '9') {
                //判断字符是否在0-9之间（数字）
            } else {
                ch = '*';
            }
            sb.append(ch);
        }
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        transfer();
    }
}
