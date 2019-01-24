package top.hiccup.jdk.io.tel.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import top.hiccup.jdk.io.tel.server.NettyServerHandler;

/**
 * Netty线程模型：reactor
 *
 * @author wenhy
 * @date 2019/1/13
 */
public class NettyServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    private static final int PORT = 8234;

    public static void main(String[] args) {
        // 服务端启动引导类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 监听端口，accept 新连接的线程组
        NioEventLoopGroup boss = new NioEventLoopGroup();
        // 处理每一条连接的数据读写的线程组
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap
                // 配置引导类线程模型
                .group(boss, worker)
                // 指定服务端的IO模型，有NIO和BIO（OioServerSocketChannel.class）
                .channel(NioServerSocketChannel.class)
                // 配置服务端channel的自定义属性
                .attr(AttributeKey.newInstance("serverName"), "nettyServer")
                // 表示系统用于临时存放已完成三次握手的请求的队列的最大长度，如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 指定在服务端启动过程中的一些逻辑，通常情况下用不着
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel nioServerSocketChannel) {
                        Attribute<Object> serverName = nioServerSocketChannel.attr(AttributeKey.valueOf("serverName"));
                        System.out.println("服务端【" + (String) serverName.get() + "】启动中..");
                    }
                })
                // 配置每条连接的自定义属性
                .childAttr(AttributeKey.newInstance("clientKey"), "clientValue")
                // 配置每条连接的处理器
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) {
                        nioSocketChannel.pipeline().addLast(new NettyServerHandler());
                    }
                })
                // 是否开启TCP底层心跳机制，true为开启
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 是否开启Nagle算法，true表示关闭，false表示开启
                // 如果要求高实时性，有数据需要发送时就立马发送就关闭，如果需要减少发送次数减少网络交互就开启。
                .childOption(ChannelOption.TCP_NODELAY, true)
                // 绑定端口，异步方法（基于事件驱动的体现？），可以添加监听器来监听结果
                .bind(PORT).addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        if (future.isSuccess()) {
                            LOGGER.warn("绑定端口成功：" + PORT);
                            System.out.println("绑定端口成功：" + PORT);
                        } else {
                            String errorMsg = "绑定端口失败：" + PORT;
                            LOGGER.error(errorMsg);
                            throw new RuntimeException(errorMsg);
                        }
                    }
                });
    }
}
