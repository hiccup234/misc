package top.hiccup.algorithm.problem.geek;

import java.util.LinkedList;

/**
 * 编程实现一组数据集合的全排列
 *
 * @author wenhy
 * @date 2019/5/27
 */
public class $16_Permutation {
}



class Solution {
    public int longestValidParentheses(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int count = 0;
        LinkedList<Character> statck = new LinkedList<>();
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (c == '(' || statck.size() == 0) {
                statck.push(c);
            } else {
                Character top = statck.peek();
                if (top == '(') {
                    statck.pop();
                    count += 2;
                } else {
                    statck.push(c);
                }
            }
        }
        return count;
    }
}