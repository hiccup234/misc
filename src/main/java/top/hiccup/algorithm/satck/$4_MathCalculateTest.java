package top.hiccup.algorithm.satck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.junit.Test;

/**
 * 请实现一个简单计算器功能，实现以下接口
 * 
 * double calc(string st);
 * 
 * 要求如下:
 * 支持加，减，乘，除运算
 * 支持括号的优先级
 * 算法运行效率尽量高效
 * 占用空间尽量少
 * 支持小数
 * 例如：输入字符串“(6+4)*2/(1.1+2.9)”,输出5
 * 
 * 思路：借鉴JVM执行字节码的基于栈的指令架构
 *
 * @author wenhy
 * @date 2019/4/23
 */
public class $4_MathCalculateTest {

    public static int calculate(String s) {
        java.util.LinkedList<Integer> stackData = new java.util.LinkedList<>();
        java.util.LinkedList<Character> stackOp = new java.util.LinkedList<>();
        java.util.Map<Character, Integer> priorityMap = new HashMap<>(8);
        priorityMap.put('(', -1);
        priorityMap.put(')', -1);
        priorityMap.put('*', -2);
        priorityMap.put('/', -2);
        priorityMap.put('+', -3);
        priorityMap.put('-', -3);

        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            Character c = chars[i];
            // 如果遇到')'，则说明这个子表达式必须要运算完成
            if (c == ' ') {
                continue;
            }
            if (c == ')') {
                while (stackOp.peek() != '(') {
                    Character cal = stackOp.pop();
                    Integer d1 = stackData.pop();
                    Integer d2 = stackData.pop();
                    if ('+' == cal.charValue()) {
                        stackData.push(d1 + d2);
                    } else if ('-' == cal.charValue()) {
                        stackData.push(d2 - d1);
                    }
                }
                stackOp.pop();
                continue;
            }

            Integer priority = null;
            if ((priority = priorityMap.get(c)) != null) {
                // 计算前一个操作符
                if (stackOp.size() > 0 && priorityMap.get(stackOp.peek()).intValue() < -1
                        && priorityMap.get(stackOp.peek()).intValue() >= priority) {
                    Character cal = stackOp.pop();
                    Integer d1 = stackData.pop();
                    Integer d2 = stackData.pop();
                    if ('+' == cal.charValue()) {
                        stackData.push(d1 + d2);
                    } else if ('-' == cal.charValue()) {
                        stackData.push(d2 - d1);
                    }
                }
                stackOp.push(c);
            } else {
                if (Character.isDigit(c)) {
                    int num = c - '0';
                    i++;
                    while (i < chars.length && chars[i] >= '0' && chars[i] <= '9') {
                        num = num * 10 + (chars[i] - '0');
                        i++;
                    }
                    stackData.push(num);
                    i = i - 1;
                }
            }
        }

        while (stackOp.size() != 0) {
            Character c = stackOp.pop();
            if ('+' == c.charValue()) {
                Integer d1 = stackData.pop();
                Integer d2 = stackData.pop();
                stackData.push(d1 + d2);
            } else if ('-' == c.charValue()) {
                Integer d1 = stackData.pop();
                Integer d2 = stackData.pop();
                stackData.push(d2 - d1);
            }
        }
        return stackData.pop();
    }

    public static void main(String[] args) {
        System.out.println(calculate("(1+(4+5+2)-3)+(6+8)"));
//        System.out.println(calculate(" 2-1 + 2 "));
    }
















    // 43+     45-    42*     47/     40(    41)
    public static double calc(String s) {
        if (s == null) {
            throw new RuntimeException("Invalid input");
        }
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (!((c >= '0' && c <= '9')
                    || c == '.'
                    || c == '+'
                    || c == '-'
                    || c == '*'
                    || c == '/'
                    || c == '('
                    || c == ')')) {
                throw new RuntimeException("Invalid input");
            }
        }

        List<String> serl = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            // 如果是数字
            if ((chars[i] >= 48 && chars[i] <= 57) || chars[i] == 46) {
                int p = i + 1;
                int count = 1;
                while((chars[p] >= 48 && chars[p] <= 57) || chars[p] == 46) {
                    count++;
                    p++;
                }
                String tmp = String.copyValueOf(chars, i, count);
                serl.add(tmp);
                i += count-1;
            } else {
                serl.add(new String(new char[]{chars[i]}));
            }
        }

        return 0;
    }



    /**
     * 中缀表达式转化为后缀表达式
     * 1、遇到数字输出
     * 2、遇到高优先级的全部出栈
     * 3、最后全部出栈
     */
    public List<String> InfixToPostfix(List<String> list){
        // 存放后缀表达式
        List<String> postfixlist = new ArrayList<>();
        // 暂存操作符
        LinkedList<String> stack = new LinkedList<>();
        //stack.push('#');
        for(int i=0;i<list.size();i++){

            String s = list.get(i);
            if(s.equals("(")){
                stack.push(s);
            }else if(s.equals("*")||s.equals("/")){
                stack.push(s);
            }else if(s.equals("+")||s.equals("-")){
                if(stack.size() > 0){
                    while(!(stack.peek().equals("("))){
                        postfixlist.add(stack.pop());
                        if(stack.size() == 0){
                            break;
                        }
                    }
                    stack.push(s);
                }else{
                    stack.push(s);
                }
            }else if(s.equals(")")){
                while(!(stack.peek().equals("("))){
                    postfixlist.add(stack.pop());
                }
                stack.pop();
            }else{
                postfixlist.add(s);
            }
            if(i==list.size()-1){
                while(stack.size() > 0){
                    postfixlist.add(stack.pop());
                }
            }
        }
        return postfixlist;
    }
    public char[] op = {'+','-','*','/','(',')'};
    public String[] strOp = {"+","-","*","/","(",")"};

    public boolean isOp(String s){
        for(int i=0;i<strOp.length;i++){
            if(strOp[i].equals(s)){
                return true;
            }
        }
        return false;
    }

    /**
     * 后缀表达式计算
     */
    public int doCal(List<String> list) {
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            int t = 0;
            if (!isOp(s)) {
                t = Integer.parseInt(s);
                stack.push(t);
            } else {
                if (s.equals("+")) {
                    int a1 = stack.pop();
                    int a2 = stack.pop();
                    int v = a2 + a1;
                    stack.push(v);
                } else if (s.equals("-")) {
                    int a1 = stack.pop();
                    int a2 = stack.pop();
                    int v = a2 - a1;
                    stack.push(v);
                } else if (s.equals("*")) {
                    int a1 = stack.pop();
                    int a2 = stack.pop();
                    int v = a2 * a1;
                    stack.push(v);
                } else if (s.equals("/")) {
                    int a1 = stack.pop();
                    int a2 = stack.pop();
                    int v = a2 / a1;
                    stack.push(v);
                }
            }
        }
        return stack.pop();
    }

    @Test
    public void test() {
        String s = "(6+4)*2/(1.1+2.9)";
        System.out.println(calc(s));
    }

}






