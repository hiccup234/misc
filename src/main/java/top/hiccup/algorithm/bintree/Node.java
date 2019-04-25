package top.hiccup.algorithm.bintree;

import lombok.Data;

/**
 * 二叉树树节点
 *
 * 注意：二叉树不是树的特殊结构
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
}