package top.hiccup.algorithm.link;

import java.util.Random;

import org.junit.Test;

/**
 * 求单链表倒数第k个节点
 *
 * @author wenhy
 * @date 2019/3/31
 */
public class L1$LastKNokeTest {

    /**
     * 1、最容易想到的方法：先遍历出链表总长度，再遍历k个节点即可
     *
     * @param head 链表头节点
     * @param k
     * @return
     */
    public static Node lastK1(Node head, int k) {
        if (head == null || k <= 0) {
            return null;
        }
        // 1、先计算出链表长度
        int count = 0;
        for (Node tmp = head; tmp != null; tmp = tmp.next) {
            count++;
        }
        if (count < k) {
            return null;
        }
        Node tmp = head;
        // 这儿不能 i < count - k + 1，因为后面还要赋值tmp = tmp.next;类似+1的动作
        for (int i = 0; i < count - k; i++) {
            tmp = tmp.next;
        }
        return tmp;
    }

    /**
     * 2、递归遍历法：方法1的变种：递归遍历至末尾，从末尾开始返回，每返回一次num--，知道num为0则找到目标节点
     *
     * @param head
     * @param k
     * @return
     */
    private static int num = 0;
    public static Node lastK2(Node head, int k) {
        num = k;
        if (head == null || k <= 0) {
            return null;
        }
        Node node = lastK2(head.next, k);
        // 如果不为空则证明找到了倒数k的节点，并直接一路返回
        if (node != null) {
            return node;
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
    public static Node lastK3(Node head, int k) {
        if (head == null || k <= 0) {
            return null;
        }
        Node n2 = null;
        int count = 0;
        for (Node n1 = head;n1 != null; n1 = n1.next) {
            count++;
            if (count == k) {
                n2 = head;
            } else if (count > k) {
                n2 = n2.next;
            }
        }
        return n2;
    }

    @Test
    public void test1() {
        Random random = new Random(47);
        for (int i = 0; i < 100; i++) {
//            Node head = buildLinkList(random.nextInt(500));
            Node head = Node.buildLinkedList(100, (k) -> k+1);
            Node.printLinkedList(head);
            System.out.println(lastK1(head, 1));
        }
    }

    @Test
    public void test2() {
        Node head = Node.buildLinkedList(100, (k) -> k+1);
        Node.printLinkedList(head);
        System.out.println(lastK2(head, 100));
    }

    @Test
    public void test3() {
        Node head = Node.buildLinkedList(100, (k) -> k+1);
        Node.printLinkedList(head);
        System.out.println(lastK3(head, 1));
    }
}
