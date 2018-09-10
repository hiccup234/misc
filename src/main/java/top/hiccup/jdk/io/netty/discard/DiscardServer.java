package com.hiccup.jdk.io.netty.discard;

import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by wenhy on 2018/2/6.
 */
public class DiscardServer {

    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        // NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器，
        // Netty提供了许多不同的EventLoopGroup的实现用来处理不同传输协议。
        // 接收连接并把连接信息注册到‘worker’
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 处理处理实际业务
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 启动NIO服务的辅助启动类
            ServerBootstrap b = new ServerBootstrap();
            // 链式编程，方法返回this
            // 把boss和worker两个线程组绑定起来
            b.group(bossGroup, workerGroup)
                    // 使用NioServerSocketChannel通道
                    .channel(NioServerSocketChannel.class)
                    // 绑定具体的事件处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // 注册丢弃协议服务器处理器
                            ch.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync();
            // 绑定两个端口
            ChannelFuture f2 = b.bind(23402).sync();

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
            f2.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 23401;
        }
        new DiscardServer(port).run();
    }
}
