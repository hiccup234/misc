package top.hiccup.algorithm.linkedlist;

import java.util.Random;

import org.junit.Test;

/**
 * 有序链表合并：
 *
 * 将两个有序链表（升序）合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 *
 * @author wenhy
 * @date 2019/4/1
 */
public class L4$MergeOrderdListTest {

    public static Node mergeList(Node head1, Node head2) {
        if(head1 == null && head2 == null) {
            return null;
        }
        if (head1 == null) return head2;
        if (head2 == null) return head1;
        Node retHead = null, retTail = null;
        // 先确定合并后的链表头
        if (head1.getVal() < head2.getVal()) {
            retHead = head1;
            head1 = head1.getNext();
        } else {
            retHead = head2;
            head2 = head2.getNext();
        }
        retTail = retHead;
        while (head1 != null && head2 != null) {
            if (head1.getVal() < head2.getVal()) {
                retTail.setNext(head1);
                head1 = head1.getNext();
            } else {
                retTail.setNext(head2);
                head2 = head2.getNext();
            }
            retTail = retTail.getNext();
        }
        if (head1 != null) retTail.setNext(head1);
        else if (head2 != null) retTail.setNext(head2);
        return retHead;
    }

    @Test
    public void test() {
        Random random = new Random(47);
        Node head1 = Node.buildLinkedList(10, (k) -> random.nextInt(20));
        Node head2 = Node.buildLinkedList(20, (k) -> random.nextInt(50));
        Node.printLinkedList(head1);
        Node.printLinkedList(head2);
        Node.printLinkedList(mergeList(head1, head2));
    }
}
