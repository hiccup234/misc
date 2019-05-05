//package top.hiccup.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

/**
 * git批量clone
 *
 * @author wenhy
 * @date 2019/5/5
 */
public class GitBatchClone {

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        FileInputStream in = new FileInputStream("./git.properties");
        properties.load(in);
        Collection values = properties.values();
        if (values != null && values.size() > 0) {
            for (Object val : values) {
                System.out.println("开始clone：" + val);
                Runtime.getRuntime().exec("git clone " + val);
                System.out.println("clone完毕：" + val);
            }
            System.out.println("clone完毕");
            in.close();
        } else {
            System.out.println("未发现配置git");
        }
    }
}
