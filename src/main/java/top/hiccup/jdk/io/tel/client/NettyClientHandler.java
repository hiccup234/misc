package top.hiccup.jdk.io.tel.client;

import java.nio.charset.Charset;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Netty框架客户端处理器
 *
 * @author wenhy
 * @date 2019/1/24
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        byte[] bytes = "通道激活，连接上服务器!".getBytes(Charset.forName("utf-8"));
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(bytes);
        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object data) {
        ByteBuf byteBuf = (ByteBuf) data;
        System.out.println(byteBuf.toString(Charset.forName("utf-8")));
    }
}
