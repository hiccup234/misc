package top.hiccup.jdk.concurrent.aqs;

/**
 * JUC的基石：AbstractQueuedSynchronizer
 *
 * AQS中的Node.waitStatus有5种状态：
 * CANCELLED    1   因为超时或中断设置为此状态，标志节点已废除不可用
 * SIGNAL       -1  处于此状态的节点释放资源时会唤醒后面的节点
 * CONDITION    -2  处于条件队列里，等待条件成立(signal signalall) 条件成立后会置入获取资源的队列里
 * PROPAGATE    -3  共享模式下使用，头节点获取资源时将后面节点设置为此状态，如果头节点获取资源后还有足够的资源，则后面节点会尝试获取，这个状态主要是为了共享状态下队列里足够多的节点同时获取资源
 *              0   初始状态
 *
 * 有一个静态内部类Node节点，通过双向链表构成队列，入队出队都是通过CAS替换的，所以多线程并发下不用锁来同步
 *
 * 详见AQS类的详细注释
 *
 * @author wenhy
 * @date 2018/8/8
 */
public class AQSTest {

}
