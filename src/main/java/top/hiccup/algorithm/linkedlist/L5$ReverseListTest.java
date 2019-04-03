package top.hiccup.algorithm.linkedlist;

import org.junit.Test;

/**
 * 反转单链表
 * 
 * 1、新建链表反转，一直在链表表头插入
 *
 * 2、就地反转法
 * 
 * @author wenhy
 * @date 2019/4/3
 */
public class L5$ReverseListTest {

    public static Node reverse1(Node head) {
        if (head == null) {
            return head;
        }
        // 虚拟头节点
        Node dummy = new Node();
        Node pCur = head;
        Node pNex = null;
        while (pCur != null) {
            // 临时保存剩余链表
            pNex = pCur.next;
            // 把当前节点转换到新链表的头节点去
            pCur.next = dummy.next;
            dummy.next = pCur;
            // 继续遍历老链表节点
            pCur = pNex;
        }
        return dummy.next;
    }

    public static Node reverse2(Node head) {
        if (head == null) {
            return head;
        }
        // 虚拟头节点
        Node dummy = new Node();
        dummy.next = head;
        Node pCur = head.next;
        while (pCur != null) {
            head.next = pCur.next;
            // 当前节点的下一个节点指向反转过后的链表（头节点）
            pCur.next = dummy.next;
            // 虚节点指向反转后链表的最新头节点
            dummy.next = pCur;
            // 继续遍历剩下的节点
            pCur = head.next;
        }
        return dummy.next;
    }

    @Test
    public void test() {
        Node head = Node.buildLinkedList(10, (k) -> k);
        Node.printLinkedList(head);
        Node reverse = reverse1(head);
        Node.printLinkedList(reverse);
        Node.printLinkedList(reverse2(reverse));
    }
}
