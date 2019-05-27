package top.hiccup.algorithm.problem.geek;

import java.util.LinkedList;

/**
 * 编程模拟实现一个浏览器的前进、后退功能（两个栈）
 *
 * @author wenhy
 * @date 2019/5/27
 */
public class $10_ForwardBackward {

    private LinkedList<Integer> stack1 = new LinkedList<>();
    private LinkedList<Integer> stack2 = new LinkedList<>();

    public void view(int num) {
        // stack1栈顶是当前浏览的页面编号
        stack1.push(num);
        // 新加入要清空之前的backward轨迹
        stack2.clear();
    }

    public int forward() {
        if (stack2.size() > 0) {
            int result = stack2.pop();
            stack1.push(result);
            return result;
        } else {
            throw new RuntimeException("无法再前进");
        }
    }

    public int backward() {
        int curView = stack1.pop();
        stack2.push(curView);
        return stack1.peek();
    }
}
