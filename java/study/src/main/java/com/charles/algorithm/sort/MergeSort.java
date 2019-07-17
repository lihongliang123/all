package com.charles.algorithm.sort;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 归并排序: 利用归并的思想实现的排序方法,采用经典的分治策略(即将问题分为一些小的问题然后再递归求解,最后将得到的结果"修补"在一起)
 * <p>
 * // todo 理解不完全,需要继续
 *
 * @author CharlesLee
 */
public class MergeSort implements SortAlgorithm<int[]> {
    @Override
    public void sort(int[] arrays) {
        sort(arrays, 0, arrays.length - 1, new int[arrays.length]);
    }

    /**
     * 分治 + 合并
     *
     * @param arrays 需要排序的数组
     * @param left   左边有序序列的初始化索引
     * @param right  右边序列的索引
     * @param temp   需要额外开销的临时空间
     */
    private void sort(int[] arrays, int left, int right, int[] temp) {
        if (left < right) {
            // 找到拆分点
            int mid = (left + right) / 2;
            // 向左进行拆分
            sort(arrays, left, mid, temp);
            // 向右进行拆分
            sort(arrays, mid + 1, right, temp);

            // 合并
            merge(arrays, left, mid, right, temp);
        }
    }

    /**
     * @param arrays 需要排序的原始数组
     * @param left   左边有序序列的初始化索引
     * @param mid    寻找到的中间索引
     * @param right  右边序列索引
     * @param temp   需要额外开销的临时空间
     */
    private void merge(int[] arrays, int left, int mid, int right, int[] temp) {
        // 左边有序序列的初始化索引
        int i = left;
        // 右边有序序列的初始化索引
        int j = mid + 1;
        // 指向temp数组的当前索引,因为temp数组是需要进行填充的,该索引指向需要填充的位置
        int t = 0;

        //先把左右两边有序的数据按照规则填充到temp数组,直到左右两边的有序序列,有一边处理完毕为止
        while (i <= mid && j <= right) {
            // 如果左边的有序序列的当前元素小于等于右边有序序列的当前元素,那么就将左边的当前元素填充到temp数组
            if (arrays[i] <= arrays[j]) {
                temp[t] = arrays[i];
                i++;
                t++;
            } else {
                // 否则,就将右边的有序序列当前元素,填充到temp数组
                temp[t] = arrays[j];
                j++;
                t++;
            }

        }

        // 将把剩余数据的一边的数据全部填充到temp
        // 将左边的有序序列还剩余的元素,全部填充到temp
        while (i <= mid) {
            temp[t] = arrays[i];
            t++;
            i++;
        }

        // 将右边有序序列还剩余的元素,全填充到temp
        while (j <= right) {
            temp[t] = arrays[j];
            t++;
            j++;
        }

        // 将临时数组里面的元素拷贝会原始数组,需要注意的是并不是每次都拷贝所有
        // 索引归位,指向临时数组的开始位置
        t = 0;
        int tempLeft = left;
        while (tempLeft <= right) {
            arrays[tempLeft] = temp[t];
            tempLeft++;
            t++;
        }
    }
}
