package top.hiccup.jdk.io.socket.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Netty简单客户端
 *
 * @author wenhy
 * @date 2018/2/6
 */
public class NettyClient {

    private static String ip = "127.0.0.1";
    private static int port = 23401;
    /**
     * 最大重连次数
     */
    private static final int MAX_RETRY = 10;

    public static void main(String[] arsg) throws InterruptedException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                // TODO 这里的handler与服务端的childHandler有什么区别?
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                try {
                                    ByteBuf buf = (ByteBuf) msg;
                                    byte[] data = new byte[buf.readableBytes()];
                                    buf.readBytes(data);
                                    String request = new String(data, "utf-8");
                                    System.out.println("【Client receive】" + request);
                                } finally {
                                    ReferenceCountUtil.release(msg);
                                }
                            }
                        });
                    }
                })
                // 给NioSocketChannel绑定自定义属性，可以通过channel.attr()取出这个属性
                .attr(AttributeKey.newInstance("clientName"), "nettyClient")
                // 表示连接的超时时间，超过这个时间还是建立不上的话则代表连接失败
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                // 表示是否开启 TCP 底层心跳机制，true 为开启
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true);

        // 异步建立连接
        connect(bootstrap, ip, port, MAX_RETRY);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // 同步建立连接
        ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();

        // 向缓冲区写数据
        channelFuture.channel().write(Unpooled.copiedBuffer("服务器你好..".getBytes()));
        // 强制刷新缓冲区
        channelFuture.channel().flush();

        // sync阻塞线程
        channelFuture.channel().closeFuture().sync();
        workerGroup.shutdownGracefully();
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(
                        () -> connect(bootstrap, host, port, retry - 1),
                        delay, TimeUnit.SECONDS);
            }
        });
    }
}
