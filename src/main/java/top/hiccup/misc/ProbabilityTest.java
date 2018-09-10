package top.hiccup.misc;

/**
 * Created by wenhy on 2018/1/12.
 */
public class ProbabilityTest {

    /**
     * 54张扑克牌，除去两张大小王剩下52张扑克牌。问红桃A和黑桃A同时被一个人拿到的概率是多少？
     * 任意排列的种数有C(52,13)*C(39,13)*C(26*13)
        其中两个A在一家的种数为
        C(4,1)*C(50,11)*C(39,13)*C(26*13)
        因此概率为
        C(4,1)*C(50,11)*C(39,13)*C(26*13)
        --------------------------------
        C(52,13)*C(39,13)*C(26*13)
        =4*C(50,11)/C(52,13) = 4*(50!/11!/39!) / (52!/13!/39!)
        = 4*13*12/51/52
        =12/51
     */
    public static void main(String[] args) {
        System.out.println(ProbabilityTest.factorial(52));
        System.out.println(ProbabilityTest.combination(13, 12));
    }


    /**
     * 计算阶乘数，即 n! = n * (n-1) * ... * 2 * 1
     * 容易溢出
     */
    private static long factorial(int n) {
        return (n > 1) ? n * factorial(n - 1) : 1;
    }

    /**
     * 计算排列数，即 A(n, m) = n!/(n-m)!
     */
    public static long arrangement(int n, int m) {
        return (n >= m) ? factorial(n) / factorial(n - m) : 0;
    }

    /**
     * 计算组合数，即 C(n, m) = n!/((n-m)! * m!)
     */
    public static long combination(int n, int m) {
        return (n >= m) ? factorial(n) / factorial(n - m) / factorial(m) : 0;
    }

}
