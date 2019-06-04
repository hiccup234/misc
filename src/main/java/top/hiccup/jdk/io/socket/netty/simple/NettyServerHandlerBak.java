package top.hiccup.jdk.io.socket.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

import java.io.UnsupportedEncodingException;

/**
 * Netty简单服务端处理器
 *
 * @author wenhy
 * @date 2018/2/6
 */
public class NettyServerHandlerBak extends ChannelHandlerAdapter {

//    /**
//     * 实现简单的丢弃协议：丢弃所有接受到的数据，并不做有任何的响应的协议。
//     */
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//         // Discard the received data silently.
//         ((ByteBuf) msg).release();
//    }

//    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        try {
            byte[] data = new byte[buf.readableBytes()];
            buf.readBytes(data);
            String clientMsg = new String(data, "utf-8");
            System.out.println(clientMsg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

//    /**
//     * 服务器回传数据
//     * @param ctx
//     * @param cause
//     */
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        ctx.write(msg);
//        ctx.flush();
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
