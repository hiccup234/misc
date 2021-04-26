package top.hiccup.jdk.lang.newfeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Jdk1.8 Lambda表达式测试类
 *
 * 基本语法:
 * (parameters) -> expression
 * 或
 * (parameters) -> { statements; }
 *
 * // 1. 不需要参数,返回值为 5
 * () -> 5
 * // 2. 接收一个参数(数字类型),返回其2倍的值
 * x -> 2 * x
 * // 3. 接受2个参数(数字),并返回他们的差值
 * (x, y) -> x – y
 * // 4. 接收2个int型整数,返回他们的和
 * (int x, int y) -> x + y
 * // 5. 接受一个 string 对象,并在控制台打印,不返回任何值(看起来像是返回void)
 * (String s) -> System.out.print(s)
 *
 * @author wenhy
 * @date 2018/8/27
 */
public class Jdk8LambdaTest {

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(20);
        Random random = new Random(47);
        for (int i=0; i<20; i++) {
            numbers.add(random.nextInt(20));
        }
        numbers.forEach((num) -> System.out.print(num + ","));
        System.out.println();
        numbers.forEach(System.out::print);
        System.out.println();

        numbers.stream()
                .filter((num) -> num > 15)
                .forEach((num) -> System.out.println(num));

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous Inner Class");
            }
        }).start();

        new Thread(() -> {
            System.out.println("Lambda");
        }).start();
    }
}
