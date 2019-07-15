package top.hiccup.algorithm.string;

import java.util.LinkedList;
import java.util.Queue;

/**
 * AC自动机算法（Aho-Corasick），建立在Trie树之上，可以用来做敏感词过滤
 *
 * 1、将多个模式串构建成Trie树
 * 2、在Trie树上构建失败指针（相当于KMP中的失效函数next数组）
 *
 * @author wenhy
 * @date 2019/7/15
 */
public class AC {

    /**
     * 根节点，存储无意义字符
     */
    private AcNode root = new AcNode('/');

    public void buildFailurePointer() {
        Queue<AcNode> queue = new LinkedList<>();
        root.fail = null;
        queue.add(root);
        while (!queue.isEmpty()) {
            AcNode p = queue.remove();
            for (int i = 0; i < 26; ++i) {
                AcNode pc = p.children[i];
                if (pc == null) continue;
                if (p == root) {
                    pc.fail = root;
                } else {
                    AcNode q = p.fail;
                    while (q != null) {
                        AcNode qc = q.children[pc.data - 'a'];
                        if (qc != null) {
                            pc.fail = qc;
                            break;
                        }
                        q = q.fail;
                    }
                    if (q == null) {
                        pc.fail = root;
                    }
                }
                queue.add(pc);
            }
        }
    }

    public void match(char[] text) { // text 是主串
        int n = text.length;
        AcNode p = root;
        for (int i = 0; i < n; ++i) {
            int idx = text[i] - 'a';
            while (p.children[idx] == null && p != root) {
                // 失败指针发挥作用的地方
                p = p.fail;
            }
            p = p.children[idx];
            // 如果没有匹配的，从 root 开始重新匹配
            if (p == null) {
                p = root;
            }
            AcNode tmp = p;
            // 打印出可以匹配的模式串
            while (tmp != root) {
                if (tmp.isEndingChar == true) {
                    int pos = i-tmp.length+1;
                    System.out.println(" 匹配起始下标 " + pos + "; 长度 " + tmp.length);
                }
                tmp = tmp.fail;
            }
        }
    }

}


class AcNode {
    public char data;
    // 字符集只包含 a~z 这 26 个字符
    public AcNode[] children = new AcNode[26];
    // 结尾字符为 true
    public boolean isEndingChar = false;
    // 当 isEndingChar=true 时，记录模式串长度
    public int length = -1;
    // 失败指针
    public AcNode fail;
    public AcNode(char data) {
        this.data = data;
    }
}

