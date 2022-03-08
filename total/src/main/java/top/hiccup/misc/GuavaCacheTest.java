package top.hiccup.misc;

/**
 * 内存缓存guava-cache:
 *
 * @author wenhy
 * @date 2019/4/15
 */
public class GuavaCacheTest {

    public static void main(String[] args) {

        int[] arr = {6, 7, 0, 54, 0, 6, 0, 23, 123};
        int p = 0;
        for (int i = 0; i < arr.length; i++) {
            while (arr[i] == 0 && i < arr.length) {
                i++;
            }
            arr[p] = arr[i];
            p++;
        }
        while (p < arr.length) {
            arr[p] = 0;
            p++;
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

}
