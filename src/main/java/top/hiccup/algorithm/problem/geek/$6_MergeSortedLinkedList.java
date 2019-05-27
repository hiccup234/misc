package top.hiccup.algorithm.problem.geek;

/**
 * 实现两个有序的链表合并为一个有序链表
 *
 * @author wenhy
 * @date 2019/5/27
 */
public class $6_MergeSortedLinkedList {

    static class Node {
        int val;
        Node next;
    }

    public static Node merge(Node head1, Node head2) {
        if (head1 == null && head2 == null) {
            return null;
        }
        Node dummy = new Node();
        Node tail = dummy;
        Node p1 = head1, p2 = head2;
        while (p1 != null && p2 != null) {
            if (p1.val <= p2.val) {
                tail.next = p1;
                tail = p1;
                p1 = p1.next;
            } else {
                tail.next = p2;
                tail = p2;
                p2 = p2.next;
            }
        }

        if (p1 != null) {
            tail.next = p1;
        }

        if (p2 != null) {
            tail.next = p2;
        }
        return dummy.next;
    }
}
