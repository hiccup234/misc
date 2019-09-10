package top.hiccup.jdk.io.socket.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 传统阻塞I/O：客户端类
 *
 * @author wenhy
 * @date 2018/2/5
 */
public class Client {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 23401;

    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            // 1、创建Socket对象时，就会调用本地方法connect0去连接远程服务器，会导致远程服务器的accept0返回
            // 即两端程序建立起了连接，如果服务端此时未启动则会抛异常：java.net.ConnectException: Connection refused: connect
            socket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            // 向服务器端发送数据
            out.println("你好服务器..");
            String response = in.readLine();
            System.out.println("Client接收到:" + response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    // 客户端socket记得也要关闭
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            socket = null;
        }
    }
}

