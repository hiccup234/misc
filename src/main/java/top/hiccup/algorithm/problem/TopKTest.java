package top.hiccup.algorithm.problem;

import java.util.Arrays;

/**
 * 面试常见的TopK问题:
 * 
 * 1.直接排序 O(n*lg(n))
 * 几种排序法都可以（一般用快排），排序后直接取前k个元素即是TopK
 * 
 * 2.局部排序 O(n*k)
 * 只对最大（小）的k个元素排序，用冒泡的思想，冒出前k个元素（也可用选择）
 * 
 * 3.堆 O(n*lg(k))
 * n个元素扫一遍，假设运气很差，每次都入堆调整，调整时间复杂度为堆的高度，即lg(k)，故整体时间复杂度是n*lg(k)
 * 堆是求topk的经典算法，利用最小堆所有父节点比其子节点小的特性，节省了排序的计算
 * 
 * 4.随机选择 时间复杂度约为 O(n) [最差的情况，每次左边都取到1个 k*n]
 *  a.分治法：将大的问题转化为若干个子问题，再分而治之解决每个子问题，则大问题就解决了  --  例如常见的快排
 *  b.减治法：将大的问题转化为若干个子问题，然后只解决其中一个子问题，大问题便随之解决  --  例如常见的二分查找
 *      由快排里的partition可以很容易的联想到减治法来实现topk的思想
 *      i = partition(arr, 1, n); 如果i大于k，则说明topk全在arr[i]左边（降序），
 *      如果i小于k则说明，有一部分top(k-i)在arr[i]右边，递归找到这部分即可。
 *   减治法遇到大数据量时可能内存不够，可以考虑多台分布式计算，各取topK再合起来计算，而堆不受内存大小限制
 * 
 * 5.bitmap位图法（受限于数组值域范围以及是否有重复值，换种思路，有时候就是要求不包括重复值）
 * 假设topk的问题是对整型计算，值域为[-2^31~2^31-1]共计需要4G个bit
 * 在数据量很小且取值范围很大的情况下，bitmap很难体现出优势来
 * 
 * 6.bitmap位图法改进（允许有重复值）
 * 相对于5中的用bit记录数组元素是否存在，这里换成int或者视可能出现的重复个数来缓存，这里程序不再实现
 *
 * @author wenhy
 * @date 2018/9/21
 */
public class TopKTest {

