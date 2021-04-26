package top.hiccup.schema.design;

import org.junit.Test;

/**
 * Q：实现一个能够生产不同类型手机（Android，iPhone）的工厂，考虑未来可能的扩展
 * A：考虑扩展就要遵循开闭原则
 *
 * @author wenhy
 * @date 2019/5/30
 */
public class PhoneFactory {

    class Phone {
    }

    class APhone extends Phone {
    }

    class IPhone extends Phone {
    }

    public Phone produce(ProduceStrategy strategy) {
        System.out.println("开始生产");
        Phone phone = strategy.apply();
        System.out.println("完成生产");
        return phone;
    }

    interface ProduceStrategy {
        Phone apply();
    }

    class APhoneProduceStrategy implements ProduceStrategy {
        @Override
        public Phone apply() {
            return new APhone();
        }
    }

    class IPhoneProduceStrategy implements ProduceStrategy {
        @Override
        public Phone apply() {
            return new IPhone();
        }
    }

    @Test
    public void test() {
        System.out.println(produce(new APhoneProduceStrategy()));
        System.out.println(produce(new IPhoneProduceStrategy()));
        System.out.println(produce(new IPhoneProduceStrategy()));
    }
}


class PhoneFactory2 {
    static class Phone {
    }

    static class APhone extends Phone {
    }

    static class IPhone extends Phone {
    }
    public <T> T produce2(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        System.out.println("开始生产");
        T product = clazz.newInstance();
        System.out.println("完成生产");
        return product;
    }
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        PhoneFactory2 factory2 = new PhoneFactory2();
        System.out.println(factory2.produce2(APhone.class));
        System.out.println(factory2.produce2(IPhone.class));
        System.out.println(factory2.produce2(APhone.class));
    }
}