package com.charles.algorithm.sort;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 基数排序:(典型的空间换时间)
 * 1, 属于"分配式排序",又称"桶子法",它是通过键值的各个位的值,将要排序的元素分配至某些"桶"中,以达到排序的目的
 * 2, 属于稳定性排序,效率非常高的稳定性排序法
 * 3, 基数排序是基于桶排序的扩展
 * 4, 实现方式为: 将整数按位数切割成不同的数字,然后按每个位数分别比较
 * <p>
 * 将所有待比较的数值统一为同样的数位长度,数位较短的数前面补零,然后从最低位开始,然后进行依次排序,这样从最低位排序一直到最高位排序以后,就变成了有序的
 * <p>
 * 基于传统的桶排序的扩展,速度很快
 * 采用空间换时间方式,占用内存大,对待海量数据的时候,容易照成 {@link OutOfMemoryError}
 * @author CharlesLee
 */
public class RadixSort implements SortAlgorithm<int[]> {


    @Override
    public void sort(int[] arrays) {

        // 定义一个二维数组,用来表示0-9的所有的桶
        int[][] bucket = new int[10][arrays.length];

        // 为了记录每个桶中,实际存放了多少个数据,需要定义一个一位数组来记录每个桶存放的数据的个数
        int[] bucketElementCount = new int[10];

        // 找到最大的数字
        int max = 0;
        // 首先找到数组中的最大数字
        for (int array : arrays) {
            if (array > max) {
                max = array;
            }
        }

        // 需要注意这里的i,这里的i就是元素的位数
        for (int i = 1; i <= getMaxLen(max); i++) {
            // 这里针对每一个元素的位置进行处理
            for (int j = 0; j < arrays.length; j++) {
                // 取出当前元素的i位数的值, 10的0次方是1, 10的1次方是10, 10的2次方是100...
                int digitOfElement = (int) (arrays[j] / (Math.pow(10, i - 1)) % 10);
                // 将元素放入对应的桶里面
                bucket[digitOfElement][bucketElementCount[digitOfElement]] = arrays[j];
                // 将下标索引加一
                bucketElementCount[digitOfElement]++;
            }

            // 将数据拉入原来的数组
            int index = 0;
            // 遍历所有的桶,将所有的桶的数据依次放入原来的数组
            for (int j = 0; j < bucket.length; j++) {
                // 判断桶里面是否有数据,因为放入数据的时候下标索引会增加的,所以有数据的数组这里不可能为0
                if (bucketElementCount[j] != 0) {
                    // 循环桶的数据,依次放入
                    for (int k = 0; k < bucketElementCount[j]; k++) {
                        arrays[index++] = bucket[j][k];
                    }
                }
                // 桶里面的数据不管,但是桶对应的下标清空
                bucketElementCount[j] = 0;
            }
        }
    }


    private int getMaxLen(int n) {
        int count = 0;
        while (Math.abs(n) % 10 > 0 || n / 10 != 0) {
            count++;
            n /= 10;
        }
        return count;
    }
}
