package top.hiccup.algorithm.linklist;

import java.util.Random;

import org.junit.Test;

/**
 * 用链表实现大数相加：
 * 两个用链表代表的整数，其中每个节点包含一个数字（个位数字）。数字存储按形式如下，相加的结果也用链表形式存储。
 *
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8  注意这里时向后进位，而不是数学上的向前进位
 * Explanation: 342 + 465 = 807.
 *
 * @author wenhy
 * @date 2019/4/1
 * @see https://leetcode.com/problems/add-two-numbers
 */
public class $3_AddBigNum {

    public static ListNode addBigNum(ListNode head1, ListNode head2) {
        ListNode n1 = head1, n2 = head2;
        // 用新链表存储计算结果
        ListNode retHead = null, retTail = null;
        int up = 0;
        while (n1 != null || n2 != null) {
            int s1 = 0;
            if (n1 != null) {
                s1 = ((Integer) n1.val).intValue();
            }
            int s2 = 0;
            if (n2 != null) {
                s2 = ((Integer) n2.val).intValue();
            }
            int sum = s1 + s2 + up;
            ListNode listNode = new ListNode();
            if (sum > 10) {
                listNode.val = sum - 10;
                up = sum/10;
            } else {
                listNode.val = sum;
                up = 0;
            }
            if (n1 != null) {
                n1 = n1.next;
            }
            if (n2 != null) {
                n2 = n2.next;
            }
            if (retHead == null) {
                retHead = listNode;
                retTail = listNode;
            } else {
                retTail.next = listNode;
                retTail = listNode;
            }

        }
        if (up > 0) {
            ListNode listNode = new ListNode();
            listNode.val = up;
            retTail.next = listNode;
        }
        return retHead;
    }

    @Test
    public void test() {
        Random random = new Random(47);
        ListNode head1 = ListNode.buildLinkedList(10, (k) -> random.nextInt(10));
        ListNode head2 = ListNode.buildLinkedList(20, (k) -> random.nextInt(10));
        ListNode.printLinkedList(head1);
        ListNode.printLinkedList(head2);
        ListNode.printLinkedList(addBigNum(head1, head2));
    }
}
