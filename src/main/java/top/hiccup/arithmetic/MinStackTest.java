package top.hiccup.arithmetic;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

/**
 * 可以获取最小值的栈
 * <p>
 * 1、普通栈+一个临时保存最小值，出栈元素如果是最小值则要重新遍历整个栈找到最小值
 * 时间复杂度：O(n)  空间复杂度：O(1)
 * 2、空间换时间，利用辅助栈来保存当前最小值
 *
 * @author wenhy
 * @date 2019/2/8
 */
public class MinStackTest {

    private class Stack1 {
        private int min;
        private List<Integer> data = new LinkedList<>();

        public void push(int num) {
            data.add(num);
            if (data.size() == 1) {
                min = num;
            } else if (num < min) {
                min = num;
            }
        }

        public int pop() {
            int result = data.remove(data.size() - 1);
            if (result == min) {
                int size = data.size();
                min = data.get(0);
                for (int i = 1; i < size; i++) {
                    if (data.get(i) < min) {
                        min = data.get(i);
                    }
                }
            }
            return result;
        }

        public int getMin() {
            return min;
        }
    }

    @Test
    public void test1() {
        Stack1 stack1 = new Stack1();
        stack1.push(9);
        stack1.push(5);
        stack1.push(7);
        stack1.push(3);
        System.out.println("最小值：" + stack1.getMin());
        System.out.println("出栈：" + stack1.pop());
        System.out.println("最小值：" + stack1.getMin());
        stack1.push(8);
        System.out.println("出栈：" + stack1.pop());
        System.out.println("最小值：" + stack1.getMin());
    }
}