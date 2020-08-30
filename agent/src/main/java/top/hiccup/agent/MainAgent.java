package top.hiccup.agent;

import top.hiccup.agent.agentmain.TransClass;

/**
 * 程序启动之后启动代理(agent-main)
 *
 * @author wenhy
 * @date 2020/8/30
 */
public class MainAgent {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(new TransClass().getNumber());
        int count = 0;
        while (true) {
            Thread.sleep(500);
            count++;
            int number = new TransClass().getNumber();
            System.out.println(number);
            if (3 == number || count >= 10) {
                break;
            }
        }
    }
}
