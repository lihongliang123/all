package com.charles.algorithm.search;

import java.util.Arrays;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 斐波那契(黄金分割法)查找算法
 *
 * @author CharlesLee
 */
public class FibolacciSearch implements Search<int[], Integer, Integer> {

    /**
     * 基本类型可以计算的最大数字就为92位的数组长度
     */
    private static final long[] FIBOLACCI = new long[92];

    static {
        // 初始化斐波拉契数列,计算机可以正确计算的最大序列
        FIBOLACCI[0] = 1;
        FIBOLACCI[1] = 1;
        for (int i = 2; i < FIBOLACCI.length; i++) {
            FIBOLACCI[i] = FIBOLACCI[i - 1] + FIBOLACCI[i - 2];
        }
    }

    @Override
    public Integer search(int[] arrays, Integer v) {
        // 需要查找的数组的左边开始位置下标
        int low = 0;
        // 需要查找的数组的右边位置结束下标
        int high = arrays.length - 1;

        // 斐波拉契的分割下标
        int k = 0;
        // 根据需要查找的数组长度减一 记为a,在斐波拉契数列中找到大于a的最小的一个数减一,则为需要生成的新的数组的长度
        while (high > FIBOLACCI[k] - 1) {
            k++;
        }
        /*
         如果原数组的长度小于需要指定长度数组的长度,那么将原数组拷贝到新的数组,空出来的位置补原数组最后一个数字
         即原数组为 {1,2,3,4,5,6}; 新的数组为{1,2,3,4,5,6,6,6}
        */
        if (arrays.length < FIBOLACCI[k]) {
            // 这里生成的数据末尾是0
            int[] temp = Arrays.copyOf(arrays, (int) FIBOLACCI[k]);
            for (int i = arrays.length; i < temp.length; i++) {
                if (temp[i] == 0) {
                    temp[i] = arrays[high];
                }
            }
            arrays = temp;
        }

        int mid;

        // 用循环来处理整个数组
        while (low <= high) {
            // 查找判断的坐标点,因为经过前面的计算,需要查找数据的数组已经变成了长度为 FIBOLACCI[k] 的长度了
            mid = (int) (low + FIBOLACCI[k - 1] - 1);
            // 如果我们需要查找的值小于给定的下标值,那么就需要继续往小端查找
            if (v < arrays[mid]) {
                // 将坐标移动至mid的左边一位,后面的部分不用比较了
                high = mid - 1;
                // 根据斐波拉契数列的长度进行拆分,下一次循环只查询FIBOLACCI[k-1]长度的数组
                k--;
            } else if (v > arrays[mid]) {
                // 将左边的下标移动至mid的后一位,前面的部分已经不用查找了
                low = mid + 1;
                // 根据斐波拉契数列的长度进行拆分,下一次循环只查询FIBOLACCI[k-2]长度的数组,因为FIBOLACCI[k-1]长度的元素已经在上一个if条件中跳过了
                k -= 2;
            } else {
                // 这里需要确定下返回哪个下标
                return mid <= high ? mid : high;
            }
        }
        return null;
    }
}
