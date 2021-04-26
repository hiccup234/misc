package top.hiccup.jdk.lang.newfeature;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * JDK8新特性之Stream流：
 *
 * 【创建流】
 * 1、调用集合的stream()方法或者parallelStream()方法。
 * 2、Stream.of()方法，有针对int,long的专用流IntStream，LongStream...
 *
 *
 * 需要注意的是流只能执行一次，再次使用需要重要打开。
 *
 * @author wenhy
 * @date 2019/1/16
 */
public class Jdk8StreamTest {

    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1, 2, 3, -1);
        // 普通顺序流
        nums.stream().forEach(n -> System.out.println(n));
        System.out.println("====================================");

        // 多核CPU并行流（使用了Fork/Join并行框架）
        nums.parallelStream().forEach(n -> System.out.println(n));
        System.out.println("====================================");

        // 求最小最大值
        Optional<Integer> min = nums.stream().min(Integer::compareTo);
//        min.ifPresent(n -> System.out.println(n));
        min.ifPresent(System.out::println);
        System.out.println("====================================");

        // 排序
        nums.stream().sorted(Integer::compareTo).forEach(n -> System.out.println(n));
        System.out.println("====================================");

        // 过滤
        nums.stream()
                .filter(n -> n > 1)
                .filter(n -> n < 3)
                .forEach(n -> System.out.println(n));
        System.out.println("====================================");

    }
}
