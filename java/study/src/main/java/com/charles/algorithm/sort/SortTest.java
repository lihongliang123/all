package com.charles.algorithm.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * Charles Lee original, reprint please indicate the source
 *
 * @author CharlesLee
 */
public class SortTest {

    public static void main(String[] args) {
        int[] array = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0,22,132,555,9875};
//        int[] array = new int[50000000];
//        for (int i = 0; i < array.length; i++) {
//            array[i] = new Random().nextInt(800000000);
//        }
        SortAlgorithm<int[]> sort = new RadixSort();
        long start = System.currentTimeMillis();
        sort.sort(array);
        System.out.println("耗时为：" + (System.currentTimeMillis() - start));



    }
}
