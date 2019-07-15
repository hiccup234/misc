package top.hiccup.algorithm.string;

/**
 * 字典树：专门用来处理字符串匹配的数据结构（搜索引擎的关键词提示），时间复杂度为O(k)，k为要匹配的字符串长度。
 *
 * Trie是多叉树，子节点可采用数组来存储（前缀重复不多的情况下，会非常浪费内存），Apache Commons 有关于Trie的实现。
 *
 * 哈希表、红黑树更适合精确的匹配查找，而Trie更适合前缀匹配查找。
 *
 * @author wenhy
 * @date 2019/7/15
 */
public class TrieTree {

    /**
     * 根节点，存储无意义字符
     */
    private TrieNode root = new TrieNode('/');

    /**
     * 往 Trie 树中插入一个字符串
     *
     * @param text
     */
    public void insert(char[] text) {
        TrieNode p = root;
        for (int i = 0; i < text.length; ++i) {
            int index = text[i] - 'a';
            if (p.children[index] == null) {
                TrieNode newNode = new TrieNode(text[i]);
                p.children[index] = newNode;
            }
            p = p.children[index];
        }
        p.isEndingChar = true;
    }

    /**
     * 在 Trie 树中查找一个字符串
     *
     * @param pattern
     * @return
     */
    public boolean find(char[] pattern) {
        TrieNode p = root;
        for (int i = 0; i < pattern.length; ++i) {
            int index = pattern[i] - 'a';
            if (p.children[index] == null) {
                // 不存在 pattern
                return false;
            }
            p = p.children[index];
        }
        if (p.isEndingChar == false) {
            // 不能完全匹配，只是前缀
            return false;
        } else {
            // 找到 pattern
            return true;
        }
    }

    public class TrieNode {
        public char data;
        /**
         * 这里默认只存在a~b这26字符，如果想要优化空间则可以换成有序数组、跳表、哈希表、红黑树等数据结构
         * 同时也可以考虑缩点优化
         */
        public TrieNode[] children = new TrieNode[26];
        public boolean isEndingChar = false;

        public TrieNode(char data) {
            this.data = data;
        }
    }
}