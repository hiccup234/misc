package top.hiccup.misc;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 内存缓存guava-cache:
 *
 *
 *
 * @author wenhy
 * @date 2019/4/15
 */
public class GuavaCacheTest {

    public static void main(String[] args) {
//        int m = 5;
//        int [][] crr = new int[5][3];
//        crr[1][0] = 1;
//        crr[4][0] = 6;
//
//        for (int i = 0; i < m; i++) {
//            System.out.print(crr[i][0] + " " + crr[i][1] + "" + crr[i][2]);
//            System.out.println("");
//        }
//
//        int p = (crr[0][0] == 0) ? 0 : 1;
//        for (int i = 0; i < m; i++) {
//            while(crr[i][0] == 0 && i < m) {
//                i++;
//            }
//            if (i >= m) {
//                break;
//            }
//            crr[p][0] = crr[i][0];
//            crr[p][1] = crr[i][1];
//            crr[p][2] = crr[i][2];
//            p++;
//        }
//        System.out.println("");
//        System.out.println("");
//        for (int i = 0; i < m; i++) {
//            System.out.print(crr[i][0] + " " + crr[i][1] + "" + crr[i][2]);
//            System.out.println("");
//        }

b
        int[] arr = {6,7, 0,54,0,6,0,23,123};
        int p = 0;
        for (int i = 0; i < arr.length; i++) {
            while(arr[i] == 0 && i < arr.length) {
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
