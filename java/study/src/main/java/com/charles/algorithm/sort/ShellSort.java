package com.charles.algorithm.sort;

import java.util.Arrays;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 希尔排序: 它是一种插入排序,它是简单插入排序经过改进之后的一个更高效的版本,也被称之为缩小增量排序
 *
 * @author CharlesLee
 */
public class ShellSort implements SortAlgorithm<int[]> {


    @Override
    public void sort(int[] array) {


        int temp;

//        // 交换法,速度很慢
//        // 将所有数据对半分组,进行遍历,每一轮都在上一次的基础上继续对半分
//        for (int i = array.length / 2; i > 0; i /= 2) {
//            // 遍历各组元素
//            for (int j = i; j < array.length; j++) {
//                // 遍历各组中的元素,(一共有i组, 每组的元素为 array.length 除以 i 个, 如果有剩余不会被纳入分组), 步长为i
//                for (int k = i - j; k >= 0; k -= i) {
//                    if (array[k] > array[k + i]) {
//                        temp = array[k];
//                        array[k] = array[k + i];
//                        array[k + i] = temp;
//                    }
//                }
//            }
//        }

        // 移动法,速度快
        for (int i = array.length / 2; i > 0; i /= 2) {
            for (int j = i; j < array.length; j++) {
                int index = j;
                int ins = array[index];
                if (array[index] < array[index - i]) {
                    while (index - i >= 0 && ins < array[index - i]) {
                        array[index] = array[index - i];
                        index -= i;
                    }
                    // while 退出后, 插入位置已经找到
                    array[index] = ins;
                }
            }
        }
    }
}
