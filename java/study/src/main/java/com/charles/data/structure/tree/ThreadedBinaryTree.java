package com.charles.data.structure.tree;

/**
 * Charles Lee original, reprint please indicate the source
 * <pre>
 *  线索化二叉树
 *  1, n个节点的二叉链表中含有n+1(公式: 2n-(n-1) = n+1) 个空指针域,利用二叉链表中的空指针域,额存指向该节点在某种遍历次序下的前驱和后继点的指针(这种附加的指针被称之为 "线索")
 *  2, 这种加了线索的二叉链表被称为线索链表, 相应的二叉树被称为线索二叉树, 根据线索的性质不同,线索二叉树分为前,中,后线索二叉树三中情况
 *  3, 一个节点的前一个节点,被称之为前驱节点; 一个节点的后一个节点,被称之为后继节点
 *
 *  当线索化二叉树以后,节点的熟悉left 和 right 可能有以下几种情况
 *  1, left指向的是左子树,也有可能指向的是前驱节点
 *  2, right指向的是右子树, 也有可能指向的是后继节点
 * </pre>
 *
 * @author CharlesLee
 */
public class ThreadedBinaryTree {

    private TreeNode temp;

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node3 = new TreeNode(3);
        TreeNode node6 = new TreeNode(6);
        TreeNode node8 = new TreeNode(8);
        TreeNode node10 = new TreeNode(10);
        TreeNode node14 = new TreeNode(14);

        node1.left = node3;
        node1.right = node6;

        node3.left = node8;
        node3.right = node10;

        node6.left = node14;

        // 前序遍历一次
        ThreadedBinaryTree tree = new ThreadedBinaryTree();

        // 线索化二叉树
        tree.threadedMidNodes(node1);

        System.out.println("8 的左节点为: " + node8.left + ", 类型为: " + node8.leftType);
        tree.threadedMidList(node1);
    }

    /**
     * 遍历线索化过后的二叉树
     * <p>
     * 中序线索化的二叉树必须中序进行遍历
     */
    public void threadedMidList(TreeNode node) {
        if (node != null) {
            threadedMidList(node.leftType == NodeType.PRECURSOR_NODE ? null : node.left);
            System.out.println(node.toString());
            threadedMidList(node.rightType == NodeType.SUCCESSOR_NODE ? null : node.right);
        }
    }

    /**
     * 对二叉树的中序线索化方法
     */
    public void threadedMidNodes(TreeNode node) {
        // 需要保证要线索化的节点不可以为null
        if (node == null) {
            return;
        }
        // 如果有左节点,那么就一直查找到第一个不为null的子节点
        if (node.left != null) {
            threadedMidNodes(node.left);
        }

        // 处理前驱节点
        if (node.left == null) {
            node.left = temp;
            node.leftType = NodeType.PRECURSOR_NODE;
        }

        // 处理后继节点
        if (temp != null && temp.right == null) {
            temp.right = node;
            temp.rightType = NodeType.SUCCESSOR_NODE;
        }

        // 每处理完一个节点,就指向下一个节点
        temp = node;
        // 线索化右子节点
        if (node.right != null) {
            threadedMidNodes(node.right);
        }
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
         * 左节点类型
         */
        private NodeType leftType;

        /**
         * 右子节点
         */
        private TreeNode right;

        /**
         * 右子节点类型
         */
        private NodeType rightType;

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


    private enum NodeType {
        /**
         * 左子树
         */
        LEFT_SUBTREE,

        /**
         * 右子树
         */
        RIGHT_SUBTREE,

        /**
         * 前驱节点
         */
        PRECURSOR_NODE,

        /**
         * 后继节点
         */
        SUCCESSOR_NODE;
    }
}
