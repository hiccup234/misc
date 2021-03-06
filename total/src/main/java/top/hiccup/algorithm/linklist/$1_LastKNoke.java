package top.hiccup.algorithm.linklist;

import java.util.Random;

import org.junit.Test;

/**
 * 求单链表倒数第k个节点
 *
 * @author wenhy
 * @date 2019/3/31
 */
public class $1_LastKNoke {

    /**
     * 1、最容易想到的方法：先遍历出链表总长度count，再遍历count-k个节点即可，注意判断大小范围
     *
     * @param head 链表头节点
     * @param k
     * @return
     */
    public static ListNode lastK1(ListNode head, int k) {
        if (head == null || k <= 0) {
            return null;
        }
        // 1、先计算出链表长度
        int count = 0;
        for (ListNode tmp = head; tmp != null; tmp = tmp.next) {
            count++;
        }
        if (count < k) {
            return null;
        }
        ListNode tmp = head;
        // 这儿不能 i < count - k + 1，因为后面还要赋值tmp = tmp.next;类似+1的动作
        for (int i = 0; i < count - k; i++) {
            tmp = tmp.next;
        }
        return tmp;
    }

    /**
     * 2、递归遍历法：方法1的变种：递归遍历至末尾，从末尾开始返回，每返回一次num--，直到num为0则找到目标节点
     *
     * @param head
     * @param k
     * @return
     */
    private static int num = 0;
    public static ListNode lastK2(ListNode head, int k) {
        num = k;
        if (head == null || k <= 0) {
            return null;
        }
        ListNode listNode = lastK2(head.next, k);
        // 如果不为空则证明找到了倒数k的节点，并直接一路返回
        if (listNode != null) {
            return listNode;
        } else {
            num --;
            if (num == 0) {
                // 返回倒数第K个节点
                return head;
            }
            return null;
        }
    }

    /**
     * 3、双指针法：指针1先走k步，然后指针2再同指针1一起走，直到指针1到达末尾，需要注意边界问题
     *   面试官常爱问的
     * @param head
     * @param k
     * @return
     */
    public static ListNode lastK3(ListNode head, int k) {
        if (head == null || k <= 0) {
            return null;
        }
        ListNode n2 = null;
        int count = 0;
        for (ListNode n1 = head; n1 != null; n1 = n1.next) {
            count++;
            if (count == k) {
                n2 = head;
            } else if (count > k) {
                n2 = n2.next;
            }
        }
        return n2;
    }

    /**
     * 删除倒数第n个元素，n从1开始（注意dummy节点的使用）
     * @see https://leetcode.com/problems/remove-nth-node-from-end-of-list/
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || n <= 0) {
            return null;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode listNodeN = dummy;
        ListNode pCur = dummy;
        int i;
        for (i=0; i < n + 1 && pCur != null; i++) {
            pCur = pCur.next;
        }
        if (i != n) {
            return null;
        }
        while (pCur != null) {
            pCur = pCur.next;
            listNodeN = listNodeN.next;
        }
        listNodeN.next = listNodeN.next.next;
        return dummy.next;
    }

    @Test
    public void test1() {
        Random random = new Random(47);
        for (int i = 0; i < 100; i++) {
            ListNode head = ListNode.buildLinkedList(100, (k) -> k+1);
            ListNode.printLinkedList(head);
            System.out.println(lastK1(head, 1));
        }
    }

    @Test
    public void test2() {
        ListNode head = ListNode.buildLinkedList(100, (k) -> k+1);
        ListNode.printLinkedList(head);
        System.out.println(lastK2(head, 100));
    }

    @Test
    public void test3() {
        ListNode head = ListNode.buildLinkedList(100, (k) -> k+1);
        ListNode.printLinkedList(head);
        System.out.println(lastK3(head, 1));
    }
}
