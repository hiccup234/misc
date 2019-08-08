package top.hiccup.jdk.concurrent.atomic;

/**
 * Striped64是一个抽象类，用来支持实现累加器，设计思路就是通过Cell数组来分散对单个点的竞争
 *
 * 主要就是 longAccumulate 和 doubleAccumulate 两个方法比较复杂
 *
 * @author wenhy
 * @date 2019/8/7
 */
public class Striped64Test {
}
