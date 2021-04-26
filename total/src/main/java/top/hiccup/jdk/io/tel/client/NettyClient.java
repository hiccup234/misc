package top.hiccup.jdk.io.tel.client;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import top.hiccup.jdk.io.tel.protocol.BytePacket;
import top.hiccup.jdk.io.tel.protocol.MsgPacket;
import top.hiccup.jdk.io.tel.protocol.PacketCodeC;

/**
 * Netty框架客户端
 *
 * @author wenhy
 * @date 2019/1/13
 */
public class NettyClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8234;
    private static int MAX_RETRY = 8;

    public static void main(String[] args) {
        request();
        System.out.println("main线程结束");
    }

    private static void request() {
        Runnable runnable = () -> {
            Bootstrap bootstrap = new Bootstrap();
            NioEventLoopGroup group = new NioEventLoopGroup();
            // 指定线程模型
            bootstrap.group(group)
                    // 指定IO类型
                    .channel(NioSocketChannel.class)
                    // 连接超时时间
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    // 指定IO处理逻辑
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            channel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            Channel channel = connect(bootstrap, HOST, PORT, MAX_RETRY);
            while (true) {
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();
                line = "你好，服务器：" + line;
                MsgPacket packet = new MsgPacket(line);
                ByteBuf byteBuf = PacketCodeC.encode(packet);
                // 会自动释放byteBuf
                channel.writeAndFlush(byteBuf);
            }
        };
        new Thread(runnable).start();
    }

    private static Channel connect(Bootstrap bootstrap, String host, int port) {
        return connect(bootstrap, host, port, 0);
    }

    private static Channel connect(Bootstrap bootstrap, String host, int port, int retry) {
        return bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功！");
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔（1s,2s,4s,8s...）
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连..");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        }).channel();
    }
}