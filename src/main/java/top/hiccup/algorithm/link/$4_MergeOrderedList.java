package top.hiccup.algorithm.link;

import org.junit.Test;

/**
 * 有序链表合并：
 *
 * 将两个有序链表（升序）合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 *
 * @author wenhy
 * @date 2019/4/1
 */
public class $4_MergeOrderedList {

    public static Node mergeList(Node head1, Node head2) {
        if(head1 == null && head2 == null) {
            return null;
        }
        if (head1 == null) return head2;
        if (head2 == null) return head1;
        Node retHead = null, retTail = null;
        // 先确定合并后的链表头
        if (head1.val < head2.val) {
            retHead = head1;
            head1 = head1.next;
        } else {
            retHead = head2;
            head2 = head2.next;
        }
        retTail = retHead;
        while (head1 != null && head2 != null) {
            if (head1.val < head2.val) {
                retTail.next = head1;
                head1 = head1.next;
            } else {
                retTail.next = head2;
                head2 = head2.next;
            }
            retTail = retTail.next;
        }
        if (head1 != null) retTail.next = head1;
        else if (head2 != null) retTail.next = head2;
        return retHead;
    }

    /**
     * 使用虚拟头节点，可以免除对合并后初始头节点的判断
     */
    public static Node merge(Node head1, Node head2) {
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

    @Test
    public void test() {
        Node head1 = Node.buildLinkedList(10, (k) -> (int)(k * 1.5) + 2);
        Node head2 = Node.buildLinkedList(20, (k) -> k * 2);
        Node.printLinkedList(head1);
        Node.printLinkedList(head2);
        Node.printLinkedList(mergeList(head1, head2));

        System.out.println("=================================");
        Node h1 = Node.buildLinkedList(10, (k) -> (int)(k * 1.5) + 2);
        Node h2 = Node.buildLinkedList(20, (k) -> k * 2);
        Node.printLinkedList(h1);
        Node.printLinkedList(h2);
        Node.printLinkedList(merge(h1, h2));
    }
}
