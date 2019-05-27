package top.hiccup.algorithm.problem.geek;

/**
 * 实现求链表的中间结点
 *
 * @author wenhy
 * @date 2019/5/27
 */
public class $7_MiddleLinkedList {

    static class Node {
        int val;
        Node next;
    }

    public static Node middleNode(Node head) {
        if (head == null) {
            return null;
        }
        Node slow = head;
        Node fast = head;
        // 如果是求1/3节点，则fast=fast.next.next.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

}
