package top.hiccup.schema.design;

/**
 * Created by wenhy on 2018/1/6.
 */
public class MockFutureSchema {

    /**
     * 模拟实现Future设计模式
     */
    public static void main(String[] args) throws InterruptedException {
        FutureClient fc = new FutureClient();
        FutureData futureData = fc.sendRequest("发送请求");
        System.out.println(futureData.getRealData().getDataName());
    }

}

class FutureClient {
    public FutureData sendRequest(String str) {
        System.out.println(str);
        final FutureData data = new FutureData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //新线程异步处理
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                data.setRealData(new RealData("真实数据"));
            }
        }).start();
        return data;
    }
}
class FutureData implements Data {
    //FutureData持有一个RealData的引用
    private RealData realData;
    private volatile boolean isReady = false;

    public synchronized RealData getRealData() {
        while(!isReady) {
            try {
                System.out.println("线程阻塞");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return realData;
    }

    public synchronized void setRealData(RealData realData) {
        if(isReady) {
            return ;
        }
        this.realData = realData;
        isReady = true;
        //这里通知可能被阻塞的线程
        System.out.println("线程唤醒");
        notify();
    }
}
class RealData implements Data {
    private String dataName;

    public RealData(String dataName) {
        this.dataName = dataName;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }
}
interface Data {
    int i = 0;
}