package com.hiccup.schema.design;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by wenhy on 2018/1/6.
 */
public class MockMasterWorkerSchema {

    /**
     * 模式实现Master-Worker设计模式
     */
    public static void main(String[] args) throws InterruptedException {
        Master master = new Master();
        Worker worker = new Worker(master);
        System.out.println(Runtime.getRuntime().availableProcessors());
        master.buildWorkers(worker, Runtime.getRuntime().availableProcessors());
        List<Task> taskList = new LinkedList<>();
        for(int i = 0; i < 100; i++) {
            Task task = new Task(i, "任务"+i);
            taskList.add(task);
        }
        master.submit(taskList);
        master.execute();
        long startTime = System.currentTimeMillis();
        while (!master.isComplete()) {
            System.out.println("客户端等待。。");
            Thread.sleep(1000);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("执行总耗时："+(endTime-startTime));
        System.out.println("结果为："+master.getResult());

    }

}

class Task {
    private int taskId;
    private String taskName;

    public Task(int taskId, String taskName) {
        this.taskId = taskId;
        this.taskName = taskName;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

}

class Master {

    // 1、保存需要处理的任务
    private ConcurrentLinkedQueue<Task> taskQueue = new ConcurrentLinkedQueue<>();

    // 2、保存worker对象
    private HashMap<String, Thread> workers = new HashMap<>();

    // 3、保存每个worker并发执行的结果集
    private ConcurrentHashMap<String, Object> results = new ConcurrentHashMap<>();

    public void buildWorkers(Worker worker, int count) {
        for(int i = 0; i < count; i++) {
            workers.put("worker"+i, new Thread(worker));
        }
    }

    public void submit(List<Task> tasks) {
        this.taskQueue.addAll(tasks);
    }
    public Task getTask() {
        return taskQueue.poll();
    }
    public void setResult(String str, Object result) {
        results.put(str, result);
    }

    public void execute() {
        for(Map.Entry<String, Thread> entry : workers.entrySet()) {
            entry.getValue().start();
        }
    }

    public boolean isComplete() {
        for(Map.Entry<String, Thread> entry : workers.entrySet()) {
            if(entry.getValue().getState() != Thread.State.TERMINATED) {
                return false;
            }
        }
        return true;
    }

    public String getResult() {
        String retStr = "";
        for(Map.Entry<String, Object> entry : results.entrySet()) {
            retStr += (entry.getKey()+entry.getValue());
        }
        return retStr;
    }

}

class Worker implements Runnable{

    private Master master;

    public Worker(Master master) {
        this.master = master;
    }

    @Override
    public void run() {
        while(true) {
            Task task = this.master.getTask();
            if(null == task) break;
            /////////////////////////////////////////////////////////
            try {
                Thread.sleep(3000);
                System.out.println("执行："+task.getTaskName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /////////////////////////////////////////////////////////
            master.setResult(String.valueOf(task.getTaskId()), task.getTaskName()+"任务结果");
        }
    }
}