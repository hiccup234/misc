import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
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