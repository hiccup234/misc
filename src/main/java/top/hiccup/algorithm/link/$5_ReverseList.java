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
        // 虚拟头节点
        Node dummy = new Node();
        Node pCur = head;
        while (pCur != null) {
            // 临时保存剩余链表
            Node pNex = pCur.next;
            // 把当前节点转换到新链表的头节点去
            pCur.next = dummy.next;
            dummy.next = pCur;
            // 继续遍历老链表节点
            pCur = pNex;
        }
        return dummy.next;
    }

    /**
     * 就地反转法（不会新建一个临时引用）
     */
    public static Node reverse2(Node head) {
        if (head == null) {
            return head;
        }
        // 虚拟头节点
        Node dummy = new Node();
        dummy.next = head;
        Node pCur = head.next;
        while (pCur != null) {
            // 其实就是借助反转后链表的尾节点（即入参的head）的next来作为临时变量
            // 因为反转后head.next会置为空，所以不会存储元素，可以拿来做临时存储空间，但是这里就需要把判断head==null的逻辑单独拿出来
            head.next = pCur.next;
            pCur.next = dummy.next;
            dummy.next = pCur;
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
