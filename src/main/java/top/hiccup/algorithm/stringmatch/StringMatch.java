package top.hiccup.algorithm.stringmatch;

/**
 * 字符串匹配算法：主串与模式串，假设各自长度分别为n和m，则n >= m
 *
 * 1、BF算法：Brute Force暴力匹配算法，也叫朴素匹配算法，从主串0~n-m依此比较看是否跟模式串匹配，时间复杂度为O(n*m)。
 * 因为实现简单，字符串长度一般不大且有短路优化，所以工程中实际使用较多。
 *
 * 2、RK算法：Rabin-Karp算法，BF的改进版，借助哈希函数，给主串0~n-m的每个m长的字串求hash值，然后跟模式串做比较
 *
 *
 *
 * @author wenhy
 * @date 2019/6/14
 */
public class StringMatch {
}
