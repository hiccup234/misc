package top.hiccup.jdk.io.socket.$2_bio2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 客户端类
 *
 * @author wenhy
 * @date 2018/2/5
 */
public class Client {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 23401;

    public static void main(String[] args) {
        Runnable runnable = () -> {
            Socket socket = null;
            BufferedReader in = null;
            PrintWriter out = null;
            try {
                socket = new Socket(HOST, PORT);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                while (true) {
                    out.println(dateFormat.format(new Date()) + ":" + Thread.currentThread().getName() + ":服务器你好..");
                    String response = in.readLine();
                    System.out.println(Thread.currentThread().getName() + "接收到:" + response);
                    Thread.sleep(1000);
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
                        // 客户端socket记得也要关闭
                        socket.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        };

        // 启动两个客户端
        new Thread(runnable, "客户端1").start();
        new Thread(runnable, "客户端2").start();
    }
}
