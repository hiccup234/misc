package top.hiccup.algorithm.linklist;

import org.junit.Test;

/**
 * 有序链表合并：将两个有序链表（升序）合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 *
 * @author wenhy
 * @date 2019/4/1
 */
public class $4_MergeOrderedList {

    public static ListNode mergeList(ListNode head1, ListNode head2) {
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }
        ListNode retHead = null, retTail = null;
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
        if (head1 != null) {
            retTail.next = head1;
        } else if (head2 != null) {
            retTail.next = head2;
        }
        return retHead;
    }

    /**
     * 使用虚拟头节点，可以免除对合并后初始头节点的判断
     */
    public static ListNode mergeList2(ListNode head1, ListNode head2) {
        ListNode dummy = new ListNode();
        ListNode tail = dummy;
        ListNode p1 = head1, p2 = head2;
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
        ListNode head1 = ListNode.buildLinkedList(10, (k) -> (int) (k * 1.5) + 2);
        ListNode head2 = ListNode.buildLinkedList(20, (k) -> k * 2);
        ListNode.printLinkedList(head1);
        ListNode.printLinkedList(head2);
        ListNode.printLinkedList(mergeList(head1, head2));

        System.out.println("=================================");
        ListNode h1 = ListNode.buildLinkedList(10, (k) -> (int) (k * 1.5) + 2);
        ListNode h2 = ListNode.buildLinkedList(20, (k) -> k * 2);
        ListNode.printLinkedList(h1);
        ListNode.printLinkedList(h2);
        ListNode.printLinkedList(mergeList(h1, h2));
    }
}
