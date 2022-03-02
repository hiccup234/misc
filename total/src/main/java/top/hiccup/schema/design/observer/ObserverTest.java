package top.hiccup.schema.design.observer;

import top.hiccup.jdk.container.jdk5.ArrayList;

import java.util.List;

/**
 * 观察者模式：
 *
 * @author wenhy
 * @date 2020/8/16
 */
public class ObserverTest {

    public static void main(String[] args) {
        Product product = new Product();
        product.registObserver(new NameObserver());
        product.setName("ppp");
    }

}

interface Observer {
    void notifyy(Observable o, Object arg);
}

class NameObserver implements Observer {

    @Override
    public void notifyy(Observable o, Object arg) {
        System.out.println(o + " 修改名字为：" + arg);
    }
}

abstract class Observable {

    List<Observer> observerList = new ArrayList<>(10);

    public void registObserver(Observer o) {
        observerList.add(o);
    }

    public void removeObserver(Observer o) {
        observerList.remove(o);
    }

    public void notifyAllObservers(Object arg) {
        observerList.stream().forEach(o -> o.notifyy(this, arg));
    }
}

class Product extends Observable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyAllObservers(name);
    }
}

