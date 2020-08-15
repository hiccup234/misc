package top.hiccup.schema.design.strategy;

/**
 * 策略模式：定义一系列算法，把它们一个个封装起来，并且使它们可以相互替换。该模式使得算法可独立于它们的客户变化。
 * <p>
 * 策略模式一般用来优化程序中多组 if else 或者 switch case
 *
 * @author wenhy
 * @date 2020/8/16
 */
public class StrategyTest {

    public static interface DiscountStrategy {
        public double getDiscount(double originPrice);
    }

    public static class VipDiscount implements DiscountStrategy {
        @Override
        public double getDiscount(double originPrice) {
            System.out.println("VIP客户折扣0.7");
            return originPrice * 0.7;
        }
    }

    public static class OrdinaryDiscount implements DiscountStrategy {
        @Override
        public double getDiscount(double originPrice) {
            System.out.println("普通客户折扣0.9");
            return originPrice * 0.9;
        }
    }

    public static class DiscountContext {
        private DiscountStrategy strategy;

        public DiscountContext(DiscountStrategy strategy) {
            this.strategy = strategy;
        }

        public double getDiscountPrice(double price) {
            if (strategy == null) {
                strategy = new OrdinaryDiscount();
            }
            return strategy.getDiscount(price);
        }

        public void changeDiscountStrategy(DiscountStrategy strategy) {
            this.strategy = strategy;
        }
    }

    public static void main(String[] args) {
        DiscountContext discountContext = new DiscountContext(null);
        double originPrice = 89.9;
        System.out.println("普通会员打折价格：" + discountContext.getDiscountPrice(originPrice));
        discountContext.changeDiscountStrategy(new VipDiscount());
        System.out.println("VIP会员打折价格：" + discountContext.getDiscountPrice(originPrice));
    }
}
