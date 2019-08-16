package com.charles.data.structure.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 霍夫曼树是二叉树的一种特殊形式，又称为最优二叉树，其主要作用在于数据压缩和编码长度的优化。
 * <p>
 * 给定n个权值作为n个叶子节点,构造一颗二叉树,若该树的带权路径长度(wpl)达到最小,这样的树就是霍夫曼树,它是带权路径最短的树,权值较大的节点距离根节点较近
 * <pre>
 *     概念:
 *     1, 路径和路径的长度,在一棵树中,从一个节点往下可以达到的孩子或其下节点之间的通路,称之为路径,通路中分支的数目称之为路径的长度.若规定根节点的层数为1,那么从根节点到第L层节点的路径长度为 L-1
 *     2, 节点的权及带权路径长度, 若将树中节点赋给一个有某种含义的数值,则这个数值称之为该节点的权,节点的带权路径长度为: 从根节点到该节点之间的路径长度与该节点的权的乘积
 *     3, 树的带权路径长度:树的带权路径长度规定为所有叶子节点的带权路径长度之和,记为WPL(weighted path length),权值越大的节点距离根节点越近的二叉树才是最优二叉树
 *     4, WPL最小的就是霍夫曼树
 * </pre>
 *
 * @author CharlesLee
 */
public class HuffmanTree implements Tree {

    public static void main(String[] args) {
        int[] array = {13, 7, 8, 3, 29, 6, 1};
        // 创建霍夫曼树的时候一定要保证数组是有序的
        Arrays.sort(array);
        HuffmanTree huffmanTree = new HuffmanTree();
        TreeNode root = huffmanTree.createHuffmanTree(array);
        huffmanTree.midOrder(root);
    }

    /**
     * 中序遍历
     */
    public void midOrder(TreeNode node) {
        if (node != null) {
            midOrder(node.left);
            System.out.println(node.toString());
            midOrder(node.right);
        }
    }

    /**
     * 创建霍夫曼树
     *
     * @param arrays 需要创建的树的数组
     * @return 返回霍夫曼树的根节点
     */
    private TreeNode createHuffmanTree(int[] arrays) {
        List<TreeNode> list = new ArrayList<>(arrays.length);
        for (int array : arrays) {
            list.add(new TreeNode(array));
        }
        System.out.println(list);
        while (list.size() > 1) {
            // 创建左子节点
            TreeNode leftNode = list.get(0);
            // 创建右子节点
            TreeNode rightNode = list.get(1);
            list.remove(0);
            list.remove(0);
            // 创建左右子节点的父节点,该父节点的值是左右节点值之和
            TreeNode root = new TreeNode(leftNode.value + rightNode.value);
            root.left = leftNode;
            root.right = rightNode;
            list.add(0, root);
        }
        return list.get(0);
    }

    /**
     * node对象
     */
    private static class TreeNode {

        public TreeNode(int value) {
            this.value = value;
        }

        public TreeNode() {
        }

        /**
         * 左子节点
         */
        private TreeNode left;

        /**
         * 右子节点
         */
        private TreeNode right;

        /**
         * 节点值
         */
        private Integer value;


        @Override
        public String toString() {
            return "TreeNode{" +
                    "value=" + value +
                    '}';
        }
    }
}
