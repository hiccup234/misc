package top.hiccup.algorithm.problem.geek;

/**
 * 实现单链表、循环链表、双向链表，支持增删操作
 *
 * @author wenhy
 * @date 2019/5/27
 */
public class $4_LinkedList {

    /**
     * 单链表
     */
    public class Single {
        class Node {
            int val;
            Node next;
        }
        /**
         * 哨兵节点，模拟头节点
         */
        private Node dummy;
        private Node tail;
        public Single() {
            dummy = new Node();
            tail = dummy;
        }

        public void add(Node node) {
            tail.next = node;
            tail = node;
        }

        public void delete(Node node) {
            Node pre = dummy;
            Node pCur = dummy.next;
            while (pCur != null) {
                if (pCur == node) {
                    if (pCur == tail) {
                        tail = pre;
                    }
                    pre.next = pCur.next;
                }
                pre = pCur;
                pCur = pCur.next;
            }
        }
    }

    /**
     * 循环链表
     */
    public class Cycle {
        class Node {
            int val;
            Node next;
        }
        private Node dummy;
        private Node tail;

        public Cycle() {
            dummy = new Node();
            dummy.next = dummy;
            tail = dummy;
        }

        public void add(Node node) {
            tail.next = node;
            tail = node;
            tail.next = dummy.next;
        }

        public void delete(Node node) {
            Node pre = dummy;
            Node pCur = dummy.next;
            while (pCur != null) {
                if (pCur == node) {
                    if (pCur == tail) {
                        tail = pre;
                        tail.next = dummy.next;
                    }
                    pre.next = pCur.next;
                }
                pre = pCur;
                pCur = pCur.next;
            }
        }
    }

    /**
     * 双向链表
     */
    public class DoubleDirection {
        class Node {
            int val;
            Node pre;
            Node next;
        }
        private Node dummy;
        private Node tail;

        public DoubleDirection() {
            dummy = new Node();
            tail = dummy;
        }

        public void add(Node node) {
            node.pre = tail;
            tail.next = node;
            tail = node;
        }

        public void delete(Node node) {
            node.pre.next = node.next;
        }
    }
}
