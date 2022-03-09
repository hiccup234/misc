package top.hiccup.algorithm.dp;

import java.util.Scanner;

/**
 * 描述
 * 给定两个只包含小写字母的字符串，计算两个字符串的最大公共子串的长度。
 *
 * 注：子串的定义指一个字符串删掉其部分前缀和后缀（也可以不删）后形成的字符串。
 * 数据范围：字符串长度：1\le s\le 150\1≤s≤150
 * 进阶：时间复杂度：O(n^3)\O(n
 * 3
 *  ) ，空间复杂度：O(n)\O(n)
 * 输入描述：
 * 输入两个只包含小写字母的字符串
 *
 * 输出描述：
 * 输出一个整数，代表最大公共子串的长度
 *
 * 示例1
 * 输入：
 * asdfas
 * werasdfaswer
 * 复制
 * 输出：
 * 6
 */

public class 最长公共字串 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String a = in.nextLine();
        String b = in.nextLine();
        char[] arr = a.toCharArray();
        char[] brr = b.toCharArray();
        
        int max = 0;
        
//         // 暴力法
//         for (int i = 0; i < arr.length; i++) {
//             for (int j = 0; j < brr.length; j++) {
//                 int m = i, n = j;
//                 while(m < arr.length && n < brr.length && arr[m] == brr[n]) {
//                     m++;
//                     n++;
//                 }
//                 max = Math.max(max, n-j);
//             }
//         }
        
        
        // dp[i][j] = max(dp[i-1][j-1]+1, dp[i][j]);
        int m = arr.length, n = brr.length;
        int[][] dp = new int[m+1][n+1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (arr[i-1] == brr[j-1]) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                }
                max = Math.max(max, dp[i][j]);
            }
        }
        System.out.println(max);
    }
}