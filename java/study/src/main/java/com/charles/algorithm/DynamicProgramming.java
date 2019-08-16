package com.charles.algorithm;

import java.util.Arrays;

/**
 * Charles Lee original, reprint please indicate the source
 * 动态规划算法,来处理背包问题
 *
 * @author CharlesLee
 */
public class DynamicProgramming {
    public static void main(String[] args) {
        // 记录物品的重量
        int[] itemWeight = {1, 4, 3};

        // 记录物品的价值
        int[] price = {1500, 3000, 2000};

        // 记录背包的最大重量
        int packet = 4;

        // 记录物品的总个数
        int num = itemWeight.length;

        // 表示在前i个物品中能够装入容量为j的背包中的最大价值
        int[][] v = new int[num + 1][packet + 1];

        // 动态规划处理
        // 这里不处理第一行与第一列的数据
        for (int i = 1; i < v.length; i++) {
            for (int j = 1; j < v[i].length; j++) {
                // 通过公式的运用
                if (itemWeight[i - 1] > j) {
                    v[i][j] = v[i - 1][j];
                } else {
                    v[i][j] = Math.max(v[i - 1][j], price[i - 1] + v[i - 1][j - itemWeight[i - 1]]);
                }
            }
        }

        System.out.println(Arrays.deepToString(v));
    }
}
