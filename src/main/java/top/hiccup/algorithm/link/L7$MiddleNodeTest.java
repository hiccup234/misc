package top.hiccup.algorithm.link;

import org.junit.Test;

/**
 * 如何找到链表的中间节点？
 *
 * 1、自然的想法：先遍历链表，找出链表长度n，再遍历到n/2。
 *
 * 2、只遍历一次：快慢指针，慢指针步长为1，快指针步长为2，当快指针到达末尾，慢指针就到链表中间节点了。
 *
 * 拓展：如何找到链表的1/3长节点，答：慢指针步长1，快指针步长3
 *
 * @author wenhy
 * @date 2019/4/26
 */
public class L7$MiddleNodeTest {

    public static Node middleNode(Node head) {
        if (head == null) {
            return null;
        }
        // n1慢指针，每次步长为1，n2快指针，每次步长为2
        Node n1 = head, n2 = head;
        // 这里要注意判断n2.next是否为空，否则会造成NPE
        while (n1 != null && n2 != null && n2.next != null && n2.next.next != null) {
            n1 = n1.next;
            n2 = n2.next.next;
        }
        return n1;
    }

    @Test
    public void test() {
        Node head = Node.buildLinkedList(10, (k) -> k+1);
        Node.printLinkedList(head);
        System.out.println(middleNode(head));

        head = Node.buildLinkedList(20, (k) -> k+1);
        Node.printLinkedList(head);
        System.out.println(middleNode(head));
    }
}
