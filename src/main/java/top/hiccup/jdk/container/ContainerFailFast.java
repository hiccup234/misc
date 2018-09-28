package top.hiccup.jdk.container;

import java.util.ArrayList;
import java.util.List;

/**
 * 非线程安全容器的快速失败
 *
 * @author wenhy
 * @date 2018/8/25
 */
public class ContainerFailFast {

    public static void main(String[] args) {
        List list = new ArrayList();
        list.add(new Object());
        list.get(-1);
    }
}
