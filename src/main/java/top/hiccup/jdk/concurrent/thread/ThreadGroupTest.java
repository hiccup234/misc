package top.hiccup.jdk.concurrent.thread;

/**
 * 线程组测试类：
 *
 * 1、java.lang.ThreadGroup#activeCount() 获取当前线程组内的运行线程数
 *
 * 2、java.lang.ThreadGroup#interrupt() 中断线程组内的所有线程
 *
 * 3、java.lang.ThreadGroup#list() 使用 System.out 打印出所有线程信息
 *
 * 4、java.lang.ThreadGroup#stop() 停止线程组内的所有线程（已不建议使用）
 *
 * @author wenhy
 * @date 2019/1/12
 */
public class ThreadGroupTest {

    public static void main(String[] args) {
        Runnable runnable = () -> {
            System.out.println("线程名称：" + Thread.currentThread().getName());
            System.out.println("线程组名称：" + Thread.currentThread().getThreadGroup());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        ThreadGroup workGroup = new ThreadGroup("worker");
        workGroup.setMaxPriority(Thread.MIN_PRIORITY);

        Thread userTask1 = new Thread(workGroup, runnable, "work-task1");
        Thread userTask2 = new Thread(workGroup, runnable, "work-task2");

        userTask1.start();
        userTask2.start();

        System.out.println("工作线程组活跃线程数：" + workGroup.activeCount());
        workGroup.list();
    }

}
