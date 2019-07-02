package top.hiccup.algorithm.linklist;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * 判断单链表中是否有环路（变种：定位环入口、环长度等）
 *
 * @author wenhy
 * @date 2019/3/31
 */
public class $2_CycledList {

    /**
     * 1、穷举比较法：遍历的时候记录已经访问的节点，并与之前访问的做比较，时间复杂度O(n)
     * 注意这里要比较的是节点的内存地址是否相等，而不是元素是否相等，所以要求ListNode不能重写hashCode和equals方法
     */
    public static boolean cycled1(ListNode head) {
        if (head == null) {
            return false;
        }
        Set<ListNode> set = new HashSet<>();
        for (ListNode p = head; p != null; p = p.next) {
            if (set.contains(p)) {
                // 因为是从头遍历，所以p节点就是环入口，剩下的节点数就是环长度
                System.out.println("cycled node:" + p);
                return true;
            }
            set.add(p);
        }
        return false;
    }

    /**
     * 2、追赶法（快慢指针）：前后两个不同步长得指针各自遍历，如果有环则一定会相逢
     */
    public static boolean cycled2(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    /**
     * 更好的写法：只有一个循环条件
     */
    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    /**
     * 2.1、找出环入口：n1是慢指针，走s步，则n2走2s步，是n1的两倍
     *               如果n1从head出发，而n2从环上的相遇点出发，相同步长则他们一定会在环入口相遇（有等式可以验证）
     */
    public static ListNode cycled21(ListNode head) {
        boolean hasCycle = false;
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }
        // 如果没环则直接返回空
        if (!hasCycle) {
            return null;
        }
        ListNode p = head;
        while (p != slow) {
            p = p.next;
            slow = slow.next;
        }
        return p;
    }

    /**
     * 2.2、求环的长度：slow与fast第一次相遇为起点，再次相遇则slow的步数就是环的长度
     * @param head
     * @return
     */
    public static long cycled22(ListNode head) {
        boolean hasCycle = false;
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }
        // 如果没环则直接返回-1
        if (!hasCycle) {
            return -1;
        }
        // 这里已经可以确认存在环，不用判断空指针，
        long length = 1;
        slow = slow.next;
        fast = fast.next.next;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next.next;
            length ++;
        }
        return length;
    }

    private static void buildCycledLinkedList(ListNode head) {
        int count = 1;
        ListNode tail = head;
        while (tail.next != null) {
            count ++;
            tail = tail.next;
        }
        count++;
        ListNode listNodeK = $1_LastKNoke.lastK3(head, 3);
        tail.next = listNodeK;
    }

    @Test
    public void test1() {
        ListNode head = ListNode.buildLinkedList(10, (k) -> k+1);
        ListNode.printLinkedList(head);
        buildCycledLinkedList(head);
//        ListNode.printLinkedList(head);
        System.out.println(cycled1(head));
    }

    @Test
    public void test2() {
        ListNode head = ListNode.buildLinkedList(10, (k) -> k+1);
        ListNode.printLinkedList(head);
        buildCycledLinkedList(head);
        System.out.println(cycled2(head));

        head = ListNode.buildLinkedList(20, (k) -> k+1);
        System.out.println(cycled2(head));
    }
}
