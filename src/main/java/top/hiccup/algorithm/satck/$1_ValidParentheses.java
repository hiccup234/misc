package top.hiccup.algorithm.satck;

import java.util.LinkedList;

/**
 * 判断是否有效的表达式
 *
 * @author wenhy
 * @date 2019/5/10
 */
public class $1_ValidParentheses {

    public boolean isValid(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        LinkedList<Character> stack = new LinkedList<>();
        char[] chars = s.toCharArray();
        for (int i=0; i<chars.length; i++) {
            char c = chars[i];
            if (stack.size() == 0) {
                stack.push(c);
                continue;
            }
            Character left = stack.peek();
            if (isMatch(left, c)) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }
        return stack.size() == 0;
    }

    private boolean isMatch(char left, char right) {
        boolean flag = false;
        if (left == '(' && right == ')') {
            flag = true;
        } else if (left == '[' && right == ']') {
            flag = true;
        } else if (left == '{' && right == '}') {
            flag = true;
        }
        return flag;
    }
}