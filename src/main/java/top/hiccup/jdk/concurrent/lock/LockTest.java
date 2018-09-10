package top.hiccup.jdk.concurrent.lock;

/**
 * Java锁分类和优化策略：
 *
 *
 *
 * 轻量级锁
 *
 * 偏向锁
 *
 * 自旋锁
 *
 * 轻量级锁、偏向锁、自旋锁都不是Java语言层面的锁优化方法，是内置于JVM中的获取锁的优化方法和获取锁的步骤。
 * 使用顺序为
 * 偏向锁>轻量级锁>自旋锁>重量级
 *
 * 减小锁粒度
 *
 * 锁分离(读写分离)
 *
 * 锁粗化
 *
 * 锁消除
 * 逃逸分析等
 *
 * 无锁
 * 也就是乐观锁，如cas操作，java.util.concurrent.atomic包使用无锁实现
 *
 * @author wenhy
 * @date 2018/9/7
 */
public class LockTest {
}
