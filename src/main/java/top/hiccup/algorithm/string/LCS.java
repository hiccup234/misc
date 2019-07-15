package top.hiccup.algorithm.string;

/**
 * 最长公共子序列：常用在git版本管理中
 *
 * 1、当其中一个串长度为0的时候子序列为0
 *
 * 2、当两个串尾字符相等时，最长子序列等于子串的最长子序列长度+1
 *
 * 3、当尾字符不等时，等于两个串的子串最大的最长子序列长度
 *
 * @author wenhy
 * @date 2019/7/15
 */
public class LCS {

    public static String LCS_caculate(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        int chess[][] = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    chess[i][j] = chess[i - 1][j - 1] + 1;
                } else {
                    chess[i][j] = Math.max(chess[i][j - 1], chess[i - 1][j]);
                }
            }
        }
        int i = len1;
        int j = len2;
        StringBuffer sb = new StringBuffer();
        while ((i != 0) && (j != 0)) {//利用上面得到的矩阵计算子序列，从最右下角往左上走
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                sb.append(s1.charAt(i - 1));//相同时即为相同的子串
                i--;
                j--;
            } else {
                if (chess[i][j - 1] > chess[i - 1][j]) {
                    j--;
                } else {
                    i--;
                }
            }
        }
        System.out.println((double) sb.length() / s2.length() + "," + (double) sb.length() / s1.length());
        // 记得反转
        return sb.reverse().toString();
    }
}
