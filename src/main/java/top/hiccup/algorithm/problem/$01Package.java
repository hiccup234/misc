package top.hiccup.algorithm.problem;

/**
 * 0 - 1 背包问题
 *
 *
 * @author wenhy
 * @date 2019/7/16
 */
public class $01Package {
    // 存储背包中物品总重量的最大值
    public int maxW = Integer.MIN_VALUE;

    /**
     * 用回溯法解决
     *  // 假设背包可承受重量 100，物品个数 10，物品重量存储在数组 a 中，那可以这样调用函数：
     *  // f(0, 0, a, 10, 100)
     *
     * @param i 表示考察到哪个物品了
     * @param cw 表示当前已经装进去的物品的重量和
     * @param items 表示每个物品的重量
     * @param n 表示物品个数
     * @param w 背包重量
     */
    public void f(int i, int cw, int[] items, int n, int w) {
        // cw==w 表示装满了，i==n 表示已经考察完所有的物品
        if (cw == w || i == n) {
            if (cw > maxW) {
                maxW = cw;
            }
            return;
        }
        // 当前物品不装进背包
        f(i + 1, cw, items, n, w);
        // 没有超过背包可以承受的重量才能装进去
        if (cw + items[i] <= w) {
            // 当前物品装进背包
            f(i + 1, cw + items[i], items, n, w);
        }
    }


    /**
     * @param weight 物品重量
     * @param n      物品个数
     * @param w      背包可承载重量
     * @return
     */
    public int knapsack(int[] weight, int n, int w) {
        // 默认值 false
        boolean[][] states = new boolean[n][w + 1];
        // 第一行的数据要特殊处理，可以利用哨兵优化
        states[0][0] = true;
        if (weight[0] <= w) {
            states[0][weight[0]] = true;
        }
        // 动态规划状态转移
        for (int i = 1; i < n; ++i) {
            // 不把第 i 个物品放入背包
            for (int j = 0; j <= w; ++j) {
                if (states[i - 1][j] == true) {
                    states[i][j] = states[i - 1][j];
                }
            }
            // 把第 i 个物品放入背包
            for (int j = 0; j <= w - weight[i]; ++j) {
                if (states[i - 1][j] == true) {
                    states[i][j + weight[i]] = true;
                }
            }
        }
        // 输出结果
        for (int i = w; i >= 0; --i) {
            if (states[n - 1][i] == true) {
                return i;
            }
        }
        return 0;
    }

}
