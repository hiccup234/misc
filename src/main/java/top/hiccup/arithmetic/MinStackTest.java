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
 * 时间复杂度：O(1)  空间复杂度：O(n)
 *
 * @author wenhy
 * @date 2019/2/8
 */
public class MinStackTest {

    private class MinStack1 {
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
        MinStack1 stack1 = new MinStack1();
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

    private class MinStack2 {
        private List<Integer> data = new LinkedList<>();
        private List<Integer> mins = new LinkedList<>();

        public void push(int num) throws Exception {
            data.add(num);
            if (mins.size() == 0) {
                mins.add(num);
            } else {
                // 这里data栈和mins栈是同等长度的，比较浪费空间
                // 优化思路：如果入栈的数大于当前mins栈顶则不入栈，等于的话还是需要入栈（因为如果把最小值出栈了，则mins里只有一个最小值出站后就没了）
                // 出栈时判断是否跟mins栈顶相等再决定mins是否出栈，有个问题就是如果连续入栈都是最小值也会造成一定的空间浪费
                int min = getMin();
                if (num >= min) {
                    mins.add(min);
                } else {
                    mins.add(num);
                }
                // 优化思路2：mins只保存data的中最小值的下标，每次pop时判断data的栈顶跟mins的栈顶是否相等，相等则把mins也出栈
//                int min = getMin();
//                if (num < min) {
//                    mins.add(data.size() - 1);
//                }
            }
        }

        public int pop() {
            mins.remove(mins.size() - 1);
            return data.remove(data.size() - 1);
        }

        public int getMin() throws Exception {
            if (mins.size() == 0) {
                throw new Exception("最小值为空");
            }
            return mins.get(mins.size() - 1);
        }
    }

    @Test
    public void test2() throws Exception {
        MinStack2 stack2 = new MinStack2();
        stack2.push(9);
        stack2.push(5);
        stack2.push(7);
        stack2.push(3);
        System.out.println("最小值：" + stack2.getMin());
        System.out.println("出栈：" + stack2.pop());
        System.out.println("最小值：" + stack2.getMin());
        stack2.push(8);
        System.out.println("出栈：" + stack2.pop());
        System.out.println("最小值：" + stack2.getMin());
    }
}