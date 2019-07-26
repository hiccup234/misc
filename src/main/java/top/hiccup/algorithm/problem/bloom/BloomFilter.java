package top.hiccup.algorithm.problem.bloom;

/**
 * 布隆过滤器：（利用了hash的思想，为了降低哈希冲突，采用多个哈希函数，分别得到m个哈希值，再分别映射到位图上）
 *
 * 见图：
 * 首先需要初始化一个二进制的数组，长度设为 L（图中为 8），同时初始值全为 0 。
 * 当写入一个 A1=1000 的数据时，需要进行 H 次 hash 函数的运算（这里为 2 次）；与 HashMap 有点类似，通过算出的 HashCode 与 L 取模后定位到 0、2 处，将该处的值设为 1。
 * A2=2000 也是同理计算后将 4、7 位置设为 1。
 * 当有一个 B1=1000 需要判断是否存在时，也是做两次 Hash 运算，定位到 0、2 处，此时他们的值都为 1 ，所以认为 B1=1000 存在于集合中。
 * 当有一个 B2=3000 时，也是同理。第一次 Hash 定位到 index=4 时，数组中的值为 1，所以再进行第二次 Hash 运算，结果定位到 index=5 的值为 0，所以认为 B2=3000 不存在于集合中。
 *
 * 布隆过滤有以下几个特点：
 * 1、只要返回数据不存在，则肯定不存在。
 * 2、返回数据存在，但只能是大概率存在（因为存在hash冲突的情况，冲突越小则过滤越准确，可以考虑多次hash，也可以考虑动态扩容，超过阈值则再new一个布隆过滤器）
 * 3、同时不能清除其中的数据（原因同2）。
 *
 * bloom filter: False is always false. True is maybe true.
 *
 * TIPS：第三点很重要
 *
 * @author wenhy
 * @date 2019/1/28
 */
public class BloomFilter {

    /**
     * 数组，优化：可以利用long（64bit）来存储，然后使用位操作以节省空间
     */
    private byte[] data;
    private byte[] data2;
    /**
     * 数组大小
     */
    private int dataSize;

    public BloomFilter(int dataSize) {
        this.dataSize = dataSize;
        data = new byte[dataSize];
        data2 = new byte[dataSize];
    }

    /**
     * 写入数据
     */
    public void add(String key) {
        int first = hash1(key);
        int second = hash2(key);
        data[getLawfulIndex(first, dataSize)] = 1;
        data2[getLawfulIndex(second, dataSize)] = 1;
    }

    /**
     * 判断数据是否存在
     */
    public boolean check(String key) {
        int first = hash1(key);
        int second = hash2(key);
        int firstIndex = data[getLawfulIndex(first, dataSize)];
        if (firstIndex == 0) {
            return false;
        }
        int secondIndex = data2[getLawfulIndex(second, dataSize)];
        if (secondIndex == 0) {
            return false;
        }
        return true;
    }

    private int getLawfulIndex(int orginIndex, int range) {
        if (orginIndex > range -1) {
            return getLawfulIndex( orginIndex/2, range);
        }
        return orginIndex;
    }

    /**
     * hash算法1
     */
    private int hash1(String data) {
        return Math.abs(data.hashCode());
    }

    /**
     * hash算法2
     */
    private int hash2(String data) {
        int seek = 37;
        int hash = data.hashCode() + seek++;
        hash += (hash << 3);
        hash -= (hash >> 7);
        hash ^= (hash << 11);
        return Math.abs(hash);
    }
}
