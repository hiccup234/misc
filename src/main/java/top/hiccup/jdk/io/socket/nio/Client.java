package top.hiccup.jdk.io.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * NIO客户端
 *
 * @author wenhy
 * @date 2018/2/5
 */
public class Client {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 23401;

    public static void main(String[] args) {
        // 1、创建连接的地址
        InetSocketAddress address = new InetSocketAddress(HOST, PORT);
        // 2、声明连接通道
        SocketChannel socketChannel = null;
        // 3、建立缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        try {
            // 4、打开通道
            socketChannel = SocketChannel.open();
            // 5、进行连接（如果此时服务器没有启动，则会报java.net.ConnectException: Connection refused: connect）
            socketChannel.connect(address);
            while (true) {
                // 6、定义1KB的字节数组，然后使用从标准输入读取
                System.out.print("请输入：");
                byte[] bytes = new byte[1024];
                System.in.read(bytes);
                // 7、把数据放到缓冲区中
                buf.put(bytes);
                // 8、对缓冲区进行复位
                buf.flip();
                // 9、写出数据
                socketChannel.write(buf);
                Thread.sleep(50);
                ByteBuffer readBuf = ByteBuffer.allocate(2048);
                socketChannel.read(readBuf);
                String s = Charset.forName("UTF-8").newDecoder().decode(readBuf).toString();
                System.out.println(s);
                // 10、清空缓冲区数据
                buf.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socketChannel != null) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
