package top.hiccup.algorithm.tree.bintree;

import org.junit.Test;

/**
 * 跟据前序遍历和中序遍历（不包含重复数字）重建二叉树
 *
 * 如：前序 1,2,4,7,3,5,6,8
 *    中序 4,7,2,1,5,3,8,6
 *
 * @see 《剑指Offer》 主要考察二叉树遍历时递归的思想
 *
 * @author wenhy
 * @date 2019/4/22
 */
public class ReBuildBinTree {

    public static TreeNode reBuild(int[] pre, int[] in, int size) {
        if (pre == null || pre.length == 0 ||
                in == null || in.length == 0 || size <= 0) {
            return null;
        }
        return buildSubTree(pre, 0, size, in, 0, size);
    }

    public static TreeNode buildSubTree(int[] pre, int pLeft, int pRight, int[] in, int iLeft, int iRight) {
        // 前序遍历的第一个节点是根节点
        int rootVal = pre[pLeft];
        TreeNode root = new TreeNode(rootVal);
        if (pLeft == pRight) {
            if (iLeft == iRight && pre[pLeft] == in[iLeft]) {
                return root;
            } else {
                throw new RuntimeException("invalid val");
            }
        }
        // 否则在中序遍历序列中找到根节点
        int i = 0;
        for (i = iLeft; i <= iRight; i++) {
            if (in[i] == rootVal) {
                break;
            }
        }
        if (i == iRight && in[i] != rootVal) {
            throw new RuntimeException("invalid val");
        }
        int leftLength = i - iLeft;

        if (leftLength > 0) {
            // 构建左子树
            root.setLeft(buildSubTree(pre, pLeft+1, pLeft+leftLength, in, iLeft, i-1));
        }
        if (leftLength < pRight - pLeft) {
            // 构建右子树
            root.setRight(buildSubTree(pre, pLeft+leftLength+1, pRight, in, i+1, iRight));
        }
        return root;
    }

    @Test
    public void test() {
        int[] pre = new int[] {1,2,4,7,3,5,6,8};
        int[] in = new int[] {4,7,2,1,5,3,8,6};

        reBuild(pre, in, pre.length-1);
    }
}
