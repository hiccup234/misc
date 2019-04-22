package top.hiccup.algorithm.link;

import java.util.Random;

import org.junit.Test;

/**
 * 判断连个链表是否相交并找出相交节点：
 *
 * 1、先遍历链表A到尾部，将尾部节点指向B的head，再判断B上是否有环，时间复杂度O(n)
 *
 * 2、对A遍历每个节点时把其next置为null，直到到达尾部，再遍历B到尾部（相交节点），缺点：破环了A链表
 *
 * @author wenhy
 * @date 2019/4/6
 */
public class L6$TwoListMeetingTest {

    public static Node meeting(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node tail = head1;
        while (tail != null && tail.next != null) {
            tail = tail.next;
        }
        tail.next = head2;
        // 判断是否有环
        if (L2$CycledListTest.cycled2(tail)) {
            // 这里记得断开环
            tail.next = null;
            Node prev = head1;
            Node pCur = head1;
            while (pCur != null && pCur != tail) {
                prev = pCur;
                pCur = pCur.next;
                prev.next = null;
            }
            Node tail2 = head2;
            while (tail2 != null && tail2.next != null) {
                tail2 = tail2.next;
            }
            return tail2;
        } else {
            return null;
        }
    }

    @Test
    public void test() {
        Node head1 = Node.buildLinkedList(10, (k) -> k+1);
        Random random = new Random(47);
        Node head2 = Node.buildLinkedList(15, (k) -> random.nextInt(10));
        Node.printLinkedList(head1);
        Node.printLinkedList(head2);
        // 使相交
        Node t1 = head1;
        for (int i=0; i< 9; i++) {
            t1 = t1.next;
        }
        Node tail2 = head2;
        while (tail2 != null && tail2.next != null) {
            tail2 = tail2.next;
        }
        tail2.next = t1;
        Node.printLinkedList(head1);
        Node.printLinkedList(head2);
        System.out.println(meeting(head1, head2));
    }
}
