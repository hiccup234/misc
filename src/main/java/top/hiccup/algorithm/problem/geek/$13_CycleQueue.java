package top.hiccup.algorithm.problem.geek;

/**
 * 实现一个循环队列
 *
 * @author wenhy
 * @date 2019/5/27
 */
public class $13_CycleQueue {

    /**
     * 需要浪费一个空间，来区分空和满的情况（head == tail 为空，(tail+1)%n==head为满），或者采用cout计数
     */
    private int head;
    private int tail;
    private int[] table;

    public $13_CycleQueue(int n) {
        head = 0;
        tail = 0;
        table = new int[n];
    }

    public void enqueue(int num) {
        if ((tail + 1) % table.length == head) {
            throw new RuntimeException("队列已满");
        }
        table[tail] = num;
        tail = (tail + 1) % table.length;
    }

    public int dequeue() {
        if (head == tail) {
            throw new RuntimeException("队列为空");
        }
        int result = table[head];
        head = (head + 1) % table.length;
        return result;
    }
}
