package top.hiccup.algorithm.linkedlist;

import java.util.Random;

import org.junit.Test;

/**
 * 用链表实现大数相加：
 * @see https://leetcode.com/problems/add-two-numbers
 *
 * 两个用链表代表的整数，其中每个节点包含一个数字（个位数字）。数字存储按形式如下，相加的结果也用链表形式存储。
 *
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8  注意这里时向后进位，而不是数学上的向前进位
 * Explanation: 342 + 465 = 807.
 *
 * @author wenhy
 * @date 2019/4/1
 */
public class L3$AddBigNumTest {

    public static Node addBigNum(Node head1, Node head2) {
        Node n1 = head1, n2 = head2;
        // 用新链表存储计算结果
        Node retHead = null, retTail = null;
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
            Node node = new Node();
            if (sum > 10) {
                node.val = sum - 10;
                up = sum/10;
            } else {
                node.val = sum;
                up = 0;
            }
            if (n1 != null) {
                n1 = n1.next;
            }
            if (n2 != null) {
                n2 = n2.next;
            }
            if (retHead == null) {
                retHead = node;
                retTail = node;
            } else {
                retTail.next = node;
                retTail = node;
            }

        }
        if (up > 0) {
            Node node = new Node();
            node.val = up;
            retTail.next = node;
        }
        return retHead;
    }

    @Test
    public void test() {
        Random random = new Random(47);
        Node head1 = Node.buildLinkedList(10, (k) -> random.nextInt(10));
        Node head2 = Node.buildLinkedList(20, (k) -> random.nextInt(10));
        Node.printLinkedList(head1);
        Node.printLinkedList(head2);
        Node.printLinkedList(addBigNum(head1, head2));
    }
}
