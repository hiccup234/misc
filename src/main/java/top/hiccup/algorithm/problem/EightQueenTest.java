package top.hiccup.algorithm.problem;

import java.util.Arrays;

/**
 * 八皇后问题是一个以国际象棋为背景的问题：
 * 如何能够在 8×8 的国际象棋棋盘上放置八个皇后，使得任何一个皇后都无法直接吃掉其他的皇后？为了达到此目的，任两个皇后都不能处于同一条横行、纵行或斜线上。
 * 八皇后问题可以推广为更一般的n皇后摆放问题：这时棋盘的大小变为n1×n1，而皇后个数也变成n2。而且仅当 n2 ≥ 1 或 n1 ≥ 4 时问题有解。
 * 
 * 【回溯法】
 * 92个解中，只有12个独立解（跟其他解不是对称的）
 *
 * @author wenhy
 * @date 2019/4/15
 */
public class EightQueenTest {

    /**
     * 下标表示行，值表示queen存储在哪一列
     */
    private int[] queens;
    private int count;

    public EightQueenTest() {
        queens = new int[8];
        Arrays.fill(queens, 0);
    }

    public void cal8queens(int row) { // 调用方式：cal8queens(0);
        if (row == 8) { // 8 个棋子都放置好了，打印结果
            printQueens();
            return; // 8 行棋子都放好了，已经没法再往下递归了，所以就 return
        }
        for (int column = 0; column < 8; ++column) { // 每一行都有 8 中放法
            if (isOk(row, column)) { // 有些放法不满足要求
                queens[row] = column; // 第 row 行的棋子放到了 column 列
                cal8queens(row+1); // 考察下一行
            }
        }
    }

    private boolean isOk(int row, int column) {// 判断 row 行 column 列放置是否合适
        int leftup = column - 1, rightup = column + 1;
        for (int i = row-1; i >= 0; --i) { // 逐行往上考察每一行
            if (queens[i] == column) {
                return false; // 第 i 行的 column 列有棋子吗？
            }
            if (leftup >= 0) { // 考察左上对角线：第 i 行 leftup 列有棋子吗？
                if (queens[i] == leftup) {
                    return false;
                }
            }
            if (rightup < 8) { // 考察右上对角线：第 i 行 rightup 列有棋子吗？
                if (queens[i] == rightup) {
                    return false;
                }
            }
            --leftup; ++rightup;
        }
        return true;
    }

    protected void printQueens() {
        System.out.println("\n解答" + ++count);
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (queens[row] == column) {
                    System.out.print("Q");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        EightQueenTest queen = new EightQueenTest();
        queen.cal8queens(0);
    }
}

