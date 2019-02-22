package top.hiccup.algorithm.bloom;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.hash.Funnels;

/**
 * 布隆过滤器单元测试类
 *
 * @VM args: -Xms64m -Xmx64m -XX:+PrintGCDetails -XX:+PrintHeapAtGC
 *
 * @author wenhy
 * @date 2019/1/28
 */
public class BloomFilterTest {

    @Test
    public void bloomFilterTest() {
        long startTime = System.currentTimeMillis();
        BloomFilter bloomFilters = new BloomFilter(10000000);
        for (int i = 0; i < 100000000; i++) {
            bloomFilters.add(i + "");
        }
        Assert.assertTrue(bloomFilters.check(1 + ""));
        Assert.assertTrue(bloomFilters.check(3 + ""));
        Assert.assertTrue(bloomFilters.check(999999L + ""));
        Assert.assertFalse(bloomFilters.check(400230340 + "ff"));
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
    }

    /**
     * guava包中提供了布隆过滤器的实现
     */
    @Test
    public void guavaTest() {
        long startTime = System.currentTimeMillis();
        // 预计存放的数据量  误报率
        com.google.common.hash.BloomFilter<Integer> filter = com.google.common.hash.BloomFilter.create(Funnels.integerFunnel(), 10000000, 0.01);
        for (int i = 0; i < 10000000; i++) {
            filter.put(i);
        }
        Assert.assertTrue(filter.mightContain(1));
        Assert.assertTrue(filter.mightContain(2));
        Assert.assertTrue(filter.mightContain(3));
        Assert.assertFalse(filter.mightContain(10000000));
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
    }
}
