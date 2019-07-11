package top.hiccup.algorithm.stringmatch;

/**
 * 字符串匹配算法：主串与模式串，假设各自长度分别为n和m，则n >= pLen
 * <p>
 * 1、BF算法：Brute Force暴力匹配算法，也叫朴素匹配算法，从主串0~mLen-m依此比较看是否跟模式串匹配，时间复杂度为O(mLen*pLen)。
 * 因为实现简单，字符串长度一般不大且有短路优化，所以工程中实际使用较多。
 * <p>
 * 2、RK算法：Rabin-Karp算法，BF的改进版，借助哈希函数，给主串0~mLen-m的每个m长的字串求hash值，然后跟模式串做比较。
 * <p>
 * 3、BM算法：Boyer-Moore，比较复杂难懂，但性能是KMP的3-4倍，坏字符规则+好后缀规则。
 * <p>
 * 4、KMP算法：KMP算法是根据三位作者（D.E.Knuth，J.H.Morris 和 V.R.Pratt）的名字来命名的，算法的全称是Knuth Morris Pratt算法，简称为KMP算法。
 * KMP算法的核心思想跟BM算法非常相近。
 *
 * @author wenhy
 * @date 2019/6/14
 */
public class StringMatch {

    /**
     * Java中一个char是2两个字节，所以这里的长度为2的16次方
     */
    private static final int SIZE = 65536;

    /**
     * BM算法包含两部分，坏字符规则（bad character rule）和好后缀规则（good suffix shift）
     * major、pattern表示主串和模式串
     */
    public int bm(char[] major, char[] pattern) {
        // 记录模式串中每个字符最后出现的位置
        int[] bc = new int[SIZE];
        int mLen = major.length;
        int pLen = pattern.length;
        // 构建坏字符哈希表
        generateBC(pattern, bc);
        int[] suffix = new int[pLen];
        boolean[] prefix = new boolean[pLen];
        generateGS(pattern, pLen, suffix, prefix);
        int i = 0;
        while (i <= mLen - pLen) {
            // j 表示主串与模式串匹配的第一个字符
            int j;
            for (j = pLen - 1; j >= 0; --j) { // 模式串从后往前匹配
                if (major[i + j] != pattern[j]) {
                    break; // 坏字符对应模式串中的下标是 j
                }
            }
            if (j < 0) {
                return i; // 匹配成功，返回主串与模式串第一个匹配的字符的位置
            }
            int x = j - bc[(int) major[i + j]];
            int y = 0;
            if (j < pLen - 1) { // 如果有好后缀的话
                y = moveByGS(j, pLen, suffix, prefix);
            }
            i = i + Math.max(x, y);
        }
        return -1;
    }

    private void generateBC(char[] pattern, int[] bc) {
        for (int i = 0; i < bc.length; ++i) {
            bc[i] = -1;
        }
        for (int i = 0; i < pattern.length; ++i) {
            int index = (int) pattern[i];
            bc[index] = i;
        }
    }

    /**
     * j 表示坏字符对应的模式串中的字符下标 ; pLen 表示模式串长度
     */
    private int moveByGS(int j, int pLen, int[] suffix, boolean[] prefix) {
        // 好后缀的长度
        int k = pLen - 1 - j;
        if (suffix[k] != -1) {
            return j - suffix[k] + 1;
        }
        for (int r = j + 2; r <= pLen - 1; ++r) {
            if (prefix[pLen - r] == true) {
                return r;
            }
        }
        return pLen;
    }


    /**
     * pattern 表示模式串，pLen 表示长度，suffix，prefix 数组事先申请好了
     */
    private void generateGS(char[] pattern, int pLen, int[] suffix, boolean[] prefix) {
        // 初始化
        for (int i = 0; i < pLen; ++i) {
            suffix[i] = -1;
            prefix[i] = false;
        }
        for (int i = 0; i < pLen - 1; ++i) { // pattern[0, i]
            int j = i;
            int k = 0; // 公共后缀子串长度
            while (j >= 0 && pattern[j] == pattern[pLen - 1 - k]) { // 与 pattern[0, pLen-1] 求公共后缀子串
                --j;
                ++k;
                suffix[k] = j + 1; //j+1 表示公共后缀子串在 pattern[0, i] 中的起始下标
            }
            if (j == -1) {
                prefix[k] = true; // 如果公共后缀子串也是模式串的前缀子串
            }
        }
    }


    /**
     * major、pattern分别是主串和模式串，即在主串中查找模式串
     */
    public static int kmp(char[] major, char[] pattern) {
        int mLen = major.length;
        int pLen = pattern.length;

        int[] next = getNexts(pattern, pLen);
        int j = 0;
        for (int i = 0; i < mLen; ++i) {
            // 一直找到 a[i] 和 pattern[j]
            while (j > 0 && major[i] != pattern[j]) {
                j = next[j - 1] + 1;
            }
            if (major[i] == pattern[j]) {
                ++j;
            }
            // 找到匹配模式串的
            if (j == pLen) {
                return i - pLen + 1;
            }
        }
        return -1;
    }

    /**
     * 失效函数：pattern 表示模式串，pLen 表示模式串的长度
     */
    private static int[] getNexts(char[] pattern, int pLen) {
        int[] next = new int[pLen];
        next[0] = -1;
        int k = -1;
        for (int i = 1; i < pLen; ++i) {
            while (k != -1 && pattern[k + 1] != pattern[i]) {
                k = next[k];
            }
            if (pattern[k + 1] == pattern[i]) {
                ++k;
            }
            next[i] = k;
        }
        return next;
    }
}
