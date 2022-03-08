import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String s = in.nextLine();
        char[] arr = s.toCharArray();
        int n = arr.length;
        
        // 中心扩散法 aba abba
//         int max = 0;
//         for (int i=0; i<n; i++) {
//             // aba
//             int p1 = i, p2 = i;
//             while(p1 >= 0 && p2 < n && arr[p1] == arr[p2]) {
//                 p1--;
//                 p2++;
//             }
//             // while退出后p1=-1,p2=n
//             int m1 = p2 - p1 - 1;
            
//             // abba
//             p1 = i;
//             p2 = i+1;
//             while(p1 >= 0 && p2 < n && arr[p1] == arr[p2]) {
//                 p1--;
//                 p2++;
//             }
//             int m2 = p2 - p1 - 1;
//             max = Math.max(max, Math.max(m1, m2));
//         }
//         System.out.println(max);
            
        
        // 状态：对比的两个字符索引起始和终止索引位置
        // 定义: dp[i][j]字符串s的i到j字符组成的子串是否为回文子串
        boolean[][] dp = new boolean[n][n];
        int max = 0;
        for(int i = 0; i < n; i++) {
            // 默认每单个字符为回文串
            dp[i][i] = true;
        }
        // j是左边的下标，i是右边的下标
        for(int r = 0; r < n; r++) {
            for(int f = 0; f <= r; f++) {
                if (f == r) {
                    // aba
                    dp[f][r] = true;
                } else if(r - f == 1) {
                    // abba
                    dp[f][r] = (arr[f] == arr[r]);
                } else {
                    dp[f][r] = (arr[f] == arr[r] && dp[f+1][r-1]);
                }
                // 状态转移：如果左右两字符相等,同时[l+1...r-1]范围内的字符是回文子串,则 [l...r] 也是回文子串
                if(dp[f][r]) {
                    max = Math.max(max, r - f - 1 + 2);
                }
            }
        }
        System.out.println(max);
    }
}