package top.hiccup.jdk.vm.primitive;

import java.math.BigDecimal;

/**
 * 浮点数运算精度丢失问题
 * <p>
 * 在《Effective Java》书中提到，float和double只能用来做科学计算或者是工程计算，在商业计算中我们应该用 java.math.BigDecimal。
 *
 * @author wenhy
 * @date 2019/1/17
 */
public class FloatPrecisionMissTest {

    public static void main(String[] args) {
        float totalAmount = 0.09f;
        float freeAmount = 0.02f;
        float tradeAmount = totalAmount - freeAmount;
        System.out.println(tradeAmount);

        double totalAmount2 = 0.09;
        double freeAmount2 = 0.02;
        double tradeAmount2 = totalAmount2 - freeAmount2;
        System.out.println(tradeAmount2);

        // 直接用double参数的构造方法也会出现精度问题
        BigDecimal tradeAmount3 = new BigDecimal(totalAmount2).subtract(new BigDecimal(freeAmount2));
        System.out.println(tradeAmount3);

        BigDecimal tradeAmount4 = new BigDecimal(String.valueOf(totalAmount2)).subtract(new BigDecimal(freeAmount2));
        System.out.println(tradeAmount4);

        // 所以：金额运算尽量使用BigDecimal(String val)进行运算
        BigDecimal tradeAmount5 = new BigDecimal(String.valueOf(totalAmount2)).subtract(new BigDecimal(String.valueOf(freeAmount2)));
        System.out.println(tradeAmount5);
    }
}
