package top.hiccup.algorithm.dp;

import java.util.Scanner;

/**
 * 描述
 * Levenshtein 距离，又称编辑距离，指的是两个字符串之间，由一个转换成另一个所需的最少编辑操作次数。许可的编辑操作包括将一个字符替换成另一个字符，插入一个字符，删除一个字符。编辑距离的算法是首先由俄国科学家 Levenshtein 提出的，故又叫 Levenshtein Distance 。
 *
 * Ex：
 *
 * 字符串A: abcdefg
 *
 * 字符串B: abcdef
 *
 * 通过增加或是删掉字符 ”g” 的方式达到目的。这两种方案都需要一次操作。把这个操作所需要的次数定义为两个字符串的距离。
 *
 * 要求：
 *
 * 给定任意两个字符串，写出一个算法计算它们的编辑距离。
 *
 *
 * 数据范围：给定的字符串长度满足 1 \le len(str) \le 1000 \1≤len(str)≤1000
 *
 *
 * 输入描述：
 * 每组用例一共2行，为输入的两个字符串
 *
 * 输出描述：
 * 每组用例输出一行，代表字符串的距离
 *
 * 示例1
 * 输入：
 * abcdefg
 * abcdef
 * 复制
 * 输出：
 * 1
 */

public class 字符串编辑距离 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String a = in.nextLine();
        String b = in.nextLine();
        char[] arr = a.toCharArray();
        char[] brr = b.toCharArray();
        int m = arr.length;
        int n = brr.length;
        
        // 状态转移方程 dp[i][j] = max(dp[i-1][j-1]+1, dp[i][j])
        // 可以替换，插入，删除
        int[][] dp = new int[m+1][n+1];
        // 要初始化编辑距离，默认的编辑距离是0
        for(int i=1; i<=m; i++){
           dp[i][0] = i;
        }
        for(int i=1; i<=n; i++){
           dp[0][i] = i;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 如果相等，则不用操作，距离跟i-1的不变
                if (arr[i-1] == brr[j-1]) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    // 将arr修改为brr
                    // 替换
                    int d1 = dp[i-1][j-1] + 1;
                    // 插入brr
                    int d2 = dp[i-1][j] + 1;
                    // 删除arr
                    int d3 = dp[i][j-1] + 1;
                    dp[i][j] = Math.min(d1, Math.min(d2, d3));
                }
            }
        }
        System.out.println(dp[m][n]);
    }
}