    /**
     * 直接排序法
     */
    private static int[] test1(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k == 0) {
            return null;
        }
        int[] topk = new int[k];
        Arrays.sort(arr);
        System.arraycopy(arr, arr.length - k, topk, 0, k);
        return topk;
    }

    /**
     * 局部排序法
     */
    private static int[] test2(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k == 0) {
            return null;
        }
        int[] topk = new int[k];
        // 向后冒泡
        int temp;
        for (int i = 0; i < k && i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.arraycopy(arr, arr.length - k, topk, 0, k);
        // 选择排序
//        int max, temp;
//        for (int i = 0; i < k; i++) {
//            max = i;
//            for (int j = i + 1; j < arr.length; j++) {
//                if (arr[max] < arr[j]) {
//                    max = j;
//                }
//            }
//            if (max != i) {
//                temp = arr[max];
//                arr[max] = arr[i];
//                arr[i] = temp;
//            }
//        }
//        System.arraycopy(arr, 0, topk, 0, k);
        return topk;
    }

    private static void makeHeap(int[] topk) {
        // 将这k个元素转换为一颗最小堆（完全二叉树），则k/2 - 1为最后一个非叶子节点
        for (int i = topk.length / 2 - 1; i >= 0; i--) {
            // 遍历每个非叶子节点
            adjustHeap(topk, i);
        }
    }

    private static void adjustHeap(int[] topk, int i) {
        int leftChild = (i + 1) * 2 - 1;
        int rightChild = (i + 1) * 2;
        int minIndex = i;
        // 如果存在左子节点，且左子节点的值小于父节点
        if (leftChild < topk.length && topk[leftChild] < topk[minIndex]) {
            minIndex = leftChild;
        }
        // 如果存在右子节点，且右子节点的值小于父节点
        if (rightChild < topk.length && topk[rightChild] < topk[minIndex]) {
            minIndex = rightChild;
        }
        // 如果左右结点的值都大于父节点，则直接return
        if (i == minIndex) {
            return;
        }
        // 交换父节点和左右结点中最小的那个值，把父节点的值替换下去
        int tmp = topk[i];
        topk[i] = topk[minIndex];
        topk[minIndex] = tmp;
        // TODO 由于替换后左右子树的最小堆特性可能会受到影响，所以要对子树再进行heapify，一定要记得
        // TODO 可以考虑把递归优化为循环遍历
        adjustHeap(topk, minIndex);

    }

    /**
     * 最小堆法
     */
    static int[] test3(int[] arr, int k) {
        int[] topk = new int[k];
        System.arraycopy(arr, 0, topk, 0, k);
        // 创建最小堆
        makeHeap(topk);
        // 创建完最小堆后topk[0]就是堆中最小的元素
        for (int i = k; i < arr.length; i++) {
            if (arr[i] > topk[0]) {
                topk[0] = arr[i];
                adjustHeap(topk, 0);
            }
        }

        return topk;
    }


    private static int partition(int arr[], int left, int right) {
        int i = left, j = right;
        // 默认取最左边的元素为分割的基准元素
        int partition = arr[left];
        int temp;
        while (i < j) {
            // 降序，大的元素放前面
            while (i < j && partition >= arr[j]) {
                j--;
            }
            while (i < j && partition <= arr[i]) {
                i++;
            }
            // 交换arr[i]与arr[j]
            if (i < j) {
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        arr[left] = arr[i];
        arr[i] = partition;
        return i;
    }

    private static int nest(int[] arr, int left, int right, int k) {
        if (left >= right) {
            return arr[left];
        }
        int i = partition(arr, left, right);
        // 这里有个前提条件，从数组下标0开始计算topk
        if (i - left >= k) {
            return nest(arr, left, i - 1, k);
        } else {
            // 后半段只取k-i个，前i个都是属于topk的元素
            return nest(arr, i + 1, right, k - i);
        }
    }

    /**
     * 减治法
     */
    private static int[] test4(int[] arr, int k) {
        nest(arr, 0, arr.length - 1, k);
        int[] topk = new int[k];
        System.arraycopy(arr, 0, topk, 0, k);
        return topk;
    }

    /**
     * bitmap法
     */
    private static int[] test5(int[] arr, int k) {
        // 先跟据数组元素的值域范围申请相应的内存空间做缓存，一个byte有8bit
        // TODO Java没有可以直接操纵bit的方法，如果内存限制不大，这里换成直接操作byte是不是更方便快捷
        byte[] cache = new byte[2 << 29];
        for (int i = 0; i < arr.length; i++) {
            int index = arr[i] >> 3;
            byte b = cache[index];
            // 对arr[i]取8的余数
            int t = arr[i] & (8 - 1);
            b |= 1 << t;
            cache[index] = b;
        }
        int[] topk = new int[k];
        int count = 0;
//        for (int i=2<<29-1; i>=0 && count<k; i--) {
        for (int i = 2 << 12 - 1; i >= 0 && count < k; i--) {
            byte b = cache[i];
            for (int j = 7; j >= 0 && count < k; j--) {
                int offset = 1 << j;
                if ((b & offset) == offset) {
                    topk[count] = (i << 3) + j;
                    count++;
                }
            }
        }
        return topk;
    }

    public static void main(String[] args) {
        int[] arr = {9, 7, 2, 5, 8, 4, 3, 7, 3, 9};
        int[] data = {9, 275, 12, 6, 45, 999, 41, 12306, 456, 12, 532, 45};
        int[] topk;
//        topk = test1(arr, 3);
//        topk = test2(arr, 4);
//        topk = test3(data, 5);
//        topk = test4(arr, 4);
        topk = test5(data, 4);
        Arrays.stream(topk).forEach(System.out::println);
    }

}
