package top.hiccup.algorithm.problem.geek;

/**
 * 实现单链表反转
 *
 * @author wenhy
 * @date 2019/5/27
 */
public class $5_ReverseLinkedList {

    static class Node {
        int val;
        Node next;
    }

    public static Node reverse(Node head) {
        Node dummy = new Node();
        Node pCur = head;
        while (pCur != null) {
            Node pNext = pCur.next;
            pCur.next = dummy.next;
            dummy.next = pCur;
            pCur = pNext;
        }
        return dummy.next;
    }
}
