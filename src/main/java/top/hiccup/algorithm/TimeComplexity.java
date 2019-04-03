package top.hiccup.algorithm;

import top.hiccup.algorithm.sort.QuickSort;

/**
 * 时间复杂度计算
 *
 * @author wenhy
 * @date 2019/4/3
 * @see https://mp.weixin.qq.com/s/yfzrFYn0Dogy0HkN5XAS0Q
 * 
 * 【简单规则】
 * 
 * 规则一：“有限次操作”的时间复杂度往往是O(1)
 * 
 * 规则二：“for循环”的时间复杂度往往是O(n)
 * 
 * 规则三：“树的高度”的时间复杂度往往是O(lg(n)) （树的总节点个数是n，则树的高度是lg(n)）
 * 
 * 【递归求解】
 * 案例一：计算 1到n的和，时间复杂度分析。
 * 如果用非递归的算法：
 * int sum(int n){
 * int result=0;
 * for(int i=0;i<n;i++)
 * result += i;
 * return result;
 * }
 * 根据简单规则，for循环，sum的时间复杂度是O(n)。
 * 但如果是递归算法，就没有这么直观了：
 * int sum(int n){
 * if (n==1) return 1;
 * return n+sum(n-1);
 * }
 * 如何来进行时间复杂度分析呢？
 * 用f(n)来表示数据量为n时，算法的计算次数，很容易知道：
 * 当n=1时，sum函数只计算1次
 * 画外音：if (n==1) return 1;
 * 即：
 * f(1)=1【式子A】
 * 不难发现，当n不等于1时：
 * f(n)的计算次数，等于f(n-1)的计算次数，再加1次计算
 * 画外音：return n+sum(n-1);
 * 即：
 * f(n)=f(n-1)+1【式子B】
 * 【式子B】不断的展开，再配合【式子A】：
 * 画外音：这一句话，是分析这个算法的关键。
 * f(n)=f(n-1)+1
 * f(n-1)=f(n-2)+1
 * …
 * f(2)=f(1)+1
 * f(1)=1
 * 上面共n个等式，左侧和右侧分别相加：
 * f(n)+f(n-1)+…+f(2)+f(1)
 * =
 * [f(n-1)+1]+[f(n-2)+1]+…+[f(1)+1]+[1]
 * 即得到：
 * f(n)=n
 * 已经有那么点意思了哈，再来个复杂点的算法，见如下
 * 
 * @author wenhy
 * @date 2019/4/3
 */
public class TimeComplexity {

    /**
     * 案例二：二分查找binary_search，时间复杂度分析。
     * 二分查找，单纯从递归算法来分析，怎能知道其时间复杂度是O(lg(n))呢？
     * 仍用f(n)来表示数据量为n时，算法的计算次数，很容易知道：
     * 当n=1时，bs函数只计算1次
     * 画外音：不用纠结是1次还是1.5次，还是2.7次，是一个常数次。
     * 即：
     * f(1)=1【式子A】
     * 在n很大时，二分会进行一次比较，然后进行左侧或者右侧的递归，以减少一半的数据量：
     * f(n)的计算次数，等于f(n/2)的计算次数，再加1次计算
     * 画外音：计算arr[mid]>target，再减少一半数据量迭代
     * 即：
     * f(n)=f(n/2)+1【式子B】
     * 【式子B】不断的展开，
     * f(n)=f(n/2)+1
     * f(n/2)=f(n/4)+1
     * f(n/4)=f(n/8)+1
     * …
     * f(n/2^(m-1))=f(n/2^m)+1
     * 上面共m个等式，左侧和右侧分别相加：
     * f(n)+f(n/2)+…+f(n/2^(m-1))
     * =
     * [f(n/2)+1]+[f(n/4)+1]+…+[f(n/2^m)]+[1]
     * 即得到：
     * f(n)=f(n/2^m)+m
     * 再配合【式子A】：
     * f(1)=1
     * 即，n/2^m=1时, f(n/2^m)=1, 此时m=lg(n), 这一步，这是分析这个算法的关键。
     * 将m=lg(n)带入，得到：
     * f(n)=1+lg(n)
     */
    public int bs(int[] arr, int left, int right, int target) {
        if (left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        if (arr[mid] == target) {
            return mid;
        }
        if (arr[mid] > target) {
            return bs(arr, left, mid - 1, target);
        } else {
            return bs(arr, mid + 1, right, target);
        }
    }

    /**
     * 案例三：快速排序quick_sort，时间复杂度分析。
     * 仍用f(n)来表示数据量为n时，算法的计算次数，很容易知道：
     * 当n=1时，quick_sort函数只计算1次
     * f(1)=1【式子A】
     * 在n很大时：
     * 第一步，先做一次partition；
     * 第二步，左半区递归；
     * 第三步，右半区递归；
     * 即：
     * f(n)=n+f(n/2)+f(n/2)=n+2*f(n/2)【式子B】
     * 画外音：
     * (1)partition本质是一个for，计算次数是n；
     * (2)二分查找只需要递归一个半区，而快速排序左半区和右半区都要递归，这一点在分治法与减治法一章节已经详细讲述过；
     * 【式子B】不断的展开，
     * f(n)=n+2*f(n/2)
     * f(n/2)=n/2+2*f(n/4)
     * f(n/4)=n/4+2*f(n/8)
     * …
     * f(n/2^(m-1))=n/2^(m-1)+2f(n/2^m)
     * 上面共m个等式，逐步带入，于是得到：
     * f(n)=n+2*f(n/2)
     * =n+2*[n/2+2*f(n/4)]=2n+4*f(n/4)
     * =2n+4*[n/4+2*f(n/8)]=3n+8f(n/8)
     * =…
     * =m*n+2^m*f(n/2^m)
     * 再配合【式子A】：
     * f(1)=1
     * 即，n/2^m=1时, f(n/2^m)=1, 此时m=lg(n), 这一步，这是分析这个算法的关键。
     * 将m=lg(n)带入，得到：
     * f(n)=lg(n)*n+2^(lg(n))*f(1)=n*lg(n)+n
     * 故，快速排序的时间复杂度是n*lg(n)。
     */
    public void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int i = QuickSort.partition(arr, left, right);
        quickSort(arr, left, i - 1);
        quickSort(arr, i + 1, right);
    }

    /**
     * 作业：使用随机选择randomized_select来找到n个数中第k大元素。
     * 问：randomized_select的时间复杂度是多少？
     * 答：f(n) = n + n/2 + n/4 + ... + 2 + 1 所以O(n) = n
     */
    public int rs(int[] arr, int left, int right, int k) {
        if (left >= right) {
            return arr[left];
        }
        int i = QuickSort.randPartition(arr, left, right);
        // 数组前半部分元素个数
        int cout = i - left;
        if (cout >= k) {
            // 前半部分第k大
            return rs(arr, left, i - 1, k);
        } else {
            // 后半部分第k-t大
            return rs(arr, i + 1, right, k - cout);
        }
    }
}
