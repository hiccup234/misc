package top.hiccup.jdk.io.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Netty简单客户端
 *
 * @author wenhy
 * @date 2018/2/6
 */
public class NettyClient {

    private static String ip = "127.0.0.1";
    private static int port = 23401;

    public static void main(String[] arsg) throws InterruptedException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                // TODO 这里的handler与服务端的childHandler有什么区别?
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyClientHandler());
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
        // 向缓冲区写数据
        channelFuture.channel().write(Unpooled.copiedBuffer("服务器你好..".getBytes()));
        // 强制刷新缓冲区
        channelFuture.channel().flush();

        channelFuture.channel().closeFuture().sync();
        workerGroup.shutdownGracefully();
    }
}
