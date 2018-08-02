package com.hiccup.io.socket_bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wenhy on 2018/2/5.
 */
public class Server {

    /**
     * 面向Socket（插座）编程简单示例：应用程序TCP/IP直连通信（进程通信）方式
     */
    private static final int PROT = 23401;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PROT);
            System.out.println("server start ..");
            // 阻塞等待客户端请求
            Socket socket = serverSocket.accept();
            // 这里存在的问题是：每来一个客户端请求就要创建一个线程，Windows最多能支持到1000个，Linux最多也只能到2000左右
            // 而且平凡创建线程很消耗系统资源
            new Thread(new ServerHandler(socket)).start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            serverSocket = null;
        }
    }
}
