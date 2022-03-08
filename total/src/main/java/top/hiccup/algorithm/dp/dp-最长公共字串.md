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