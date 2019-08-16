package com.charles.algorithm;

/**
 * Charles Lee original, reprint please indicate the source
 * 分治算法解决汉罗诺塔问题
 *
 * @author CharlesLee
 */
public class DivideAndConquerHanoi {

    public static void main(String[] args) {
        new DivideAndConquerHanoi().hanoi(5, "A", "B", "C");
    }


    private void hanoi(int num, String a, String b, String c) {

        if (num == 1) {
            System.out.printf("1盘 %s -> %s \n", a, c);
        } else {

            // 如果在num大于等于2的情况下,那么可以看作总数为2,分别为上下两部分,下部分为最底层的一个为1,其余剩下的为1
            hanoi(num - 1, a, c, b);
            System.out.printf("%d盘 %s -> %s \n", num, a, c);
            hanoi(num - 1, b, a, c);
        }
    }
}
