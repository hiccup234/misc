package top.hiccup.jdk.concurrent.future;

public class MyFuture {

    private Data data;

    private volatile boolean ready = false;

    public synchronized void setData(Data data) {
        if (ready) {
            return;
        }
        this.data = data;
        ready = true;
        notify();
    }

    public synchronized Data getData() {
        while (!ready) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

}
