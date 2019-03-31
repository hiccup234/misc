package top.hiccup.algorithm.linkedlist;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * 判断单链表中是否有环路
 *
 * @author wenhy
 * @date 2019/3/31
 */
public class L2$CycledLinkedListTest {

    /**
     * 1、穷举比较法：遍历的时候记录已经访问的节点，并与之前访问的做比较，时间复杂度O(n^2)
     * 注意这里要比较的是节点的内存地址是否相等，而不是元素是否相等
     *
     * @param head
     * @return
     */
    public static boolean cycled1(Node head) {
        if (head == null) {
            return false;
        }
        Set<Node> set = new HashSet<>();
        for (Node p = head; p != null; p = p.getNext()) {
            if (set.contains(p)) {
                System.out.println("cycled node:" + p);
                return true;
            }
            set.add(p);
        }
        return false;
    }



    private static void buildCycledLinkedList(Node head) {
        int count = 1;
        Node tail = head;
        while (tail.getNext() != null) {
            count ++;
            tail = tail.getNext();
        }
        count++;
        Node nodeK = L1$LastKNokeTest.lastK3(head, 3);
        tail.setNext(nodeK);
    }

    @Test
    public void test1() {
        Node head = Node.buildLinkedList(10);
        Node.printLinkedList(head);
        buildCycledLinkedList(head);
//        Node.printLinkedList(head);
        System.out.println(cycled1(head));
    }
}
