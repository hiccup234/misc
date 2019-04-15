package top.hiccup.algorithm.problem;

/**
 * 八皇后问题是一个以国际象棋为背景的问题：
 *    如何能够在 8×8 的国际象棋棋盘上放置八个皇后，使得任何一个皇后都无法直接吃掉其他的皇后？为了达到此目的，任两个皇后都不能处于同一条横行、纵行或斜线上。
 *    八皇后问题可以推广为更一般的n皇后摆放问题：这时棋盘的大小变为n1×n1，而皇后个数也变成n2。而且仅当 n2 ≥ 1 或 n1 ≥ 4 时问题有解。
 *
 * 【回溯法】
 * 92个解中，只有12个独立解（跟其他解不是对称的）
 *
 * @author wenhy
 * @date 2019/4/15
 */
public class EightQueenTest {

    private int[] column; //同栏是否有皇后，1表示有
    private int[] rup; //右上至左下是否有皇后
    private int[] lup; //左上至右下是否有皇后
    private int[] queen; //解答
    private int num; //解答编号

    public EightQueenTest() {
        column = new int[8+1];
        rup = new int[(2*8)+1];
        lup = new int[(2*8)+1];
        for (int i = 1; i <= 8; i++)
            column[i] = 0;
        for (int i = 1; i <= (2*8); i++)
            rup[i] = lup[i] = 0;  //初始定义全部无皇后
        queen = new int[8+1];
    }

    public void backtrack(int i) {
        if (i > 8) {
            showAnswer();
        } else {
            for (int j = 1; j <= 8; j++) {
                if ((column[j] == 0) && (rup[i+j] == 0) && (lup[i-j+8] == 0)) {
                    //若无皇后
                    queen[i] = j; //设定为占用
                    column[j] = rup[i+j] = lup[i-j+8] = 1;
                    backtrack(i+1);  //循环调用
                    column[j] = rup[i+j] = lup[i-j+8] = 0;
                }
            }
        }
    }

    protected void showAnswer() {
        num++;
        System.out.println("\n解答" + num);
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                if(queen[y]==x) {
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
        queen.backtrack(1);
    }
}
