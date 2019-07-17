package com.charles.algorithm.sort;


/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 选择排序: 内部排序法,从欲排序的数据中,按指定的规则选择某一个元素,再依规定交换位置后达到排序的目的
 *
 * @author CharlesLee
 */
public class SelectSort implements SortAlgorithm<int[]> {


    @Override
    public void sort(int[] arrays) {
        for (int i = 0; i < arrays.length - 1; i++) {
            // 假设当前位置为最小
            int minIndex = i;
            int min = arrays[i];
            for (int j = i + 1; j < arrays.length; j++) {
                if (min > arrays[j]) {
                    min = arrays[j];
                    minIndex = j;
                }
            }
            // 到了这里，就代表minIndex代表最小值所在下标, min代表最小值, 所以将找到的具体位置和假设位置想交换即可
            if (minIndex != i) {
                // 这里将假设位置的最小值,设置为真的位置最小值
                arrays[minIndex] = arrays[i];
                // 这里将真的位置最小值,设置为假设位置最小值
                arrays[i] = min;
            }
        }
    }
}
