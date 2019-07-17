package com.charles.algorithm.recursion;

import java.util.Arrays;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 八皇后问题
 * <p>
 * 该问题是一个古老而著名的问题,是回溯算法的典型案例,该问题是由国际西洋棋手马克思,贝瑟尔与1848年提出,该问题为
 * 在8 X 8 的国际象棋上摆八个皇后,使其不能相互攻击, 即,任意两个皇后不能处于同一行,同一列或同一斜线上,问一共有多少种摆法.
 *
 * @author CharlesLee
 */
public class EightQueensProblem {

    /**
     * 定义max表示一共有多少皇后
     */
    private int max;

    /**
     * 定义数组array,存放皇后所在的位置
     */
    private int[] array;

    public EightQueensProblem(int max) {
        this.max = max;
        this.array = new int[max];
    }

    public static void main(String[] args) {
        EightQueensProblem e = new EightQueensProblem(8);
        e.addQueens(0);
    }

    /**
     * 添加皇后
     */
    public void addQueens(int n) {
        // 因为n是0开始的, 当n等于max的时候,实际放置的是第 max + 1 个皇后,所以需要停止
        if (n == max) {
            print();
            return;
        }
        // 依次放置,冲突判断
        for (int i = 0; i < max; i++) {
            // 放置的位置是从0开始,所以
            array[n] = i;
            // 好,皇后已经摆放完毕,那么验证是否冲突
            if (checkIndexIllegal(n)) {
                // 如果不冲突,那么就接着放下一行的皇后,也就是n+1个
                addQueens(n + 1);
            }
            // 如果冲突,那么数组的当前列的坐标就会依次往后移动,直到移动到一个不冲突的位置
            // 就是说当 array[n] = i 冲突后,那么进入下一个循环,会去判断 array[n] = n + 1 的位置是否冲突, 直到最后一列 max - 1
        }
    }

    /**
     * 检测放置的第n个皇后是否非法
     *
     * @param n 因为是数组,所以n的开始是从0,而不是1
     * @return 当返回true的时候, 代表不冲突, 否则代表冲突
     */
    public boolean checkIndexIllegal(int n) {
        for (int i = 0; i < n; i++) {
            // 如果是在同一列
            if (array[i] == array[n]) {
                return false;
            }

            // 表示第n-1个皇后和 第 array[i] 是否是在同一斜线, 判断同一斜线,那么只有 n - i == 1 的时候才会触发同一斜线判断
            if (Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }

            // 是否在同一行判断,不需要,数组的下标就已经定死了不可能在同一行
        }
        return true;
    }

    /**
     * 将所有的摆放位置打印出来
     */
    public void print() {
        System.out.println(Arrays.toString(array));
    }
}
