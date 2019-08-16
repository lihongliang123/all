package com.charles.algorithm.sort;

import java.util.Arrays;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 堆排序
 * <pre>
 *  1, 堆排序是利用堆这种数据结构而设计的一种排序算法,它属于选择排序,他的最坏,最好,平均时间复杂度均为O(nlogn),它也是不稳定排序
 *  2, 堆是具有以下性质的完全二叉树: 每个节点的值都大于或等于左右子节点值,称为大顶堆. 这里并不要求左右子节点值的大小关系.
 * 每个节点的值都小于或等于左右子节点的值,称之为小顶堆
 *
 * </pre>
 *
 * @author CharlesLee
 */
public class HeapSort implements SortAlgorithm<int[]> {

    public static void main(String[] args) {
        int[] arr = {4, 6, 8, 5, 9};

        // 49856
        new HeapSort().sort(arr);
        // 96854
    }

    /**
     * 堆排序,以大顶堆的方式进行设计
     */
    @Override
    public void sort(int[] arrays) {
        // 首先构建大顶堆,找到根据定理找到需要开始判断的子节点
        for (int i = arrays.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arrays, i, arrays.length);
        }


        // 将堆顶元素与未元素进行交换,将最大的元素沉到数组的末端
        // 然后重新调整结构,使其满足堆定义,然后继续交换堆顶元素与末尾元素,反复执行交换与调整的步骤,直到整个序列有序
        int temp;
        for (int i = arrays.length - 1; i > 0; i--) {
            temp = arrays[i];
            arrays[i] = arrays[0];
            arrays[0] = temp;
            adjustHeap(arrays, 0, i);
        }

        System.out.println(Arrays.toString(arrays));

    }


    /**
     * 将一个以i所对应的非叶子节点的树调整成为大顶堆
     * <p>
     * 也就是说i所对应的树为父节点,与其左右子节点三个节点构成的树,将之调整为大顶堆
     *
     * @param array  需要调整的数组
     * @param i      需要调整的非叶子节点的树所对应的位置
     * @param lenght 表示需要对多少个元素进行调整,元素个数会因为不断地调整而变得更少
     * @author CharlesLee
     */
    private void adjustHeap(int[] array, int i, int lenght) {
        // 首先记录树的位置为i的坐标的值,因为交换变量,所以临时存储
        int temp = array[i];

        // 开始调整,首先查询到该节点的左子节点, 根据公式为 父节点 i 的坐标 * 2 + 1 可取得
        for (int j = i * 2 + 1; j < lenght; j = j * 2 + 1) {
            // 如果左子树比右子树的值小,则进行交换
            if (j + 1 < lenght && array[j] < array[j + 1]) {
                // 调整指针的指向位置, 将其指向为较大的数字
                j++;
            }
            // 从上面判断中找到了左右子节点最大的值,然后与父节点进行对比, 如果子节点大于父节点则进行交换
            if (array[j] > temp) {
                // 将数字进行调换,生成当前的大顶堆
                array[i] = array[j];
                // 移动下标,继续判断
                i = j;
            } else {
                break;
            }
        }
        // 执行到了这里代表前面已经全部交换完毕了,已经是一个大顶堆了, 所以这里只需要把临时保存的变量给存入到交换过后的原数组即可
        array[i] = temp;
    }
}
