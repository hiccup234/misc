package top.hiccup.schema.design.factory;

/**
 * 抽象工厂模式：抽象工厂不再像工厂方法只生产单一的产品，而是可以生产多种类型的产品
 *
 * @author wenhy
 * @date 2020/7/13
 */
public class AbstractFactory {

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

    static interface Commodity {
        public abstract String desc();
    }

    static class ConcreteCommodityA implements Commodity {
        @Override
        public String desc() {
            return "Commodity A";
        }
    }

    static class ConcreteCommodityB implements Commodity {
        @Override
        public String desc() {
            return "Commodity B";
        }
    }


    static interface Factory {
        public abstract Product getProduct();

        public abstract Commodity getCommodity();
    }

    static class ConcreteFactoryA implements Factory {
        @Override
        public Product getProduct() {
            return new ConcreteProductA();
        }

        @Override
        public Commodity getCommodity() {
            return new ConcreteCommodityA();
        }
    }

    static class ConcreteFactoryB implements Factory {
        @Override
        public Product getProduct() {
            return new ConcreteProductB();
        }

        @Override
        public Commodity getCommodity() {
            return new ConcreteCommodityB();
        }
    }

    static class Program {
        public static void main(String[] args) {
            Factory factory = new ConcreteFactoryA();
            Product product = factory.getProduct();
            Commodity commodity = factory.getCommodity();
            System.out.println(product.name());
            System.out.println(commodity.desc());

            factory = new ConcreteFactoryB();
            product = factory.getProduct();
            commodity = factory.getCommodity();
            System.out.println(product.name());
            System.out.println(commodity.desc());
        }
    }
}
