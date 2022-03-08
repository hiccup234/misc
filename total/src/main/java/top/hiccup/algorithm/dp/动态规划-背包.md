import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
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