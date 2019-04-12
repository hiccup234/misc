package top.hiccup.jdk.io.netty;

/**
 * Netty采用了Reactor线程模型（Spring 5.0也是采用了这种模型，见官网）
 *
 * 1、单线程模式(1:1)：只有一个线程，既处理连接也处理IO
 *
 * 2、主从Reactor模式(1:n)：将监听连接和处理IO分开来，监听线程和IO处理线程，也即：BossGroup和workerGroup
 *
 * 3、多Reactor线程模式(n:n)：
 *
 * @author wenhy
 * @date 2019/4/12
 */
public class NettyThreadMode {
}
