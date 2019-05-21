package top.hiccup.algorithm.bintree;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;

/**
 * 二叉树树节点
 * =====================================================================================================================
 * 注意：二叉树不是树的特殊结构，两者定义不同：二叉树可以为空，树不能为空
 *
 * 树的定义：树是n个结点的有限集。在任意一棵非空树中：1.有且仅有一个特定的称为根的结点
 *       2.当n＞1时，其余结点可分为m个互不相交的有限集T1,T2,...,Tm，其中每一个集合本身是一棵树，并且称为根的子树
 *
 * 二叉树定义：二叉树是另一种树型结构，它的特点是每个结点至多只有两棵子树，并且二叉树的子树有左右之分，其次序不能任意颠倒
 *
 * 对树来说，结点的子树是不区分左右顺序的，因此度为2的树只能说明树中每个结点的子结点个数不超过2。
 * 而二叉树虽然也满足每个结点的子结点个数不超过2，当它的左右子树是严格区分的，不能随意交换左子树和右子树的位置，这就是二叉树和度为2的树最主要的区别。
 *
 * =====================================================================================================================
 * 满二叉树：一个二叉树，如果每一个层的结点数都达到最大值，则这个二叉树就是满二叉树。
 *         如果一个二叉树的层数为K，且结点总数是(2^k)-1 ，则它就是满二叉树。
 *
 * 完全二叉树：叶子节点都在最底下两层，最后一层的叶子节点都靠左排列，并且除了最后一层，其他层的节点个数都要达到最大。
 *
 * =====================================================================================================================
 * 前序遍历：从根节点开始，先打印当前节点，再打印左子树，最后打印右子树
 * 中序遍历：从根节点开始，先打印左子树，然后打印当前节点，然后打印右子树
 * 后续遍历：从根节点开始，先打印左子树，然后打印右子树，最后打印当前节点
 *
 * 按层遍历：广度优先搜索算法，可以借助队列，先去根节点入队，然后判断队列不为空则取出节点，然后把其左右子节点入队，直到队列为空则遍历完成。
 *
 * 二叉树节点遍历的时间复杂度为 O(n)
 *
 * @author wenhy
 * @date 2019/3/31
 */
@Data
public class Node {
    int val;
    Node left;
    Node right;

    public Node() {}

    public Node(int val) {
        this.val = val;
    }

    public Node(int val, Node left, Node right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public Node(Node node) {
        this.val = node.val;
        this.left = node.left;
        this.right = node.right;
    }


    /**
     * 二叉树的相关遍历
     * @param root
     */
    public static void preOrderPrint(Node root) {
        if (root == null) {
            return ;
        }
        System.out.println(root);
        preOrderPrint(root.left);
        preOrderPrint(root.right);
    }
    public static void inOrderPrint(Node root) {
        if (root == null) {
            return ;
        }
        inOrderPrint(root.left);
        System.out.println(root);
        inOrderPrint(root.right);
    }
    public static void postOrderPrint(Node root) {
        if (root == null) {
            return ;
        }
        postOrderPrint(root.left);
        postOrderPrint(root.right);
        System.out.println(root);
    }

    public List<List<Integer>> levelOrder(Node root) {
        if (root == null) {
            return null;
        }
        LinkedList<Node> queue = new LinkedList<>();
        queue.addLast(root);
        List<List<Integer>> result = new LinkedList<>();
        while (queue.size() > 0) {
            List<Integer> levelList = new LinkedList<>();
            LinkedList<Node> newQueue = new LinkedList<>();
            while (queue.size() > 0) {
                Node node = queue.removeFirst();
                levelList.add(node.val);
                if (node.left != null) {
                    newQueue.addLast(node.left);
                }
                if (node.right != null) {
                    newQueue.addLast(node.right);
                }
            }
            result.add(levelList);
            queue = newQueue;
        }
        return result;
    }
}