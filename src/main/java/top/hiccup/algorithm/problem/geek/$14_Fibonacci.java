package top.hiccup.algorithm.problem.geek;

/**
 * 编程实现斐波那契数列求值 f(n)=f(n-1)+f(n-2...
 *
 * @author wenhy
 * @date 2019/5/27
 */
public class $14_Fibonacci {

    public static long fibonacci1(int n) {
        if (n < 2) {
            return n;
        }
        return fibonacci1(n - 1) + fibonacci1(n - 2);
    }

    public static long fibonacci2(int n) {
        if (n < 2) {
            return n;
        }
        // 注意prepre 和 pre的顺序不能反了
        long prepre = 0;
        long pre = 1;
        long x = 0;
        for (int i = 2; i <= n; i++) {
            x = prepre + pre;
            prepre = pre;
            pre = x;
        }
        return x;
    }
}
