package top.hiccup.jdk.io.tel.server;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import top.hiccup.jdk.io.tel.protocol.LoginRequestPacket;
import top.hiccup.jdk.io.tel.protocol.MsgPacket;
import top.hiccup.jdk.io.tel.protocol.Packet;
import top.hiccup.jdk.io.tel.protocol.PacketCodeC;

/**
 * 服务端请求处理器
 *
 * @author wenhy
 * @date 2019/1/24
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object data) {
        ByteBuf byteBuf = (ByteBuf) data;
        int magicNum = byteBuf.getInt(0);
        if (PacketCodeC.MAGIC_NUMBER != magicNum) {
            int length = byteBuf.readableBytes();
            byte[] bytes = new byte[length];
            byteBuf.readBytes(bytes);
            System.out.println(new String(bytes));
            return ;
        }
        Packet packet = PacketCodeC.decode(byteBuf);

        // 判断是否是登录请求数据包
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            if (loginRequestPacket.getPassword().equals("234234")) {
                // 登录成功
            } else {

            }
        } else {
            System.out.println(((MsgPacket)packet).getMsg());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            byte[] bytes = ("你好，客户端，现在时间是：" + dateFormat.format(new Date())).getBytes(Charset.forName("utf-8"));
            ByteBuf buffer = ctx.alloc().buffer();
            buffer.writeBytes(bytes);
            ctx.channel().writeAndFlush(buffer);
        }
    }
}
