package top.hiccup.algorithm.dp;

import java.util.Scanner;

/**
 * 描述
 * 把m个同样的苹果放在n个同样的盘子里，允许有的盘子空着不放，问共有多少种不同的分法？（用K表示）5，1，1和1，5，1 是同一种分法。
 *
 * 数据范围：0 \le m \le 10 \0≤m≤10 ，1 \le n \le 10 \1≤n≤10 。
 *
 * 输入描述：
 * 输入两个int整数
 *
 * 输出描述：
 * 输出结果，int型
 *
 * 示例1
 * 输入：
 * 7 3
 * 复制
 * 输出：
 * 8
 *
 * @author wenhy
 * @date 2022/3/9
 */
public class 放苹果 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int m = in.nextInt();
        int n = in.nextInt();

        // 状态转移方程：dp[i][j] = dp[i][j-1] + dp[i-j][j];
        // 持有i个苹果，有j个盘子可以存放苹果，总共有dp[i][j]种放法
        int[][] dp = new int[m+1][n+1];

        // 注意 0≤m≤10
        // base case：没有苹果，只有一种摆放方法
        for(int j = 0; j <= n; j++) {
            dp[0][j] = 1;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (i < j) {
                    // 苹果数 < 盘子数，有空盘，
                    // 则忽略一个盘子，在n-1个放苹果，一直递推到n==1，有一种摆法
                    dp[i][j] = dp[i][j-1];
                } else {
                    // 苹果数 >= 盘子数，可以看作没有空盘
                    // 则可以选择忽略一个盘子，如上边做法
                    // 还可以选择每个盘子放一个苹果，即苹果数剩下i-j,继续递推直到j==1
                    dp[i][j] = dp[i][j-1] + dp[i-j][j];
                }
            }
        }
        System.out.println(dp[m][n]);
    }
}