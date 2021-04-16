package top.hiccup.jdk.concurrent.future;

public class MyFutureClient {

    public MyFuture getData() {
        MyFuture myFuture = new MyFuture();
        new Thread(() -> {
            Data data = new Data("跳跳");
            myFuture.setData(data);
        }).start();
        return myFuture;
    }
}
