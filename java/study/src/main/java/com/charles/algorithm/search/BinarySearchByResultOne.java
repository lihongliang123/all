package com.charles.algorithm.search;


/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 二分查找: 使用二分查找的前提是该数组必须是有序的,无序的数组不能用二分查找
 *
 * 该数组的排序方式为从小到大
 *
 * @author CharlesLee
 */
public class BinarySearchByResultOne implements Search<int[], Integer, Integer> {

    @Override
    public Integer search(int[] arrays, Integer v) {
        return binarySearch(arrays, 0, arrays.length - 1, v);
    }


    private Integer binarySearch(int[] arrays, int left, int right, int find) {
        if (left > right) {
            return null;
        }
        int mid = (left + right) / 2;
        int midValue = arrays[mid];

        // 向右递归
        if (find > midValue) {
            return binarySearch(arrays, mid + 1, right, find);
        } else if (find < midValue) {
            // 向左递归
            return binarySearch(arrays, left, mid - 1, find);
        } else {
            return mid;
        }
    }
}
