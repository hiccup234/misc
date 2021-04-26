package top.hiccup.jdk.vm.agent;

/**
 * Attach API 方式的agent
 *
 * @author wenhy
 * @date 2019/5/1
 */
public class AttachAgent {

    public static void main(String[] args) {
        if (args.length <= 1) {
            System.out.println("Usage: java AttachTest <PID> /PATH/TO/AGENT.jar");
            return;
        }
        // TODO JDK9 or JDK11的特性？
//        VirtualMachine vm = VirtualMachine.attach(args[0]);
//        vm.loadAgent(args[1]);
    }
}

