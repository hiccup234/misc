package top.hiccup.algorithm.linklist;

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
public class $6_TwoListMeeting {

    public static ListNode meeting(ListNode head1, ListNode head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        ListNode tail = head1;
        while (tail != null && tail.next != null) {
            tail = tail.next;
        }
        tail.next = head2;
        // 判断是否有环
        if ($2_CycledList.cycled2(tail)) {
            // 这里记得断开环
            tail.next = null;
            ListNode prev = head1;
            ListNode pCur = head1;
            while (pCur != null && pCur != tail) {
                prev = pCur;
                pCur = pCur.next;
                prev.next = null;
            }
            ListNode tail2 = head2;
            while (tail2 != null && tail2.next != null) {
                tail2 = tail2.next;
            }
            return tail2;
        } else {
            return null;
        }
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        // 先找到A的尾节点
        ListNode tailA = headA;
        while (tailA.next != null) {
            tailA = tailA.next;
        }
        // 把A尾节点指向B头节点，再判断是否有环路，是的话则证明两个链表相交（记得要恢复回去）
        tailA.next = headB;
        ListNode fast = headA;
        ListNode slow = headA;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            // 证明有环
            if (fast == slow) {
                slow = headA;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                tailA.next = null;
                return slow;
            }
        }
        tailA.next = null;
        return null;
    }

    @Test
    public void test() {
        ListNode head1 = ListNode.buildLinkedList(10, (k) -> k+1);
        Random random = new Random(47);
        ListNode head2 = ListNode.buildLinkedList(15, (k) -> random.nextInt(10));
        ListNode.printLinkedList(head1);
        ListNode.printLinkedList(head2);
        // 使相交
        ListNode t1 = head1;
        for (int i=0; i< 9; i++) {
            t1 = t1.next;
        }
        ListNode tail2 = head2;
        while (tail2 != null && tail2.next != null) {
            tail2 = tail2.next;
        }
        tail2.next = t1;
        ListNode.printLinkedList(head1);
        ListNode.printLinkedList(head2);
        System.out.println(meeting(head1, head2));
    }
}
