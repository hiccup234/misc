package top.hiccup.algorithm;

import java.util.Random;

/**
 * 跳表：可以用来支持对链表的二分查找（普通二分查找仅适用于数组），Redis中的有序集合（ZSet）就是用跳表+哈希表实现的
 * 
 * 支持快速的插入、删除、查找操作，甚至可以替代红黑树
 * 
 * 【跳表相比红黑树】
 * 优势：在于按区间查找时效率更高，更简单易懂，代码实现易读
 *
 * 劣势：跳表的索引层数越多则浪费的空间也越多（一般需要排序的对象都比较大，所以索引的空间占比相对来说还好）
 *      某个节点的索引层高需要设为随机值，以及插入和删除都要维护索引，否则复杂度容易退化为O(n)
 *
 * Q：跳表是不是更像B+Tree呢？
 * A：实际上，跳表确实跟B+Tree非常相像，不过B+Tree发明的时间更早且树的思想是从上至下，而跳表则是从下往上构建索引的
 *
 * @author wenhy
 * @date 2019/5/20
 */
public class SkipList {

    /**
     * 最大索引层级
     */
    private static final int MAX_LEVEL = 16;
    /**
     * 当前索引层数
     */
    private int levelCount = 1;
    /**
     * 带头链表
     */
    private ListNode head = new ListNode();

    private Random r = new Random();

    public ListNode find(int value) {
        ListNode p = head;
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
        }

        if (p.forwards[0] != null && p.forwards[0].data == value) {
            return p.forwards[0];
        } else {
            return null;
        }
    }

    public void insert(int value) {
        int level = randomLevel();
        ListNode newNode = new ListNode();
        newNode.data = value;
        newNode.maxLevel = level;
        ListNode update[] = new ListNode[level];
        for (int i = 0; i < level; ++i) {
            update[i] = head;
        }

        // record every level largest value which smaller than insert value in update[]
        ListNode p = head;
        for (int i = level - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
            update[i] = p;
        }

        // in search path ListNode next ListNode become new ListNode forwords(next)
        for (int i = 0; i < level; ++i) {
            newNode.forwards[i] = update[i].forwards[i];
            update[i].forwards[i] = newNode;
        }

        // update ListNode hight
        if (levelCount < level) {
            levelCount = level;
        }
    }

    public void delete(int value) {
        ListNode[] update = new ListNode[levelCount];
        ListNode p = head;
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
            update[i] = p;
        }

        if (p.forwards[0] != null && p.forwards[0].data == value) {
            for (int i = levelCount - 1; i >= 0; --i) {
                if (update[i].forwards[i] != null && update[i].forwards[i].data == value) {
                    update[i].forwards[i] = update[i].forwards[i].forwards[i];
                }
            }
        }
    }

    /**
     * 随机 level 次，如果是奇数层数+1，防止伪随机
     */
    private int randomLevel() {
        int level = 1;
        for (int i = 1; i < MAX_LEVEL; ++i) {
            if (r.nextInt() % 2 == 1) {
                level++;
            }
        }
        return level;
    }

    public void printAll() {
        ListNode p = head;
        while (p.forwards[0] != null) {
            System.out.print(p.forwards[0] + " ");
            p = p.forwards[0];
        }
        System.out.println();
    }

    class ListNode {
        /**
         * 跳表中存储的是正整数，并且存储的是不重复的
         */
        private int data = -1;
        /**
         * 默认第0层，即存储数据本身的链表层
         */
        private int maxLevel = 0;
        /**
         * 数组存储纵向的索引，避免在Node节点中增加向下和向后的“指针”，加快遍历速度以及维护索引的时候更方便
         */
        private ListNode forwards[] = new ListNode[MAX_LEVEL];

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("{ data: ");
            builder.append(data);
            builder.append("; levels: ");
            builder.append(maxLevel);
            builder.append(" }");
            return builder.toString();
        }
    }
}
