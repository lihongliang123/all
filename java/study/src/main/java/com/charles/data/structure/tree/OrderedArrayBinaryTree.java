package com.charles.data.structure.tree;

/**
 * Charles Lee original, reprint please indicate the source
 * 顺序存储的有序二叉树,以数组的方式来存储,数组可以转换成树,树也可以转换为数组
 * <pre>
 *     设 n 来表示二叉树中的第几个元素, 按照编号0开始, 也就是说根节点的编号为0
 *     顺序二叉树通常只考虑完全二叉树
 *     第n个元素的左子节点为 2 * n + 1
 *     第n个元素的右子节点为 2 * n + 2
 *     第n个元素的父节点为 ( n - 1 ) / 2
 *
 *
 * </pre>
 *
 * @author CharlesLee
 */
public class OrderedArrayBinaryTree {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7};
        new OrderedArrayBinaryTree().preOrder(array, 0);
    }

    /**
     * 顺序存储二叉树
     * <p>
     * 前序遍历的方式进行输出
     */
    public void preOrder(int[] array, int index) {
        if (array == null || array.length == 0) {
            return;
        }
        System.out.println(array[index]);
        int l = 2 * index + 1;
        if (l < array.length) {
            preOrder(array, l);
        }
        int r = 2 * index + 2;
        if (r < array.length) {
            preOrder(array, r);
        }
    }
}
