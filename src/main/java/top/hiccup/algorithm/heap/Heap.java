package top.hiccup.algorithm.heap;

/**
 * 堆的定义：
 * 1) 堆是一个完全二叉树
 * 2) 堆中每个节点的值都必须大于等于（或小于等于）其子树中每个节点的值（等价于：其左右子节点的值）
 * 
 * 大顶堆：每个节点的值都大于等于左右子节点的值
 * 小顶堆：每个节点的值都小于等于其子树中每个节点的值
 * 
 * 堆一般用数组来存储，数组中下标i的节点的左子节点就是i*2，右子节点就是i*2+1，父节点就是i/2
 * 
 * 堆的操作：
 * 插入元素后，需要调整以满足堆的特性 -- 堆化（heapify），一般插入元素放在堆的最后一个位置，所以从下往上堆化。
 * 删除堆顶元素后，如果直接调整可能会造成数组空洞，所以一般都拿堆的最后一个元素放在堆顶，然后从上往下堆化。
 *
 * 堆的应用：
 * 1、优先级队列（如合并多个有序的小文件，采用min()则为O(n)，而采用堆的方式则为O(logn)，再如定时器，每次都调整离到期时间最近的到堆顶）
 * 2、求TopK问题
 * 3、利用堆求中位数问题（一个大顶堆一个小顶堆，小顶堆中的数据都大于大顶堆，则大顶堆的堆顶就是中位数，这样就满足动态数据求中位数，而不用每次排序再求中位数）
 *
 * @author wenhy
 * @date 2019/6/2
 */
public class Heap {

    /**
     * 存储完全二叉树的数组
     */
    private int[] table;

    /**
     * 保存当前堆的大小
     */
    private int size;

    public Heap(int capacity) {
        size = 0;
        // 一般为了方便计算，数组下标0不存放元素
        table = new int[capacity + 1];
    }


    public void add(int t) {
        if (size >= table.length - 1) {
            throw new RuntimeException("堆已满");
        }
        size++;
        table[size] = t;
        heapifyUp();
    }

    /**
     * 从下往上堆化
     */
    private void heapifyUp() {
        // table[size]为刚插入的元素，可能不满足堆的特性
        int i = size;
        // i/2为最后一个非叶子节点
        while (i / 2 > 0 && table[i] > table[i / 2]) {
            swap(table, i, i / 2);
            i = i / 2;
        }
    }

    public int removeTop() {
        if (size == 0) {
            throw new RuntimeException("堆已空");
        }
        int ret = table[1];
        // 取最后一个元素放至堆顶，来优化删除后的堆调整，防止数组空洞出现
        table[1] = table[size];
        size--;
        heapifyDown(1);
        return ret;
    }

    /**
     * 从上往下堆化
     */
    private void heapifyDown(int pos) {
        int i = pos;
        int n = table.length;
        while (true) {
            // 假定当前i为最大值
            int maxIdx = i;
            // 先跟节点i的左子节点比较，如果有的话
            if (i * 2 < n && table[i] < table[i * 2]) {
                maxIdx = i * 2;
            }
            // 再用i的右子节点跟父节点和左子节点中最大的值比较，如果有的话
            if (i * 2 + 1 < n && table[maxIdx] < table[i * 2 + 1]) {
                maxIdx = i * 2 + 1;
            }
            // 如果父节点i已经是最大的了，则满足堆的特性，堆化调整结束
            if (maxIdx == i) {
                break;
            }
            // 否则交换，然后继续调整左子节点或者右子节点
            swap(table, i, maxIdx);
            i = maxIdx;
        }
    }

    public static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
