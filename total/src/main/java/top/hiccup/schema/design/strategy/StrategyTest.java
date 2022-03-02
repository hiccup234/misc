package top.hiccup.schema.design.strategy;

import java.math.BigDecimal;

interface DiscountStrategy {
    BigDecimal getDiscount(BigDecimal originPrice);
}

/**
 * 策略模式：定义一系列算法，把它们一个个封装起来，并且使它们可以相互替换。该模式使得算法可独立于它们的客户变化。
 * <p>
 * 策略模式一般用来优化程序中多组 if else 或者 switch case
 *
 * @author wenhy
 * @date 2020/8/16
 */
public class StrategyTest {

    public static void main(String[] args) {
        DiscountContext discountContext = new DiscountContext(null);
        BigDecimal originPrice = BigDecimal.valueOf(89.9);
        System.out.println("普通会员打折价格：" + discountContext.getDiscountPrice(originPrice));
        // TODO 这里会不会存在线程安全问题？
        discountContext.changeDiscountStrategy(new VipDiscount());
        System.out.println("VIP会员打折价格：" + discountContext.getDiscountPrice(originPrice));
    }
}

class VipDiscount implements DiscountStrategy {
    @Override
    public BigDecimal getDiscount(BigDecimal originPrice) {
        System.out.println("VIP客户折扣0.7");
        return originPrice.multiply(BigDecimal.valueOf(0.7));
    }
}

class OrdinaryDiscount implements DiscountStrategy {
    @Override
    public BigDecimal getDiscount(BigDecimal originPrice) {
        System.out.println("普通客户折扣0.9");
        return originPrice.multiply(BigDecimal.valueOf(0.9));
    }
}

class DiscountContext {
    private DiscountStrategy strategy;

    public DiscountContext(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public BigDecimal getDiscountPrice(BigDecimal price) {
        if (strategy == null) {
            strategy = new OrdinaryDiscount();
        }
        return strategy.getDiscount(price);
    }

    public void changeDiscountStrategy(DiscountStrategy strategy) {
        this.strategy = strategy;
    }
}
