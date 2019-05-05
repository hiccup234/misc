//package top.hiccup.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.util.StringUtils;

/**
 * git批量clone
 *
 * @author wenhy
 * @date 2019/5/5
 */
public class GitBatchClone {

    public static void main(String[] args) throws IOException {
        FileInputStream in = new FileInputStream("./git-wallet-channel.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("#") || StringUtils.isEmpty(line)) {
                continue;
            }
            System.out.println("开始clone：" + line);
            Runtime.getRuntime().exec("git clone " + line);
            System.out.println("clone完毕：" + line);
        }
        System.out.println("clone完毕");
        in.close();
    }
}
