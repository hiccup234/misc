package top.hiccup.jdk.util;

import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 优先队列测试，底层数据结构为堆，每次add或offer都回做堆调整，以保证堆顶的优先级最高
 *
 * @author wenhy
 * @date 2018/1/4
 */
public class PriorityQueueTest {

    public static void main(String[] args) {

        PriorityBlockingQueue<Task> priorityQueue = new PriorityBlockingQueue<>();

        Task t1 = new Task(6, "任务3");
        Task t2 = new Task(1, "任务1");
        Task t3 = new Task(3, "任务2");

        priorityQueue.add(t1);
        priorityQueue.add(t2);
        priorityQueue.add(t3);

        /**
         * iterator遍历出来的顺序不能保证优先级，需要通过take保证
         */
//        Iterator<Task> iterator = priorityQueue.iterator();
//        while (iterator.hasNext()) {
//            Task task = iterator.next();
//            System.out.println(task.getName());
//        }

        Iterator<Task> iterator = priorityQueue.iterator();
        while (iterator.hasNext()) {
            Task task = null;
            try {
                task = priorityQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(task.getName());
        }
    }

}

class Task implements Comparable<Task>{

    private Integer priority;
    private String name;

    public Task(Integer priority, String name) {
        this.priority = priority;
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Task task) {
        return this.priority > task.priority ? 1 : -1;
    }

}