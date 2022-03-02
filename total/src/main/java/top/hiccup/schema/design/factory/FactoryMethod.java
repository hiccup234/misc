package top.hiccup.schema.design.factory;

/**
 * 工厂方法模式：生产什么产品不再由方法的参数决定，而是由不同的工厂实例决定生产不同的产品
 *
 * 简单工厂模式存在违反开闭原则的缺点（修改增加或修改产品种类需要修改工厂类）
 *
 * @author wenhy
 * @date 2020/7/12
 */
public class FactoryMethod {

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

    static interface Factory {
        public abstract Product getProduct();
    }

    static class ConcreteFactoryA implements Factory {
        @Override
        public Product getProduct() {
            return new ConcreteProductA();
        }
    }

    static class ConcreteFactoryB implements Factory {
        @Override
        public Product getProduct() {
            return new ConcreteProductB();
        }
    }

    public static void main(String[] args) {
        Factory factory = new ConcreteFactoryA();
        Product product = factory.getProduct();
        System.out.println(product.name());

        factory = new ConcreteFactoryB();
        product = factory.getProduct();
        System.out.println(product.name());
    }
}
