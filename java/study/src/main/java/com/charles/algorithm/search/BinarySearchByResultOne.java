package com.charles.algorithm.search;


/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 二分查找: 使用二分查找的前提是该数组必须是有序的,无序的数组不能用二分查找
 * <p>
 * 该数组的排序方式为从小到大
 *
 * @author CharlesLee
 */
public class BinarySearchByResultOne implements Search<int[], Integer, Integer> {

    @Override
    public Integer search(int[] arrays, Integer v) {
        return binarySearch(arrays, 0, arrays.length - 1, v);
    }


    /**
     * 递归实现的二分查找算法
     *
     * @param arrays 需要查找的数组
     * @param left   左开始查找的位置
     * @param right  右结束查找的位置
     * @param find   需要查找的数字
     * @return 返回查找到的坐标
     */
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

    /**
     * 通过非递归的形式进行二分查找法
     *
     * @param arrays 需要查找的数组
     * @param find   需要查找的值
     * @return 返回查找到的下标
     */
    private Integer binarySearch(int[] arrays, int find) {
        if (arrays == null || arrays.length == 0) {
            return null;
        }
        int left = 0;
        int right = arrays.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arrays[mid] == find) {
                return mid;
                // 向左查找
            } else if (arrays[mid] > find) {
                right = mid - 1;
                // 向右查找
            } else if (arrays[mid] < find) {
                left = mid + 1;
            }

        }
        return null;
    }

    public static void main(String[] args) {
        int[] arrays = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println(new BinarySearchByResultOne().binarySearch(arrays, 8));
    }
}
