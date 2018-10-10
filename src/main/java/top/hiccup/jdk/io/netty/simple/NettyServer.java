package top.hiccup.jdk.io.netty.simple;

import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Netty简单服务端
 *
 * @author wenhy
 * @date 2018/2/6
 */
public class NettyServer {

    public void run(int port) throws Exception {
        // NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器，Netty提供了许多不同的EventLoopGroup的实现用来处理不同传输协议
        // 监听端口，accept新连接的线程组，接收连接并把连接信息注册到‘worker’
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 处理每条连接的数据读写的线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // 启动NIO服务的辅助启动类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            // 链式编程，方法返回this
            serverBootstrap
                    // 把boss和worker两个线程组绑定起来
                    .group(bossGroup, workerGroup)
                    // 使用NioServerSocketChannel通道
                    .channel(NioServerSocketChannel.class)
                    // 绑定具体的事件处理器
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        public void initChannel(NioSocketChannel nioSocketChannel) {
                            nioSocketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            serverBootstrap.bind(port);
//            // Bind and start to accept incoming connections.
//            ChannelFuture f = serverBootstrap.bind(port).sync();
//            // 绑定两个端口
//            ChannelFuture f2 = serverBootstrap.bind(23402).sync();
//
//            // Wait until the server socket is closed.
//            // In this example, this does not happen, but you can do that to c
//            // shut down your server.
//            f.channel().closeFuture().sync();
//            f2.channel().closeFuture().sync();
        } finally {
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new NettyServer().run(23401);
        Thread.sleep(500000);
    }
}
