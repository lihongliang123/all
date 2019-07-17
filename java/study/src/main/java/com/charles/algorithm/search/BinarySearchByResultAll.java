package com.charles.algorithm.search;


import java.util.ArrayList;
import java.util.List;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 二分查找: 使用二分查找的前提是该数组必须是有序的,无序的数组不能用二分查找
 * <p>
 * 该数组的排序方式为从小到大
 *
 * @author CharlesLee
 */
public class BinarySearchByResultAll implements Search<int[], Integer, List<Integer>> {

    @Override
    public List<Integer> search(int[] arrays, Integer v) {
        return binarySearch(arrays, 0, arrays.length - 1, v, new ArrayList<>());
    }


    private List<Integer> binarySearch(int[] arrays, int left, int right, int find, List<Integer> list) {
        if (left > right) {
            return list;
        }
        int mid = (left + right) / 2;
        int midValue = arrays[mid];

        // 向右递归
        if (find > midValue) {
            return binarySearch(arrays, mid + 1, right, find, list);
        } else if (find < midValue) {
            // 向左递归
            return binarySearch(arrays, left, mid - 1, find, list);
        } else {
            // 从左到右遍历整个已经做过拆分的数组,然后将所有的数据加入即可
            for (int i = left; i <= right; i++) {
                if (find == arrays[i]) {
                    list.add(i);
                }
            }
            return list;
        }
    }
}
