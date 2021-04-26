package top.hiccup.algorithm.linklist;

/**
 * 链表节点
 *
 * @author wenhy
 * @date 2019/3/31
 */
public class ListNode {
    int val;
    ListNode next;

    public ListNode() {}

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public ListNode(ListNode listNode) {
        this.val = listNode.val;
        this.next = listNode.next;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        return String.valueOf(val);
    }

    public static ListNode buildLinkedList(int n, Command command) {
        ListNode head = null;
        ListNode tail = null;
        for (int i = 0; i < n; i++) {
            ListNode listNode = new ListNode();
            listNode.val = command.getVal(i);
            if (i == 0) {
                head = listNode;
                tail = listNode;
                continue;
            }
            tail.next = listNode;
            tail = listNode;
        }
        return head;
    }

    public static void printLinkedList(ListNode head) {
        if (head == null) {
            System.out.println("none list");
        }
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (ListNode p = head; p != null; p = p.next) {
            sb.append(++count + ":" + p);
            if (p.next != null) {
                sb.append(" -> ");
            }
        }
        System.out.println(sb.toString());
    }
}

interface Command {
    int getVal(int i);
}