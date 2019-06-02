package top.hiccup.algorithm;

import java.util.Arrays;

/**
 * 测试
 *
 * @author wenhy
 * @date 2019/6/2
 */
public class Test {
    private void swap(int[] arr, int s, int t) {
        int tmp = arr[s];
        arr[s] = arr[t];
        arr[t] = tmp;
    }

    public void bubble(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    public void select(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int minIdx = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                swap(arr, minIdx, i);
            }
        }
    }

    public void insert(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int tmp = arr[i];
            int j = i;
            for (; j > 0; j--) {
                if (arr[j - 1] > tmp) {
                    arr[j] = arr[j - 1];
                } else {
                    break;
                }
            }
            arr[j] = tmp;
        }
    }


    @org.junit.Test
    public void test() {
        int[] arr = new int[]{5, 2, 12, 7, 9};
//        bubble(arr);
        select(arr);
//        insert(arr);
        System.out.println(Arrays.toString(arr));
    }
}
