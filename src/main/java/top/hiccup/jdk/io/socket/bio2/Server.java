package top.hiccup.jdk.io.socket.bio2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 面向Socket（插座）编程简单示例：应用程序TCP/IP直连通信（进程通信）方式
 * <p>
 * 改进：采用线程池而不是每来一个请求就创建一个线程，这样可以大幅减少操作系统创建线程带来的性能开销
 *
 * @author wenhy
 * @date 2018/2/5
 */
public class Server {

    private static final int PORT = 23401;

    public static void main(String[] args) {
        ServerSocket server = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started..");
            Socket socket = null;
            // 持有一个线程池
            ExecutorService executorService = new ThreadPoolExecutor(
                    // 可用处理器数
                    Runtime.getRuntime().availableProcessors(),
                    50,
                    120L,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(1000));
            while (true) {
                socket = server.accept();
                executorService.execute(new ServerHandler(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (server != null) {
                try {
                    server.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            server = null;
        }
    }
}

class ServerHandler implements Runnable {

    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);
            String body = null;
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            while (true) {
                body = in.readLine();
                if (body == null) {
                    break;
                }
                System.out.println("Server:" + body);
                out.println(dateFormat.format(new Date()) + ":客户端你好..");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            socket = null;
        }
    }
}