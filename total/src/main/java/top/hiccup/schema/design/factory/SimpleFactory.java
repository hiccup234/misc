package top.hiccup.schema.design.factory;

/**
 * 简单工厂模式：使对象的调用和对象的创建过程分离，调用者直接向工厂请求被调用对象
 * 1、普通简单工厂
 * 2、多方法简单工厂
 * 3、静态方法简单工厂
 *
 * @author wenhy
 * @date 2020/7/12
 */
public class SimpleFactory {

    static interface Product {
        public abstract String name();
        // other abstract busi method
    }

    static class ConcreteProductA implements Product {
        @Override
        public String name() {
            return "Product A";
        }
    }

    static class ConcreteProductB implements Product {
        @Override
        public String name() {
            return "Product B";
        }
    }

    static class Factory {
        public static Product getProduct(String name) {
            Product product = null;
            switch (name) {
                case "A":
                    product = new ConcreteProductA();
                    break;
                case "B":
                    product = new ConcreteProductB();
                    break;
                default:
                    break;
            }
            return product;
        }
    }

    static class Program {
        public static void main(String[] args) {
            Product product = Factory.getProduct("A");
            System.out.println(product.name());

            product = Factory.getProduct("B");
            System.out.println(product.name());
        }
    }
}
