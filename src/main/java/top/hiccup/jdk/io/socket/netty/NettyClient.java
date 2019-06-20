package top.hiccup.jdk.io.socket.netty;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.tools.ant.util.DateUtils;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Netty框架NIO客户端
 *
 * @author wenhy
 * @date 2019/1/13
 */
public class NettyClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8888;

    public static void main(String[] args) {
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
                            // 解决粘包问题
                            // 1、以$为分隔符
//                            ByteBuf buf = Unpooled.copiedBuffer("$".getBytes());
//                            channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buf));
                            // 2、使用固定长度的报文
//                            channel.pipeline().addLast(new FixedLengthFrameDecoder(10));

                            // 3、分报文头和报文体，报文体放报文体长度

                            channel.pipeline().addLast(new StringEncoder());
                            channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) {
                                    System.out.println("通道激活");
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx,  Object msg) {
                                    ByteBuf buffer = ctx.alloc().buffer();
                                    byte[] bytes = "哈哈哈".getBytes(Charset.forName("utf-8"));
                                    buffer.writeBytes(bytes);
//                                    ctx.channel().writeAndFlush(buffer);
//                                    ctx.channel().writeAndFlush(buffer);
//                                    ctx.channel().writeAndFlush(buffer);

                                    ByteBuf byteBuf = (ByteBuf) msg;
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    System.out.println(sdf.format(new Date()) + ": " + byteBuf.toString(Charset.forName("utf-8")));
                                }
                            });
                        }
                    });
            // 建立连接，依然是异步接口，可以添加事件通知
//            Channel channel = bootstrap.connect(HOST, PORT).channel();
//            Channel channel = connect(bootstrap, HOST, PORT);
            Channel channel = connect(bootstrap, HOST, PORT, MAX_RETRY);
            while (true) {
                channel.writeAndFlush( "你好，服务器$");
                channel.writeAndFlush( "你好，服务器$");
                channel.writeAndFlush( "你好，服务器$");
                channel.writeAndFlush( "你好，服务器$");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
        System.out.println("main线程结束");
    }

    private static Channel connect(Bootstrap bootstrap, String host, int port) {
        return bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else {
                System.err.println("连接失败，开始重连");
                connect(bootstrap, host, port);
            }
        }).channel();
    }

    private static int MAX_RETRY = 8;

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
