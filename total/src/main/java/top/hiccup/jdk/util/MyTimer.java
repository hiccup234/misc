package top.hiccup.jdk.util;

/**
 * MyTimer
 *
 * @author wenhy
 * @date 2022/1/28
 */
public class MyTimer {

    private Integer time;

    private Runnable runnable;

    private Thread thread;

    public MyTimer(Integer time, Runnable runnable) {
        this.time = time;
        this.runnable = runnable;
    }

    public void start() {
        thread = new Thread(()-> {
            long count = 0;
            while (true) {
                System.out.println("定时任务执行，count=" + ++count);
                runnable.run();
                try {
                    Thread.sleep(time * 1000);
                } catch (InterruptedException e) {
                    System.out.println("定时任务被中断");
                    break;
                }
            }
        });
        thread.setDaemon(Boolean.TRUE);
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        MyTimer timer = new MyTimer(5, ()-> System.out.println("每天都有好心情！"));
        timer.start();
        Thread.sleep(60 * 1000);
        timer.stop();
    }
}
