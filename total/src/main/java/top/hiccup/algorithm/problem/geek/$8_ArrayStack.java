package top.hiccup.algorithm.problem.geek;

/**
 * 用数组实现一个顺序栈
 *
 * @author wenhy
 * @date 2019/5/27
 */
public class $8_ArrayStack {
    /**
     * 栈顶指针
     */
    private int top;
    private int[] table;

    public $8_ArrayStack(int n) {
        top = 0;
        table = new int[n];
    }

    public void push(int num) {
        if (top >= table.length) {
            throw new RuntimeException("栈已满");
        }
        table[top] = num;
        top++;
    }

    public int pop() {
        if (top == 0) {
            throw new RuntimeException("栈为空");
        }
        top--;
        return table[top];
    }
}
