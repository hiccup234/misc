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