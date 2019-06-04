package top.hiccup.jdk.io.socket.netty;

import java.nio.charset.Charset;
import java.util.Date;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import top.hiccup.jdk.io.DateUtils;

/**
 * Netty线程模型：reactor
 * 线程模型（线程池，reactor，proactor）
 * <p>
 * 1、boss 对应BIO中接受新连接的线程，主要负责创建新连接
 * 2、worker 对应BIO中负责读取数据的线程(Handler)，主要用于读取数据以及业务逻辑处理
 *
 * @author wenhy
 * @date 2019/1/13
 */
public class NettyServer {

    private static final int PORT = 23401;

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
//                        nioSocketChannel.pipeline().addLast(new StringDecoder());
//                        nioSocketChannel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
//                            @Override
//                            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
//                                Attribute<Object> clientKey = ctx.channel().attr(AttributeKey.valueOf("clientKey"));
//                                System.out.println(clientKey);
//                                System.out.println(msg);
//
//                                // 服务端回写数据
//                                byte[] bytes = "你好，客户端".getBytes(Charset.forName("utf-8"));
//                                ByteBuf buffer = ctx.alloc().buffer();
//                                buffer.writeBytes(bytes);
//                                ctx.channel().writeAndFlush(buffer);
//                            }
//                            // netty 5才有
////                            @Override
////                            protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
////                                System.out.println(msg);
////                            }
//                        });
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                String msgStr = byteBuf.toString(Charset.forName("utf-8"));
                                System.out.println(DateUtils.now() + ": 服务端接受数据: " + msgStr);

                                byte[] bytes = ("你好，客户端: " + msgStr).getBytes(Charset.forName("utf-8"));
                                ByteBuf buffer = ctx.alloc().buffer();
                                buffer.writeBytes(bytes);
                                ctx.channel().writeAndFlush(buffer);
                            }
                        });
                    }
                })
                // 是否开启TCP底层心跳机制，true为开启
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 是否开启Nagle算法，true表示关闭，false表示开启
                // 如果要求高实时性，有数据需要发送时就立马发送就关闭，如果需要减少发送次数减少网络交互就开启。
                .childOption(ChannelOption.TCP_NODELAY, true)
                // 绑定端口，异步方法（基于事件驱动的体现？），可以添加监听器来监听结果
                .bind(PORT)
                .addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        if (future.isSuccess()) {
                            System.out.println("绑定端口成功: " + PORT);
                        } else {
                            System.out.println("绑定端口失败: " + PORT);
                        }
                    }
                });

    }
}
