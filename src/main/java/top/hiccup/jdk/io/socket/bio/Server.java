package top.hiccup.jdk.io.socket.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 面向Socket（插座）编程简单示例：应用程序TCP/IP直连通信（进程通信）方式
 *
 * Socket是位于传输层和网络层的
 *
 * @author wenhy
 * @date 2017/2/5
 */
public class Server {

    private static final int PROT = 23401;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PROT);
            System.out.println("Server started..");
            // 阻塞等待客户端的请求到来，由本地方法accept0阻塞程序
            Socket socket = serverSocket.accept();
            // 这里存在的问题是：每来一个客户端请求就要创建一个线程，Windows目前最多能支持到1000个，Linux最多也只能到2000左右
            // 而且频繁创建线程很消耗系统资源，如果由当前线程处理请求，则Server就没法监听其他客户端请求的到来了（只能一个一个响应客户端请求）
            new Thread(new ServerHandler(socket)).start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(serverSocket != null) {
                try {
                    // 关闭Socket
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 赋值为null，帮助gc
            serverSocket = null;
        }
    }
}