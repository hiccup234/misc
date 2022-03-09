package top.hiccup.algorithm.dp;

import java.util.Scanner;

/**
 * 描述
 * 王强今天很开心，公司发给N元的年终奖。王强决定把年终奖用于购物，他把想买的物品分为两类：主件与附件，附件是从属于某个主件的，下表就是一些主件与附件的例子：
 *
 * 主件	附件
 * 电脑	打印机，扫描仪
 * 书柜	图书
 * 书桌	台灯，文具
 * 工作椅	无
 *
 * 如果要买归类为附件的物品，必须先买该附件所属的主件，且每件物品只能购买一次。每个主件可以有 0 个、 1 个或 2 个附件。附件不再有从属于自己的附件。王强想买的东西很多，为了不超出预算，他把每件物品规定了一个重要度，分为 5 等：用整数 1 ~ 5 表示，第 5 等最重要。他还从因特网上查到了每件物品的价格（都是 10 元的整数倍）。他希望在不超过 N 元（可以等于 N 元）的前提下，使每件物品的价格与重要度的乘积的总和最大。
 * 设第 j 件物品的价格为 v[j] ，重要度为 w[j] ，共选中了 k 件物品，编号依次为 j 1 ， j 2 ，……， j k ，则所求的总和为：
 * v[j 1 ]*w[j 1 ]+v[j 2 ]*w[j 2 ]+ … +v[j k ]*w[j k ] 。（其中 * 为乘号）
 * 请你帮助王强设计一个满足要求的购物单。
 *
 *
 * 输入描述：
 * 输入的第 1 行，为两个正整数，用一个空格隔开：N m
 *
 * （其中 N （ N<32000 ）表示总钱数， m （m <60 ）为希望购买物品的个数。）
 *
 *
 * 从第 2 行到第 m+1 行，第 j 行给出了编号为 j-1 的物品的基本数据，每行有 3 个非负整数 v p q
 *
 *
 * （其中 v 表示该物品的价格（ v<10000 ）， p 表示该物品的重要度（ 1 ~ 5 ）， q 表示该物品是主件还是附件。如果 q=0 ，表示该物品为主件，如果 q>0 ，表示该物品为附件， q 是所属主件的编号）
 *
 *
 *
 * 输出描述：
 *  输出文件只有一个正整数，为不超过总钱数的物品的价格与重要度乘积的总和的最大值（ <200000 ）。
 * 示例1
 * 输入：
 * 1000 5
 * 800 2 0
 * 400 5 1
 * 300 5 1
 * 400 3 0
 * 500 2 0
 * 复制
 * 输出：
 * 2200
 * 复制
 * 示例2
 * 输入：
 * 50 5
 * 20 3 5
 * 20 3 5
 * 10 3 0
 * 10 2 0
 * 10 1 0
 * 复制
 * 输出：
 * 130
 * 复制
 * 说明：
 * 由第1行可知总钱数N为50以及希望购买的物品个数m为5；
 * 第2和第3行的q为5，说明它们都是编号为5的物品的附件；
 * 第4~6行的q都为0，说明它们都是主件，它们的编号依次为3~5；
 * 所以物品的价格与重要度乘积的总和的最大值为10*1+20*3+20*3=130
 */

// TODO 0-1背包加强版

public class 购物车 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        // 背包总容量
        int n = in.nextInt();
        // 物品数量
        int m = in.nextInt();
        // 物品重量数组
        int[][] wgts = new int [m+1][3];
        // 物品价值数组
        int[][] vals =  new int [m+1][3];
        for (int i = 1; i <= m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            if (c == 0) {
                wgts[i][0] = a;
                vals[i][0] = a * b;
            } else if (wgts[c][1] == 0) {
                wgts[c][1] = a;
                vals[c][1] = a * b;
            } else {
                wgts[c][2] = a;
                vals[c][2] = a * b;
            }
        }
        
        // dp[i][j] = max(主件i不放入，主件i，主件i+附件1，主件i+附件2，主件i+附件1+附件2)
        // dp数组 
        int dp[][] = new int[m+1][n+1];
        for(int i = 1; i <= m; i++) {
//             int w0, w1, w2, w3;
//             int v0, v1, v2, v3;
//             // 只有主件重量
//             w0 = wgts[i][0];
//             w1 = wgts[i][0] + wgts[i][1];
//             w2 = wgts[i][0] + wgts[i][2];
//             w3 = wgts[i][0] + wgts[i][1] + wgts[i][2];
            
//             // 只有主件价值
//             v0 = vals[i][0];
//             v1 = vals[i][0] + vals[i][1];
//             v2 = vals[i][0] + vals[i][2];
//             v3 = vals[i][0] + vals[i][1] + vals[i][2];
            
//             for (int j = 1; j <= n; j++) {
//                 // 主件i不放入
//                 dp[i][j] = dp[i-1][j];
//                 // 主件i放入
//                 if (j - w0 >= 0) {
//                     dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-w0] + v0);
//                     // 主件i放入，再判断各个附件是否可以放入
//                     if (j - w1 >= 0 && vals[i][1] != 0) {
//                         dp[i][j] = Math.max(dp[i][j], dp[i][j-w1] + v1);
//                     }
//                     if (j - w2 >= 0 && vals[i][2] != 0) {
//                         dp[i][j] = Math.max(dp[i][j], dp[i][j-w2] + v2);
//                     }
//                     if (j - w3 >= 0 && vals[i][1] != 0 && vals[i][2] != 0) {
//                         dp[i][j] = Math.max(dp[i][j], dp[i][j-w3] + v3);
//                     }
//                 }
//             }
             // 主件
             int w1 = wgts[i][0];
             int v1 = vals[i][0];
             // 附件1
             int w2 = wgts[i][1];
             int v2 = vals[i][1];
             // 附件2
             int w3 = wgts[i][2];
             int v3 = vals[i][2];
             for(int j = 1; j <= n; j++) {
                 // 是否只放主件
                 dp[i][j] = j - w1 >= 0 ? Math.max(dp[i-1][j], dp[i-1][j-w1] + v1) : dp[i-1][j];
                 // 放主件的同时，放附件1
                 dp[i][j] = j-w1-w2 >= 0 ? Math.max(dp[i][j], dp[i-1][j-w1-w2] + v1 + v2) : dp[i][j];
                 // 放主件的同时，放附件2
                 dp[i][j] = j-w1-w3 >= 0 ? Math.max(dp[i][j], dp[i-1][j-w1-w3] + v1 + v3) : dp[i][j];
                 // 放主件的同时，放附件1和2
                 dp[i][j] = j-w1-w2-w3 >= 0 ? Math.max(dp[i][j], dp[i-1][j-w1-w2-w3] + v1 + v2 + v3) : dp[i][j];
             }
            
        }
        System.out.println(dp[m][n]);
    }
}