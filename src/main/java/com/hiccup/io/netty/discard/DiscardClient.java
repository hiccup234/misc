package com.hiccup.io.netty.discard;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by wenhy on 2018/2/6.
 */
public class DiscardClient {

    public static void main(String[] arsg) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new DiscardClientHandler());
                    }
                });
        String ip = "127.0.0.1";
        int prot = 23401;
        ChannelFuture channelFuture = bootstrap.connect(ip, prot).sync();

        // 向缓冲区写数据
        channelFuture.channel().write(Unpooled.copiedBuffer("abcde".getBytes()));
        // 强制刷新缓存
        channelFuture.channel().flush();


        channelFuture.channel().closeFuture().sync();
        group.shutdownGracefully();
    }

}
