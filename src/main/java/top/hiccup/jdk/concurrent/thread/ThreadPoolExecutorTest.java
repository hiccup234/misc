package top.hiccup.jdk.concurrent.thread;

import java.util.concurrent.*;

/**
 * 在使用有界队列时，若有新的任务提交：
 * 1、如果线程池实际线程数小于corePoolSize，则优先创建线程并执行
 * 2、若大于corePoolSize，则将任务加入队列，
 * 3、若队列已满，则在总线程数不大于maximumPoolSize的前提下，创建新的线程并执行本次提交的任务（注意注意：队列里的任务不会优先执行）
 * 4、若队列已满，且线程数大于maximumPoolSize，则执行拒绝策略。
 *
 * @author wenhy
 * @date 2019/1/9
 */
public class ThreadPoolExecutorTest {


    public static void main(String[] args) {

        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                1,                //coreSize
                2,            //MaxSize
                60,            //60
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(3)            //指定一种队列 （有界队列）
                //new LinkedBlockingQueue<Runnable>()
                , new MyRejected()
                //, new DiscardOldestPolicy()
        );

        MyTask mt1 = new MyTask(1, "任务1");
        MyTask mt2 = new MyTask(2, "任务2");
        MyTask mt3 = new MyTask(3, "任务3");
        MyTask mt4 = new MyTask(4, "任务4");
        MyTask mt5 = new MyTask(5, "任务5");
        MyTask mt6 = new MyTask(6, "任务6");

        pool.execute(mt1);
        pool.execute(mt2);
        pool.execute(mt3);
        pool.execute(mt4);
        pool.execute(mt5);
        pool.execute(mt6);

        pool.shutdown();
    }
}

class MyTask implements Runnable {

    private int taskId;
    private String taskName;

    public MyTask(int taskId, String taskName) {
        this.taskId = taskId;
        this.taskName = taskName;
    }

    @Override
    public void run() {
        try {
            System.out.println("run taskId = " + this.taskId);
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyRejected implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("当前被拒绝任务为：" + r.toString());
    }
}