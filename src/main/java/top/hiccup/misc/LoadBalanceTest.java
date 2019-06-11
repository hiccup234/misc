package top.hiccup.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

/**
 * 常见负载均衡算法:
 * 
 * 1、轮询法
 * 
 * 2、加权轮询法
 * 
 * 3、随机法
 * 
 * 4、加权随机法
 * 
 * 5、IP哈希法
 * 
 * 6、LRU
 *
 * @author wenhy
 * @date 2019/6/11
 */
public class LoadBalanceTest {

    /**
     * key:IP  value:权重
     */
    private static Map<String, Integer> ipMap = new HashMap<>();

    static {
        ipMap.put("192.168.0.1", 1);
        ipMap.put("192.168.0.2", 2);
        ipMap.put("192.168.0.3", 1);
    }

    private volatile Integer pos = 0;

    public synchronized String roundRobin() {
        List<String> ipList = new ArrayList<>(ipMap.keySet());
        String ip = null;
        if (pos >= ipList.size()) {
            pos = 0;
        }
        ip = ipList.get(pos);
        pos++;
        return ip;
    }

    public synchronized String roundRobinWithWeight() {
        List<String> ipList = new ArrayList<>(ipMap.keySet());
        for (Map.Entry<String, Integer> entry : ipMap.entrySet()) {
            int weight = entry.getValue();
            if (weight > 1) {
                for (int i = 0; i < weight; i++) {
                    ipList.add(entry.getKey());
                }
            }
        }
        String ip = null;
        if (pos >= ipList.size()) {
            pos = 0;
        }
        ip = ipList.get(pos);
        pos++;
        return ip;
    }

    public synchronized String random() {
        List<String> ipList = new ArrayList<>(ipMap.keySet());
        Random random = new Random();
        int pos = random.nextInt(ipList.size());
        return ipList.get(pos);
    }

    public synchronized String randomWithWeight() {
        List<String> ipList = new ArrayList<>(ipMap.keySet());
        for (Map.Entry<String, Integer> entry : ipMap.entrySet()) {
            int weight = entry.getValue();
            if (weight > 1) {
                for (int i = 0; i < weight; i++) {
                    ipList.add(entry.getKey());
                }
            }
        }
        Random random = new Random();
        int pos = random.nextInt(ipList.size());
        return ipList.get(pos);
    }

    public synchronized String ipHash(String clientIp) {
        List<String> ipList = new ArrayList<>(ipMap.keySet());
        int hashCode = clientIp.hashCode();
        int pos = hashCode % ipList.size();
        return ipList.get(pos);
    }

    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            System.out.println(roundRobin());
        }
    }

    @Test
    public void test2() {
        for (int i = 0; i < 10; i++) {
            System.out.println(roundRobinWithWeight());
        }
    }
}
