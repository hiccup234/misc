package top.hiccup.algorithm.problem;

import org.junit.Test;

/**
 * 打印数列的排列
 *
 * @author wenhy
 * @date 2019/5/26
 */
public class Permutations {

    /**
     * k 表示要处理的子数组的数据个数
     */
    public void printPermutations(int[] data, int n, int k) {
        if (k == 1) {
            for (int i = 0; i < n; ++i) {
                System.out.print(data[i] + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < k; ++i) {
            // 交换当前节点和倒数第二个节点
            int tmp = data[i];
            data[i] = data[k - 1];
            data[k - 1] = tmp;

            printPermutations(data, n, k - 1);

            // 再交换回来
            tmp = data[i];
            data[i] = data[k - 1];
            data[k - 1] = tmp;
        }
    }

    @Test
    public void test() {
        int[] a = {1, 2, 3, 4};
        printPermutations(a, 4, 4);
    }
}
