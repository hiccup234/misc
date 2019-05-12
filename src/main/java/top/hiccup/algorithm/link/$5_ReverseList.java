package top.hiccup.algorithm.link;

import org.junit.Test;

/**
 * 反转单链表
 * 
 * 1、新建链表反转，一直在链表表头插入
 *
 * 2、就地反转法（不会新建一个临时引用）
 * 
 * @author wenhy
 * @date 2019/4/3
 */
public class $5_ReverseList {

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

    /**
     * 不用虚拟头节点（注意判断head的写法）
     */
    public static Node reverseList(Node head) {
        Node prev = null;
        Node curr = head;
        while (curr != null) {
            Node nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
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
