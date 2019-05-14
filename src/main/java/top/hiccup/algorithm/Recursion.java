package top.hiccup.algorithm;

import java.util.Map;

import top.hiccup.jdk.container.jdk7.HashMap;

/**
 * 递归调用：写出递推公式，找到终止条件
 * 
 * 1、存在堆栈溢出的风险：考虑边界条件、限制递归深度
 * 
 * 2、重复计算的问题：用HashMap保存已经计算的结果，如果已经计算则直接从HashMap取值并返回
 * 
 * 3、函数调用耗时较多：改成非递归的代码，函数的递归本质也是栈的压栈和出栈，所以所有递归都可改成非递归
 *
 * @author wenhy
 * @date 2019/5/14
 */
public class Recursion {

    private static Map<Integer, Integer> result = new HashMap<>(64);

    /**
     * 防止重复计算
     */
    public static int f(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        if (result.containsKey(n)) {
            return result.get(n);
        }
        int ret = f(n - 1) + f(n - 2);
        result.put(n, ret);
        return ret;
    }

    /**
     * 改写成非递归的形式，用栈模拟调用的形式可以参考MathCalculateTest.calculate
     */
    public static int f2(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int ret = 0;
        int pre = 2;
        int prepre = 1;
        for (int i = 3; i < n; i++) {
            ret = pre + prepre;
            prepre = pre;
            pre = pre + ret;
        }
        return ret;
    }

}
