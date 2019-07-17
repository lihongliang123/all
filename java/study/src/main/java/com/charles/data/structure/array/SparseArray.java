package com.charles.data.structure.array;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 稀疏数组 :
 * 当一个数组中大部分元素为0或者为同一个值的数组,可以使用稀疏数组来保存该数组
 * <p>
 * 处理方法 :
 * 1, 记录数组一共有几行几列,有多少个不同的值
 * 2, 把具有不同值的元素的行列以及值记录在一个小规模的数组(稀疏数组)中,从而缩小程序的规模
 * <p>
 * 二维数组转稀疏数组 :
 * 1, 遍历原始二维数组,得到有效数据(小部分不同的值)的个数, 设为sum;
 * 2, 根据 sum 创建稀疏数组 array int[sum + 1][3];
 * 3, 将二维数组的有效数据存入到稀疏数组
 * <p>
 * 稀疏数组转二维数组 :
 * 1, 读取稀疏数组的第一行,根据第一行的数据,创建原始的二维数组
 * 2, 再读取稀疏数组的后面所有数据,并赋予原始数组的指定位置
 *
 * @author CharlesLee
 */
public class SparseArray {

    private SparseArray() {

    }

    /**
     * 将任意给定的二维数组转行成为稀疏数组,该方法会自动判断最大的需要转换的对象
     * <p>
     * 该方法返回的结果为object的稀疏数组对象,二维稀疏数组中第一组数据记录了二维数组的大小,以及有效的数据个数,其余组记录了有效数据
     *
     * @param ordinaryArrays 需要转换的数组
     * @param <T>            任意类型
     * @return 返回转换成功的稀疏数组对象
     */
    public static <T> Object[][] twoDimensionalArrayToSparseArray(T[][] ordinaryArrays) {
        Map<T, Integer> cache = new HashMap<>(16);
        for (T[] ordinaryArray : ordinaryArrays) {
            for (T value : ordinaryArray) {
                Integer v = cache.get(value);
                if (v == null || v == 0) {
                    cache.put(value, 1);
                } else {
                    cache.put(value, v + 1);
                }
            }
        }
        int max = 0;
        T sparseValue = null;
        for (Map.Entry<T, Integer> tIntegerEntry : cache.entrySet()) {
            if (tIntegerEntry.getValue() > max) {
                max = tIntegerEntry.getValue();
                sparseValue = tIntegerEntry.getKey();
            }
        }

        int count = 0;
        // 获取有效的数据
        for (T[] ordinaryArray : ordinaryArrays) {
            for (T value : ordinaryArray) {
                if (sparseValue != value) {
                    count++;
                }
            }
        }

        Object[][] sparseArray = (Object[][]) Array.newInstance(Object.class, count + 1, 3);
        sparseArray[0][0] = ordinaryArrays.length;
        sparseArray[0][1] = ordinaryArrays[0].length;
        sparseArray[0][2] = sparseValue;
        int c = 0;
        for (int i = 0; i < ordinaryArrays.length; i++) {
            for (int j = 0; j < ordinaryArrays[i].length; j++) {
                if (ordinaryArrays[i][j] != sparseValue) {
                    c++;
                    sparseArray[c][0] = i;
                    sparseArray[c][1] = j;
                    sparseArray[c][2] = ordinaryArrays[i][j];
                }
            }
        }
        return sparseArray;
    }


    /**
     * 将稀疏数组转换为普通的二维数组
     * 需要注意的是稀疏数组的第一组数据保存的是原数据的大小信息
     */
    @SuppressWarnings("unchecked")
    public static <T> T[][] sparseArrayToTwoDimensionalArray(Object[][] arrays, Class<T> clazz) {
        T[][] ordinaryArrays = (T[][]) Array.newInstance(clazz, (int) arrays[0][0], (int) arrays[0][1]);
        for (int i = 0; i < ordinaryArrays.length; i++) {
            for (int j = 0; j < ordinaryArrays[i].length; j++) {
                ordinaryArrays[i][j] = (T) arrays[0][2];
            }
        }

        for (int i = 1; i < arrays.length; i++) {
            Object[] array = arrays[i];
            ordinaryArrays[(int) array[0]][(int) array[1]] = (T) array[2];
        }
        return ordinaryArrays;
    }
}
