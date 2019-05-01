package top.hiccup.jdk.vm.agent;

/**
 * Agent 测试类
 *
 * javac的时候去掉package声明
 *
 * 编译后在当前目录下执行java -javaagent:myagent.jar AgentTest，即可看到agent效果
 *
 * @author wenhy
 * @date 2019/5/1
 */
public class AgentTest {

    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}
