package top.hiccup.jdk.io.socket.$1_bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 面向Socket（插座）编程简单示例：应用程序TCP/IP直连通信（进程通信）方式
 * <p>
 * Socket是位于传输层和网络层的，即：TCP/UDP --> (Socket 抽象) --> IP，由底层操作系统提供的概念
 *
 * @author wenhy
 * @date 2017/2/5
 */
public class Server {

    private static final int PORT = 23401;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            // 1、绑定和监听端口，本地方法bind0和listen0
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server start at port: " + PORT);
            // 2、阻塞等待客户端的请求到来，由本地方法accept0阻塞程序
            Socket socket = serverSocket.accept();

            // 3、这里存在的问题是：每来一个客户端请求就要创建一个线程，Windows目前最多能支持到1000个，Linux最多也只能到2000左右线程数
            // 而且频繁创建线程很消耗系统资源，但是如果由当前线程处理请求，则Server就没法监听其他客户端请求的到来了（只能串行一个一个响应客户端请求）
            // 也可以交给当前线程处理
            new Thread(new ServerHandler(socket)).start();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    // 4、关闭Socket
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 赋值为null，帮助gc
            serverSocket = null;
        }
    }

    public static class ServerHandler implements Runnable {
        private Socket socket = null;

        public ServerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedReader in = null;
            PrintWriter out = null;
            try {
                // 字符流
                in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                out = new PrintWriter(this.socket.getOutputStream(), true);
                String body = null;
                while (true) {
                    body = in.readLine();
                    if (body == null) {
                        break;
                    }
                    System.out.println("Server接收到:" + body);
                    out.println("你好客户端..");
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                    if (socket != null) {
                        // 监听返回的socket也要关闭
                        socket.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}