package top.hiccup.algorithm.problem;

/**
 * 以下是在编程面试中排名前10的算法相关的概念
 *
 * @author wenhy
 * @date 2019/2/22
 */
public class Top10AlgorithmTest {
}


class Node {
    private int val;
    private Node next;

    public Node(int n) {
        val = n;
        next = null;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}

class Stack {
    private Node top;

    public Node peek() {
        return top;
    }

    public Node pop() {
        if (top != null) {
            Node node = top;
            top = top.getNext();
            return node;
        }
        return top;
    }

    public void push(Node node) {
        if (node != null) {
            node.setNext(top);
            top = node;
        }
    }
}

//class Queue {
//    private ListNode head, tail;
//
//    public void enqueue(ListNode node) {
//        if (tail != null) {
//            tail.setNext(node);
//        } else {
//            tail = head = node;
//        }
//    }
//
//    public ListNode dequeue() {
//        if (head != null) {
//            ListNode node = head;
//            head = head.getNext();
//            return node;
//        }
//        return head;
//    }
//}
