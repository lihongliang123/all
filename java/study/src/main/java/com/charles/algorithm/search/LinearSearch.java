package com.charles.algorithm.search;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 线性查找算法,就是遍历逐一比对
 *
 * @author CharlesLee
 */
public class LinearSearch implements Search<int[], Integer, Integer> {


    @Override
    public Integer search(int[] arrays, Integer v) {
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i] == v) {
                return i;
            }
        }
        return null;
    }
}
