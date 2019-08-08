package top.hiccup.jdk.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * Future使用示例
 *
 * Created by wenhy on 2018/1/8.
 */
public class FutureTest {

    public static void main(String[] args) throws Exception {
        // 创建一个固定线程的线程池且线程数为1
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<String> f = executor.submit(new Callable<String> (){
            private String param = null;

            private Callable<String> setParam(String param) {
                this.param = param;
                return this;
            }

            @Override
            public String call() throws Exception {
                Thread.sleep(500);
                String result = param.toUpperCase();
                return result;
            }
        }.setParam("abc"));
        System.out.println("请求完毕");
        while(true) {
            String result = null;
            // TODO 注意，这里工程中一般要指定超时时间
            if((result = f.get(1000, TimeUnit.SECONDS)) != null) {
                System.out.println("数据处理完毕");
                System.out.println("处理结果：" + result);
                break;
            }
        }
        executor.shutdown();
    }
}
