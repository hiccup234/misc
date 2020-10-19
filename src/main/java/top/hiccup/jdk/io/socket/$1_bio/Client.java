package top.hiccup.jdk.io.socket.$1_bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 传统阻塞I/O：客户端类（JDK1.4之前推出的API）
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
            // 注意：这里的Socket类型跟服务器accept0返回的类型是同一个
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
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (socket != null) {
                    // 客户端socket记得也要关闭
                    socket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 帮助gc
            socket = null;
        }
    }
}

