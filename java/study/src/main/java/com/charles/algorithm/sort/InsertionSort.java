package com.charles.algorithm.sort;

import java.util.Arrays;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 插入排序: 是对欲排序的元素以插入的方式寻找该元素的适当位置,以达到排序的目的
 *
 * @author CharlesLee
 */
public class InsertionSort implements SortAlgorithm<int[]> {

    @Override
    public void sort(int[] arrays) {
        for (int i = 1; i < arrays.length; i++) {
            // 定义一个待插入的数字
            int insertVal = arrays[i];
            // 取的待插入数字的下标的前一个下标
            int insertIndex = i - 1;

            while (insertIndex >= 0 && insertVal < arrays[insertIndex]) {
                arrays[insertIndex + 1] = arrays[insertIndex];
                insertIndex--;
            }

            // 当退出循环的时候,说明需要插入的位置已经找到了
            arrays[insertIndex + 1] = insertVal;
        }
    }
}
