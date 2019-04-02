package top.hiccup.algorithm.linkedlist;

import lombok.Data;

/**
 * 链表节点
 *
 * @author wenhy
 * @date 2019/3/31
 */
@Data
public class Node {
    private int val;
    private Node next;

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

    public static Node buildLinkedList(int n, Command command) {
        Node head = null;
        Node tail = null;
        for (int i = 0; i < n; i++) {
            Node node = new Node();
            node.setVal(command.getVal(i));
            if (i == 0) {
                head = node;
                tail = node;
                continue;
            }
            tail.setNext(node);
            tail = node;
        }
        return head;
    }

    public static void printLinkedList(Node head) {
        if (head == null) {
            System.out.println("none list");
        }
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Node p = head; p != null; p = p.getNext()) {
            sb.append(++count + ":" + p);
            if (p.getNext() != null) {
                sb.append(" -> ");
            }
        }
        System.out.println(sb.toString());
    }
}

interface Command {
    int getVal(int i);
}