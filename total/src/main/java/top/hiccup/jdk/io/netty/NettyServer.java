package top.hiccup.jdk.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Netty简单服务端
 *
 * @author wenhy
 * @date 2018/2/6
 */
public class NettyServer {

    private static int port = 23401;

    public static void main(String[] args) throws Exception {
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
                    // 指定在服务端启动过程中的逻辑
                    .handler(new ChannelInitializer<NioServerSocketChannel>() {
                        @Override
                        protected void initChannel(NioServerSocketChannel ch) {
                            System.out.println("【服务端启动中】");
                        }
                    })
                    // 指定处理新连接数据的读写处理逻辑
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        public void initChannel(NioSocketChannel nioSocketChannel) {
                            // 责任链模式：pipeline()返回的是一个链表
                            nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    ByteBuf buf = (ByteBuf) msg;
                                    byte[] data = new byte[buf.readableBytes()];
                                    buf.readBytes(data);
                                    String request = new String(data, "utf-8");
                                    System.out.println("NettyServer: " + request);
                                    // 写给客户端
                                    String response = "这是反馈信息";
//                                    ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
                                    Packet packet = new Packet();
                                    packet.body = response.getBytes();
//                                    ctx.writeAndFlush(packet);
                                    ctx.channel().writeAndFlush(packet);
                                    System.out.println("body length: " + packet.body.length);
                                }
                            });
                        }
                    })
                    // 给服务端的channel也就是NioServerSocketChannel指定自定义属性，然后通过channel.attr()取出这个属性
                    .attr(AttributeKey.newInstance("serverName"), "nettyServer")
                    // 给每条连接指定自定义属性，然后通过channel.attr()取出这个属性
                    .childAttr(AttributeKey.newInstance("clientKey"), "clientValue")
                    // 给服务端channel设置属性
                    .option(ChannelOption.SO_BACKLOG, 1024) // 表示系统用于临时存放已完成三次握手的请求的队列的最大长度，如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                    // 给每条连接设置一些TCP底层相关的属性
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 是否开启TCP底层心跳机制，true为开启
                    .childOption(ChannelOption.TCP_NODELAY, true); // 是否开启Nagle算法，true表示关闭，false表示开启

            bind(serverBootstrap, port);

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

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) {
                if (future.isSuccess()) {
                    System.out.println("绑定端口[" + port + "]成功!");
                } else {
                    System.err.println("绑定端口[" + port + "]失败!");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}

class Packet {
    /**
     * transient修饰问你验证
     * （transient修饰不会导致内存中的对象被Netty修改，需要注意MessageToByteEncoder里是否重写了encode方法）
     */
    public transient byte[] body;
}
