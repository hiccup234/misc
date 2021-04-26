package top.hiccup.algorithm.satck;


import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

/**
 * 最小栈：
 * 
 * 设计含最小函数min()、pop()、push()的栈MinStack，存储数据元素为int
 * 
 * 1、普通栈+一个临时保存最小值，出栈元素如果是最小值则要重新遍历整个栈找到最小值
 * 时间复杂度：O(n)  空间复杂度：O(1)
 * 
 * 2、空间换时间，利用辅助栈来保存当前最小值
 * 时间复杂度：O(1)  空间复杂度：O(n)
 *
 * @author wenhy
 * @date 2019/4/28
 */
public class $2_MinStack {

    private static class MinStack1 {
        private List<Integer> data = new LinkedList<>();
        private int min;

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
                min = data.get(0);
                for (int i = 1, size = data.size(); i < size; i++) {
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

    private static class MinStack2<E extends Comparable> {

        private LinkedList<E> stack = new LinkedList<>();
        private LinkedList<E> mins = new LinkedList<>();

        public void push(E data) {
            stack.push(data);
            if (mins.size() == 0) {
                mins.push(data);
            } else {
                // 这里stack栈和mins栈是同等长度的，比较浪费空间
                // 优化思路1：如果入栈的数大于当前mins栈顶则不入栈，等于的话还是需要入栈（因为如果把最小值出栈了，则mins里只有一个最小值出站后就没了）
                // 出栈时判断是否跟mins栈顶相等再决定mins是否出栈，有个问题就是如果连续入栈都是最小值也会造成一定的空间浪费
                E m = mins.peek();
                if (data.compareTo(m) < 0) {
                    mins.push(data);
                } else {
                    mins.push(m);
                }
                // 优化思路2：mins只保存data的中最小值的下标，每次pop时判断data的栈顶跟mins的栈顶是否相等，相等则把mins也出栈，
                // 这样可以把对象的判断equals方法换成整型的==
            }
        }

        public E pop() {
            mins.pop();
            return stack.pop();
        }

        public E min() {
            return mins.peek();
        }
    }

    @Test
    public void test() {
        MinStack2<Integer> minStack = new MinStack2<>();
        minStack.push(9);
        minStack.push(7);
        minStack.push(2);
        minStack.push(11);
        minStack.push(5);
        System.out.println(minStack.stack);
        System.out.println(minStack.mins);

        System.out.println(minStack.min());
        minStack.pop();
        System.out.println(minStack.min());
        minStack.pop();
        minStack.pop();
        System.out.println(minStack.min());
    }
}
