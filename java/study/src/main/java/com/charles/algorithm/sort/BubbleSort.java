package com.charles.algorithm.sort;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 冒泡排序算法
 * <p>
 * 基本思想:对待排序序列从前向后(从下标较小的元素开始),依次比较相邻元素的值,若发现逆序则交换,数值较大的元素逐渐从前向后移动,就像水下面的气泡一样往上冒
 * <p>
 * 简单的优化策略
 * 因为排序的过程中,各元素不断的接近自己的位置,如果一趟比较下来从来没有进行过交换,就说明序列有序,因此需要设置一个标志判断元素是否进行过交换,从而减少不必要的比较
 *
 * @author CharlesLee
 */
public class BubbleSort implements SortAlgorithm<int[]> {

    @Override
    public void sort(int[] arrays) {
        int temp;
        boolean flag = false;
        for (int i = 0; i < arrays.length - 1; i++) {
            for (int j = 0; j < arrays.length - 1 - i; j++) {
                if (arrays[j] > arrays[j + 1]) {
                    flag = true;
                    temp = arrays[j];
                    arrays[j] = arrays[j + 1];
                    arrays[j + 1] = temp;
                }
            }
            if (!flag) {
                return;
            } else {
                flag = false;
            }
        }
    }
}
