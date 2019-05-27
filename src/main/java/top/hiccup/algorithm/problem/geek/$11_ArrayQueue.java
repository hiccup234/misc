package top.hiccup.algorithm.problem.geek;

/**
 * 用数组实现一个顺序队列
 *
 * @author wenhy
 * @date 2019/5/27
 */
public class $11_ArrayQueue {

    private int size;
    private int[] table;

    public $11_ArrayQueue(int n) {
        size = 0;
        table = new int[n];
    }

    public void push(int num) {
        if (size > table.length) {
            throw new RuntimeException("队列已满");
        }
        table[size] = num;
        size++;
    }

    public int pop() {
        if (size == 0) {
            throw new RuntimeException("队列为空");
        }
        int result = table[0];
        for (int i = 0; i < size - 1; i++) {
            table[i] = table[i + 1];
        }
        size--;
        return result;
    }
}
