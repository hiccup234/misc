package top.hiccup.jdk.concurrent.future;

public class Data {

    private String name;

    public Data(String name) {
        System.out.println("开始构造数据...");
        try {
            Thread.sleep(3000);
            this.name = name;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("构造数据完成！！！");
    }

    @Override
    public String toString() {
        return name;
    }

}
