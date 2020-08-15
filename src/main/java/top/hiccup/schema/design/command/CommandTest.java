package top.hiccup.schema.design.command;

/**
 * 命令模式：将一个请求封装为一个对象，从而使你可用不同的请求对客户进行参数化；对请求排队或记录请求日志，以及支持可撤销的操作。
 *
 * 策略模式是通过不同的算法做同一件事情：例如排序，而命令模式则是通过不同的命令做不同的事情，常含有（关联）接收者。
 *
 * @author wenhy
 * @date 2020/8/15
 */
public class CommandTest {

    public static interface Command {
        void process(int[] target);
    }

    public static class ProcessArray {
        public static void each(int[] target, Command command) {
            command.process(target);
        }
    }

    public static void main(String[] args) {
        int[] arr = {9, 234, 5, 16};

        ProcessArray.each(arr, a -> {
            for (int i = 0; i < a.length; i++) {
                System.out.println("arr[" + i + "] = " + a[i]);
            }
        });

        ProcessArray.each(arr, a -> {
            int count = 0;
            for (int i : a) {
                count += i;
            }
            System.out.println("arr count = " + count);
        });
    }
}
