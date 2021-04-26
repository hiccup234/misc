package top.hiccup.algorithm.satck;

import java.util.LinkedList;

/**
 * 用栈来实现队列
 * 注意：push和pop的交替问题
 *
 * @author wenhy
 * @date 2019/5/10
 */
public class $3_ImplementQueueWithStacks {

   static class MyQueue {
        private LinkedList<Integer> stackIn;
        private LinkedList<Integer> stackOut;

        public MyQueue() {
            stackIn = new LinkedList<>();
            stackOut = new LinkedList<>();
        }

        public void push(int x) {
            stackIn.push(x);
        }

        public int pop() {
            if (empty()) {
                throw new NullPointerException();
            }
            if (stackOut.size() == 0) {
                int size = stackIn.size();
                for (int i = 0; i < size; i++) {
                    stackOut.push(stackIn.pop());
                }
            }
            return stackOut.pop();
        }

        public int peek() {
            if (empty()) {
                throw new NullPointerException();
            }
            if (stackOut.size() == 0) {
                int size = stackIn.size();
                for (int i = 0; i < size; i++) {
                    stackOut.push(stackIn.pop());
                }
            }
            return stackOut.peek();
        }

        public boolean empty() {
            return stackIn.size() == 0 && stackOut.size() == 0;
        }
    }

    public static void main(String[] args) {
        MyQueue myQueue = new MyQueue();
        myQueue.push(1);
        myQueue.push(2);
        System.out.println(myQueue.peek());
        System.out.println(myQueue.pop());
    }
}
