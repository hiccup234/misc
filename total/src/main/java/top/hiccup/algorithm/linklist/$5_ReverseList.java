package top.hiccup.algorithm.linklist;

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

    public static ListNode reverse1(ListNode head) {
        // 虚拟头节点
        ListNode dummy = new ListNode();
        ListNode pCur = head;
        while (pCur != null) {
            // 临时保存剩余链表
            ListNode pNex = pCur.next;
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
    public static ListNode reverse2(ListNode head) {
        if (head == null) {
            return head;
        }
        // 虚拟头节点
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode pCur = head.next;
        while (pCur != null) {
            // 其实就是借助反转后链表的尾节点（即入参的head）的next来作为临时变量
            // 因为反转后head.next会置为空，所以不会存储元素，可以拿来做临时存储空间，但是这里就需要把判断head==null的逻辑单独拿出来
            head.next = pCur.next;
            pCur.next = dummy.next;
            dummy.next = pCur;
            // 最后一步会自动把head的next置为null，即新链表的结尾为null
            pCur = head.next;
        }
        return dummy.next;
    }

    /**
     * 不用虚拟头节点（注意判断head的写法）
     */
    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    @Test
    public void test() {
        ListNode head = ListNode.buildLinkedList(10, (k) -> k);
        ListNode.printLinkedList(head);
        ListNode reverse = reverse1(head);
        ListNode.printLinkedList(reverse);
        ListNode.printLinkedList(reverse2(reverse));
    }
}
