package top.hiccup.jdk.io.socket.$3_nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * nio
 *
 * @author wenhy
 * @date 2018/2/5
 */
public class Server {

    private static final int PORT = 23401;

    public static void main(String[] args) throws IOException {
        // 1、打开多路复用器（管理所有的通道Channel）
        // Linux内核IO多路复用：select、poll（轮询监听）和 epoll（事件通知）
        Selector listenSelector = Selector.open();
        Selector handleSelector = Selector.open();

        // 监听子线程
        new Thread(() -> {
            try {
                // 2、打开服务器通道，对应BIO编程中服务端启动
                ServerSocketChannel listenerChannel = ServerSocketChannel.open();
                // 3、设置服务器通道为非阻塞模式
                listenerChannel.configureBlocking(false);
                // 4、绑定地址和端口
                listenerChannel.socket().bind(new InetSocketAddress(PORT));
                // 5、把服务器通道注册到多路复用器上，并且监听阻塞事件
                listenerChannel.register(listenSelector, SelectionKey.OP_ACCEPT);
                System.out.println("Server start at port: " + PORT);

                while (true) {
                    // 监测是否有新的连接，这里的1指的是阻塞的时间为 1ms
                    if (listenSelector.select(1) > 0) {
                        // 返回的是这个Selector轮询的所有的channel
                        Set<SelectionKey> set = listenSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = set.iterator();
                        while (keyIterator.hasNext()) {
                            SelectionKey key = keyIterator.next();
                            if (key.isAcceptable()) {
                                try {
                                    // 每来一个新连接，不需要创建一个线程，而是直接注册到handleSelector
                                    SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
                                    clientChannel.configureBlocking(false);
                                    clientChannel.register(handleSelector, SelectionKey.OP_READ);
                                } finally {
                                    keyIterator.remove();
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // 处理子线程
        new Thread(() -> {
            try {
                while (true) {
                    // 批量轮询是否有哪些连接有数据可读，这里的1指的是阻塞的时间为 1ms
                    if (handleSelector.select(1) > 0) {
                        Set<SelectionKey> set = handleSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = set.iterator();
                        while (keyIterator.hasNext()) {
                            SelectionKey key = keyIterator.next();
                            if (key.isReadable()) {
                                try {
                                    SocketChannel clientChannel = (SocketChannel) key.channel();
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                    // 使用ByteBuffer
                                    clientChannel.read(byteBuffer);
                                    byteBuffer.flip();
                                    String s = Charset.forName("UTF-8").newDecoder().decode(byteBuffer).toString();
                                    System.out.println(s);
//                                    System.out.println(new String(byteBuffer.array(), "UTF-8"));
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(2048);
                                    writeBuffer.put(("你好，" + s).getBytes());
                                    clientChannel.write(writeBuffer);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    keyIterator.remove();
                                    key.interestOps(SelectionKey.OP_READ);
                                }
                            }

                        }
                    }
                }
            } catch (IOException ignored) {
            }
        }).start();

        System.out.println("主线程结束");
    }
}


// 另一种实现方式
class AnOtherServer implements Runnable {
    private Selector seletor;
    /**
     * 2、建立读缓冲区
     */
    private ByteBuffer readBuf = ByteBuffer.allocate(1024);
    /**
     * 3、建立写缓冲区
     */
    private ByteBuffer writeBuf = ByteBuffer.allocate(1024);

    public AnOtherServer(int port) {
        try {
            // 1、打开多路复用器
            this.seletor = Selector.open();
            // 2、打开服务器通道
            ServerSocketChannel ssc = ServerSocketChannel.open();
            // 3、设置服务器通道为非阻塞模式
            ssc.configureBlocking(false);
            // 4、绑定地址和端口
            ssc.bind(new InetSocketAddress(port));
            // 5、把服务器通道注册到多路复用器上，并且监听阻塞事件
            ssc.register(this.seletor, SelectionKey.OP_ACCEPT);
            System.out.println("NioServer start, port :" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 1、必须要让多路复用器开始监听
                this.seletor.select();
                // 2、返回多路复用器已经选择的结果集
                Iterator<SelectionKey> keys = this.seletor.selectedKeys().iterator();
                // 3、进行遍历
                while (keys.hasNext()) {
                    // 4、获取一个选择的元素
                    SelectionKey key = keys.next();
                    // 5、直接从容器中移除就可以了
                    keys.remove();
                    // 6、如果是有效的
                    if (key.isValid()) {
                        // 7、如果为阻塞状态
                        if (key.isAcceptable()) {
                            this.accept(key);
                        }
                        // 8、如果为可读状态
                        if (key.isReadable()) {
                            this.read(key);
                        }
                        // 9、写数据
                        if (key.isWritable()) {
                            this.write(key);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void accept(SelectionKey key) {
        try {
            // 1、获取服务通道
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            // 2、执行阻塞方法
            SocketChannel sc = ssc.accept();
            // 3、设置阻塞模式
            sc.configureBlocking(false);
            // 4、注册到多路复用器上，并设置读取标识
            sc.register(this.seletor, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void read(SelectionKey key) throws IOException {
        // 1、清空缓冲区旧的数据
        this.readBuf.clear();
        // 2、获取之前注册的socket通道对象
        SocketChannel sc = (SocketChannel) key.channel();
        // 3、读取数据
        int count = sc.read(this.readBuf);
        // 4、如果没有数据
        if (count == -1) {
            key.channel().close();
            key.cancel();
            return;
        }
        // 5、有数据则进行读取 读取之前需要进行复位方法(把position 和limit进行复位)
        this.readBuf.flip();
        // 6、根据缓冲区的数据长度创建相应大小的byte数组，接收缓冲区的数据
        byte[] bytes = new byte[this.readBuf.remaining()];
        // 7、接收缓冲区数据
        this.readBuf.get(bytes);
        // 8、打印结果
        String body = new String(bytes).trim();
        System.out.println("Server: " + body);
    }

    private void write(SelectionKey key) throws ClosedChannelException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        ssc.register(this.seletor, SelectionKey.OP_WRITE);
    }
}