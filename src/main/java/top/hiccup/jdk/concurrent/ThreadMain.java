package top.hiccup.jdk.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhy on 2017/12/23.
 */
public class ThreadMain {

    private static volatile List list = new ArrayList();

    public void add(){
        list.add("bjsxt");
    }
    public int size(){
        return list.size();
    }

    public static void main(String[] args) {

        final ThreadMain list1 = new ThreadMain();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i = 0; i <10; i++){
                        list1.add();
                        System.out.println("当前线程：" + Thread.currentThread().getName() + "添加了一个元素..");
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(list1.size() == 5){
                        System.out.println("当前线程收到通知：" + Thread.currentThread().getName() + " list size = 5 线程停止..");
                        throw new RuntimeException();
                    }
                }
            }
        }, "t2");

        t1.start();
        t2.start();
    }

}
