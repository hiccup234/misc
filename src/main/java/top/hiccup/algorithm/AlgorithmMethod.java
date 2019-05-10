package top.hiccup.algorithm;

import top.hiccup.jdk.container.jdk6.ArrayList;
import top.hiccup.jdk.container.jdk6.LinkedList;

/**
 * 算法思路
 *
 * 1、暴力法 -- 最简单
 *
 * 2、贪心法
 *
 * 3、递归回溯法
 *
 * 4、动态规划
 *
 * 5、
 *
 * @author wenhy
 * @date 2019/4/3
 */
public class AlgorithmMethod {
}


class MinStack {

    private LinkedList<Integer> stack;
    private LinkedList<Integer> minStack;

    public MinStack() {
        stack = new LinkedList<>();
        minStack = new LinkedList<>();
    }

    public void push(int x) {
        stack.push(x);
        if (minStack.size() == 0) {
            minStack.push(x);
        } else if (minStack.peek() >= x) {
            minStack.push(x);
        }
    }

    public void pop() {
        int val = stack.pop();
        if (val == minStack.peek()) {
            minStack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}

