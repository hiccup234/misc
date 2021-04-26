package top.hiccup.jdk.concurrent.future;

public class FutureTest {

    public static void main(String[] args) {
        MyFutureClient client = new MyFutureClient();
        System.out.println("主线程请求数据...");
        MyFuture future = client.getData();
        System.out.println("主线程干点其他事情1");
        System.out.println("主线程干点其他事情2");
        Data data = future.getData();
        System.out.println(data);
    }
}
