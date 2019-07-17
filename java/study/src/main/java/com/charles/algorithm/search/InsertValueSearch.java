package com.charles.algorithm.search;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 插值查找算法: 数组必须是有序的
 * <p>
 * 该数组的排序方式为从小到大
 *
 * @author CharlesLee
 */
public class InsertValueSearch implements Search<int[], Integer, Integer> {
    @Override
    public Integer search(int[] arrays, Integer v) {
        return insertValueSearch(arrays, 0, arrays.length - 1, v);
    }

    /**
     * @param arr   需要查找的数组
     * @param left  左边开始查找的索引
     * @param right 右边结束查找的索引
     * @param find  需要查找的对象的下标
     * @return 返回查询到的下标
     */
    private Integer insertValueSearch(int[] arr, int left, int right, int find) {
        // 先决条件,arr是一个从小到大排列的有序数组
        // 这里做判断,如果开始下标大于了结束下标,或者需要查找的数小于了数组的第一个数字,或者需要查找的数字大于数组的最后一个数字,就说明该数组中没有需要查找的值
        if (left > right || find < arr[0] || find > arr[arr.length - 1]) {
            return null;
        }
        // 根据插值算法求出mid,这里需要保证需要查找的值处理整个数组的区间,否则该计算方式可能造成下标越界异常
        int mid = left + (right - left) * (find - arr[left]) / (arr[right] - arr[left]);
        int midVal = arr[mid];
        // 向右递归
        if (find > midVal) {
            return insertValueSearch(arr, mid + 1, right, find);
        } else if (find < midVal) {
            // 向左递归
            return insertValueSearch(arr, left, mid - 1, find);
        } else {
            return mid;
        }
    }
}
