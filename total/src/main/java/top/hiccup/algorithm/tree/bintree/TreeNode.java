package top.hiccup.algorithm.tree.bintree;

import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树树节点
 *
 * @author wenhy
 * @date 2019/3/31
 */
public class TreeNode {

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public TreeNode(TreeNode treeNode) {
        this.val = treeNode.val;
        this.left = treeNode.left;
        this.right = treeNode.right;
    }

    int val;
    TreeNode left;
    TreeNode right;

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    /**
     * 二叉树的前中后序遍历
     */
    public static void preOrderPrint(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.println(root);
        preOrderPrint(root.left);
        preOrderPrint(root.right);
    }

    public static void inOrderPrint(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrderPrint(root.left);
        System.out.println(root);
        inOrderPrint(root.right);
    }

    public static void postOrderPrint(TreeNode root) {
        if (root == null) {
            return;
        }
        postOrderPrint(root.left);
        postOrderPrint(root.right);
        System.out.println(root);
    }

    /**
     * 层序遍历，借助队列比较容易实现
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return null;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.addLast(root);
        List<List<Integer>> result = new LinkedList<>();
        while (queue.size() > 0) {
            List<Integer> levelList = new LinkedList<>();
            LinkedList<TreeNode> newQueue = new LinkedList<>();
            while (queue.size() > 0) {
                TreeNode treeNode = queue.removeFirst();
                levelList.add(treeNode.val);
                if (treeNode.left != null) {
                    newQueue.addLast(treeNode.left);
                }
                if (treeNode.right != null) {
                    newQueue.addLast(treeNode.right);
                }
            }
            result.add(levelList);
            queue = newQueue;
        }
        return result;
    }
}