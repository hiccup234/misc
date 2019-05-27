package top.hiccup.algorithm.problem.geek;

/**
 * 用链表实现一个链式队列
 *
 * @author wenhy
 * @date 2019/5/27
 */
public class $12_LinkedListQueue {

    class Node {
        int val;
        Node next;
        public Node(int val) {
            this.val = val;
        }
    }

    int size;
    Node dummy;
    Node tail;

    public $12_LinkedListQueue() {
        size = 0;
        dummy = new Node(0);
        tail = dummy;
    }

    public void enqueue(int num) {
        tail.next = new Node(num);
        tail = tail.next;
    }

    public int dequeue() {
        if (dummy.next == null) {
            throw new RuntimeException("队列为空");
        }
        Node node = dummy.next;
        dummy.next = dummy.next.next;
        return node.val;
    }
}
