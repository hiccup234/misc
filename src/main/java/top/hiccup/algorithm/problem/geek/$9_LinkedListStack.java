package top.hiccup.algorithm.problem.geek;

/**
 * 用链表实现一个链式栈
 *
 * @author wenhy
 * @date 2019/5/27
 */
public class $9_LinkedListStack {

    /**
     * 如果插入和删除都在链表头部进行，则不用pre
     */
    class Node {
        int val;
        Node next;
        public Node(int val) {
            this.val = val;
        }
    }

    int size;
    int count;
    Node dummy;

    public $9_LinkedListStack() {
        size = -1;
        count = 0;
        dummy = new Node(0);
    }

    public $9_LinkedListStack(int n) {
        size = n;
        count = 0;
        dummy = new Node(0);
    }

    public void push(int num) {
        if (size == -1 || count <= size) {
            Node node = new Node(num);
            node.next = dummy.next;
            dummy.next = node;
        } else {
            throw new RuntimeException("栈已满");
        }
    }

    public int pop() {
        if (count <= 0) {
            throw new RuntimeException("栈已空");
        }
        Node node = dummy.next;
        dummy.next = dummy.next.next;
        count--;
        return node.val;
    }
}
