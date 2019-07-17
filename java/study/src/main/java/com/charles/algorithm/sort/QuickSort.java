package com.charles.algorithm.sort;


/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 快速排序,是对冒泡排序的一种改进
 * <p>
 * 通过一趟排序将要排序的数据分割成为两个独立的部分,其中一部分的所有数据都比另外一部分的所有数据要小
 * 然后在按照此方法对这两部分数据分别进行快速排序,整个排序过程可以递归,以此达到对整个数据变成有序序列
 *
 * @author CharlesLee
 */
public class QuickSort implements SortAlgorithm<int[]> {

    @Override
    public void sort(int[] array) {
        sort(array, 0, array.length - 1);
    }

    private void sort(int[] array, int left, int right) {
        int l = left;
        int r = right;

        int temp;

        int pivot = array[(left + right) / 2];
        // while循环的目的是让 比pivot值小的放左边
        while (l < r) {
            // 在pivot的左边一直找,找到大于pivot的值,才退出
            while (array[l] < pivot) {
                l += 1;
            }
            // 在pivot的右边一直找,找到小于pivot的值,才退出
            while (array[r] > pivot) {
                r -= 1;
            }

            // 如果 l >= r 说明pivot左右两边的值已经全部符合规范了, 左边全是小于等于pivot的值, 右边全是大于等于pivot的值
            if (l >= r) {
                break;
            }

            temp = array[l];
            array[l] = array[r];
            array[r] = temp;

            // 如果交换完毕,发现arr[l] == pivot 值相等 r-- 前移;
            if (array[l] == pivot) {
                r -= 1;
            }

            // 如果交换完毕,发现arr[r] == pivot 值相等 l-- 前移;
            if (array[r] == pivot) {
                l += 1;
            }
        }
        //
        if (l == r) {
            l++;
            r--;
        }
        if (left < r) {
            sort(array, left, r);
        }

        if (right > l) {
            sort(array, l, right);
        }
    }
}
