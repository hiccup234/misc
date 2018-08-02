package com.hiccup.io.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by wenhy on 2018/2/6.
 */
public class DiscardServerHandler extends ChannelHandlerAdapter {
    /**
     * 实现简单的丢弃协议：丢弃所有接受到的数据，并不做有任何的响应的协议。
     */

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//         // Discard the received data silently.
//         ((ByteBuf) msg).release();
//    }

    // 在控制台显示客户端发来的信息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        ByteBuf in = (ByteBuf) msg;
//        try {
//            while (in.isReadable()) {
//                System.out.print((char) in.readByte());
//                System.out.flush();
//            }
//        } finally {
//            ReferenceCountUtil.release(msg);
//        }
        ByteBuf buf = (ByteBuf) msg;
        try {
            byte[] data = new byte[buf.readableBytes()];
            buf.readBytes(data);
            String reqStr = new String(data, "utf-8");
            System.out.println("Server:"+reqStr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    // 服务器回传数据
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        ctx.write(msg);
//        ctx.flush();
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
