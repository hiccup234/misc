package top.hiccup.jdk.io.socket.$2_bio2;

import java.net.ServerSocket;
import java.net.Socket;
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
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server start at port: " + PORT);
            Socket socket = null;
            // 持有一个线程池
            ExecutorService executorService = new ThreadPoolExecutor(
                    // 核心线程数--可直接取处理器数
                    Runtime.getRuntime().availableProcessors() + 1,
                    50,
                    120L,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(1000));
            while (true) {
                socket = serverSocket.accept();
                // 接收到请求后交给线程池执行，不再新建线程
                executorService.execute(new top.hiccup.jdk.io.socket.$1_bio.Server.ServerHandler(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            serverSocket = null;
        }
    }
}