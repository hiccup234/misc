package top.hiccup.algorithm.problem;

/**
 * 0-1背包问题，两种解法：1、递归回溯法 2、动态规划(空间换时间，通过二维平面来思考)
 *
 * @author wenhy
 * @date 2019/7/16
 */
public class $01Package {

    /**
     * 存储背包中物品总重量的最大值
     */
    public int maxW = Integer.MIN_VALUE;

    /**
     * 用回溯法解决
     * // 假设背包可承受重量 100，物品个数 10，物品重量存储在数组 a 中，那可以这样调用函数：
     * // f(0, 0, a, 10, 100)
     *
     * @param i     表示考察到哪个物品了
     * @param cw    表示当前已经装进去的物品的重量和
     * @param items 表示每个物品的重量
     * @param n     表示物品个数
     * @param w     背包重量
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
     * 背包最大能装下的价值
     */
    private int maxV = Integer.MIN_VALUE;
    /**
     * 物品的重量
     */
    private int[] weight = {2, 2, 4, 6, 3};
    /**
     * 物品的价值
     */
    private int[] value = {3, 4, 8, 9, 6};
    /**
     * 背包承受的最大重量
     */
    private int w = 9;

    /**
     *  调用 f(0, 0, 0)
     * @param i 当前物品编号
     * @param cw 当前背包总重量
     * @param cv 当前背包总价值
     */
    public void f2(int i, int cw, int cv) {
        // cw==w 表示装满了，i==n 表示物品都考察完了
        if (cw == w || i == weight.length) {
            if (cv > maxV) {
                maxV = cv;
            }
            return;
        }
        f2(i + 1, cw, cv);
        if (cw + weight[i] <= w) {
            f2(i + 1, cw + weight[i], cv + value[i]);
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

    public static int knapsack2(int[] items, int n, int w) {
        // 默认值 false
        boolean[] states = new boolean[w + 1];
        // 第一行的数据要特殊处理，可以利用哨兵优化
        states[0] = true;
        if (items[0] <= w) {
            states[items[0]] = true;
        }
        // 动态规划
        for (int i = 1; i < n; ++i) {
            for (int j = w - items[i]; j >= 0; --j) {
                // 把第 i 个物品放入背包
                if (states[j] == true) {
                    states[j + items[i]] = true;
                }
            }
        }
        // 输出结果
        for (int i = w; i >= 0; --i) {
            if (states[i] == true) {
                return i;
            }
        }
        return 0;
    }


    /**
     * 动态规划解决0-1背包问题，时间复杂度O(n*w)，空间复杂度也是O(n*w)
     * @param weight
     * @param value
     * @param n
     * @param w
     * @return
     */
    public static int knapsack3(int[] weight, int[] value, int n, int w) {
        int[][] states = new int[n][w+1];
        for (int i = 0; i < n; ++i) { // 初始化 states
            for (int j = 0; j < w+1; ++j) {
                states[i][j] = -1;
            }
        }
        states[0][0] = 0;
        if (weight[0] <= w) {
            states[0][weight[0]] = value[0];
        }
        for (int i = 1; i < n; ++i) { // 动态规划，状态转移
            for (int j = 0; j <= w; ++j) { // 不选择第 i 个物品
                if (states[i-1][j] >= 0) states[i][j] = states[i-1][j];
            }
            for (int j = 0; j <= w-weight[i]; ++j) { // 选择第 i 个物品
                if (states[i-1][j] >= 0) {
                    int v = states[i-1][j] + value[i];
                    if (v > states[i][j+weight[i]]) {
                        states[i][j+weight[i]] = v;
                    }
                }
            }
        }
        // 找出最大值
        int maxvalue = -1;
        for (int j = 0; j <= w; ++j) {
            if (states[n-1][j] > maxvalue) maxvalue = states[n-1][j];
        }
        return maxvalue;
    }

}
