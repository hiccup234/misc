import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = in.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
        }
        // i的左边最长降序子序列，状态转移方程 dp[i] = max(dp[j] + 1, dp[i]) {j < i}
        int[] dp1 = new int[n];
        for (int i = 0; i < n; i++) {
            dp1[i] = 0;
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    dp1[i] = Math.max(dp1[j] + 1, dp1[i]);
                }
            }
        }
        int[] dp2 = new int[n];
        for (int i = n-1; i >= 0; i--) {
            dp2[i] = 0;
            for (int j = n-1; j > i; j--) {
                if (arr[i] > arr[j]) {
                    dp2[i] = Math.max(dp2[j] + 1, dp2[i]);
                }
            }
        }
        int max = 0;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, dp1[i] + dp2[i] + 1);
        }
        System.out.println(n - max);
        
    }
}