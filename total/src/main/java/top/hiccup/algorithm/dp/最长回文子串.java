import java.util.Scanner;

/**
 * 描述
 * Catcher是MCA国的情报员，他工作时发现敌国会用一些对称的密码进行通信，比如像这些ABBA，ABA，A，123321，但是他们有时会在开始或结束时加入一些无关的字符以防止别国破解。比如进行下列变化 ABBA->12ABBA,ABA->ABAKK,123321->51233214　。因为截获的串太长了，而且存在多种可能的情况（abaaab可看作是aba,或baaab的加密形式），Cathcer的工作量实在是太大了，他只能向电脑高手求助，你能帮Catcher找出最长的有效密码串吗？
 *
 * 数据范围：字符串长度满足 1 \le n \le 2500 \1≤n≤2500
 * 输入描述：
 * 输入一个字符串（字符串的长度不超过2500）
 *
 * 输出描述：
 * 返回有效密码串的最大长度
 *
 * 示例1
 * 输入：
 * ABBA
 * 复制
 * 输出：
 * 4
 * 复制
 * 示例2
 * 输入：
 * ABBBA
 * 复制
 * 输出：
 * 5
 * 复制
 * 示例3
 * 输入：
 * 12HHHHA
 * 复制
 * 输出：
 * 4
 */

public class 最长回文子串 {
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