package top.hiccup.algorithm.problem;

/**
 * 斐波拉契数列常见优化策略和思路
 *  f(0) = 0;
 *  f(1) = 1;
 *  f(n) = f(n-1) + f(n-2) (当n>=2);
 *
 * 1.递归法：普通的递归模式，用代码直接翻译数学表达式，存在的问题就是当n较大时，递归次数非常非常大，且存在大量的重复计算
 *
 * 2.正推法：将递归修改为for循环，从0，1到n依此计算，防止重复计算（尾递归）
 *
 * 3.通项公式法：参考https://zh.wikipedia.org/wiki/%E6%96%90%E6%B3%A2%E9%82%A3%E5%A5%91%E6%95%B0%E5%88%97
 *
 * 4.查表法：提前计算好结果缓存起来
 *
 * 【青蛙跳台阶】
 * 题目：一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个n级的台阶总共有多少种跳法？
 * 思路：1）如果只有1级台阶，那只有一种跳法
 *      2）如果有2级台阶，那么就有2种跳法，一种是分2次跳，每次跳1级，另一种就是一次跳2级
 *      3）如果台阶级数大于2，设为n的话，这时我们把n级台阶时的跳法看成n的函数，
 *      第一次跳的时候有2种不同的选择：一是第一次跳一级，此时跳法的数目等于后面剩下的n-1级台阶的跳法数目，
 *                               二是第一次跳二级，此时跳法的数目等于后面剩下的n-2级台阶的跳法数目，
 *      因此n级台阶的不同跳法的总数为 f(n) = f(n-1) + f(n-2)，这就就是斐波那契数列了。
 *
 * @author wenhy
 * @date 2018/10/9
 */
public class FibonacciTest {

    static long fibonacci1(long n) {
        if (n < 2) {
            return n;
        }
        return fibonacci1(n-1) + fibonacci1(n-2);
    }

    static long fibonacci2(int n) {
        int[] arr = new int[n+1];
        arr[0] = 0;
        arr[1] = 1;
        for (int i=2; i<=n; i++) {
            arr[i] = arr[i-1] + arr[i-2];
        }
        return arr[n];
    }

    static long fibonacci3(int n) {
        double const_a = (1 + Math.sqrt(5)) / 2;
        double const_b = (1 - Math.sqrt(5)) / 2;
        double const_c = Math.sqrt(5) / 5;
        double result = const_c*(Math.pow(const_a, n)- Math.pow(const_b, n));
        System.out.println(result);
        return (long) result;
    }

    public static long fibonacci4(int n) {
        if (n < 2) {
            return n;
        }
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

    public static void main(String[] args) {
        System.out.println(fibonacci1(24));
        System.out.println(fibonacci2(24));
        System.out.println(fibonacci3(24));
        System.out.println(fibonacci4(24));
    }

}
