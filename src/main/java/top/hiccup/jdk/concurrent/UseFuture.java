package top.hiccup.jdk.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by wenhy on 2018/1/8.
 */
public class UseFuture implements Callable<String> {

    /**
     * Future使用示例
     */
    private String para;

    public UseFuture(String para){
        this.para = para;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(3000);
        String result = this.para + "处理完成";
        return result;
    }

    public static void main(String[] args) throws Exception {
        String queryStr = "query";
        //构造FutureTask，并且传入需要真正进行业务逻辑处理的类,该类一定是实现了Callable接口的类
        FutureTask<String> future = new FutureTask<String>(new UseFuture(queryStr));
        //创建一个固定线程的线程池且线程数为1,
        ExecutorService executor = Executors.newFixedThreadPool(1);
        //这里提交任务future,则开启线程执行RealData的call()方法执行
        Future f = executor.submit(future);
        System.out.println("请求完毕");
        while(true) {
            if(null == f.get()) {
                System.out.println("数据处理完成");
                break;
            }
        }
//        try {
//            //这里可以做额外的数据操作，也就是主程序执行其他业务逻辑
//            Thread.sleep(2000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //调用获取数据方法,如果call()方法没有执行完成,则依然会进行等待
        System.out.println("数据：" + future.get());
        executor.shutdown();
    }
}
