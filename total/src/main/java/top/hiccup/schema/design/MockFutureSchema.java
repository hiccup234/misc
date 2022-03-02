package top.hiccup.schema.design;

/**
 * 模拟实现Future设计模式
 *
 * @author wenhy
 * @date 2018/1/6
 */
public class MockFutureSchema {

    public static void main(String[] args) throws InterruptedException {
        FutureData futureData = Executor.execute("发送请求");
        System.out.println("再处理点其他事情。。");
        System.out.println(futureData.getRealData().getDataName());
        System.out.println("请求结束");
    }

}

/**
 * 类比Executor
 */
class Executor {
    public static FutureData execute(String str) {
        System.out.println(str);
        final FutureData data = new FutureData();
        new Thread(() -> {
            //新线程异步处理
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            data.setRealData(new RealData("名字叫小可爱"));
        }).start();
        return data;
    }
}

class FutureData {
    /**
     * FutureData只是持有一个RealData的引用
     */
    private RealData realData;
    private volatile boolean isReady = false;

    public synchronized RealData getRealData() {
        while (!isReady) {
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
        if (isReady) {
            return;
        }
        this.realData = realData;
        isReady = true;
        //这里通知可能被阻塞的线程
        System.out.println("线程唤醒");
        notify();
    }
}

class RealData {
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