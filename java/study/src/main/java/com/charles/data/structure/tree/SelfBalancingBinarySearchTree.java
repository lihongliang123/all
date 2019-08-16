package com.charles.data.structure.tree;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 平衡二叉树,也叫做平衡二叉搜索树,还被称之为AVL树,可以保证其查询的效率问题
 * <p>
 * 它是一颗空树或者左右两子树高度差绝对值不超过1的树,并且左右两个子树都是一颗二叉平衡树
 * <p>
 * 旋转左右节点,就是将高的部分旋转到低的部分,形成一个平衡二叉树
 *
 * @author CharlesLee
 */
public class SelfBalancingBinarySearchTree {

    private TreeNode root;

    public int maxHeight() {
        if (root == null) {
            return 0;
        } else {
            return 1;
        }
    }

    public void addNode(TreeNode node) {
        if (node == null) {
            return;
        }
        if (root == null) {
            root = node;
        } else {
            root.addNode(node);
        }
    }

    private static class TreeNode {

        public TreeNode(int value) {
            this.value = value;
        }

        public TreeNode() {

        }

        /**
         * 节点的左旋转方法
         */
        public void leftRotate() {
            // 创建新的节点,以当前节点的值
            TreeNode newNode = new TreeNode(value);

            // 新节点的左子树设置为当前节点的左子树
            newNode.left = left;

            // 新节点的右子树设置成为右子树的左子树
            newNode.right = right.left;

            // 把当前节点的值替换称为右子节点的值
            value = right.value;

            // 把当前节点的右子树替换成为当前节点右子树的右子树
            right = right.right;

            // 将当前节点的左子树设置成为新的节点
            left = newNode;
        }

        /**
         * 节点右旋转的方法
         */
        public void rightRotate() {
            TreeNode newNode = new TreeNode(value);
            newNode.right = right;
            newNode.left = left.right;
            value = left.value;
            left = left.left;
            right = newNode;
        }

        public int leftHeight() {
            if (left != null) {
                return left.maxHeight();
            } else {
                return 0;
            }
        }

        public int rightHeight() {
            if (right != null) {
                return right.maxHeight();
            } else {
                return 0;
            }
        }

        /**
         * 计算从当前节点开始,树的最大高度为多少
         */
        public int maxHeight() {
            // 以递归的方式遍历整个树, 找到其最深的那个子叶节点, 然后返回其值
            return Math.max(left == null ? 0 : left.maxHeight(), right == null ? 0 : right.maxHeight()) + 1;
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


        public void addNode(TreeNode node) {
            if (node != null) {
                // 如果node节点的值小于当前节点的值, 那么就丢到左边
                if (node.value < value) {
                    if (left == null) {
                        left = node;
                    } else {
                        left.addNode(node);
                    }
                } else {
                    if (right == null) {
                        right = node;
                    } else {
                        right.addNode(node);
                    }
                }

                // 判断是否可以进行左旋转, 当右子树的高度减去左子树的高度的值大于1的时候, 将进行左旋转
                if ((rightHeight() - leftHeight()) > 1) {
                    //如果当前节点的右子树的左子树的高度大于当前节点右子树的右子树高度
                    if (right != null && right.leftHeight() > right.rightHeight()) {
                        right.rightRotate();
                    }
                    leftRotate();
                    return;
                }
                if ((leftHeight() - rightHeight()) > 1) {
                    if (left != null && left.rightHeight() > left.leftHeight()) {
                        left.leftHeight();
                    }
                    rightRotate();
                }
            }
        }


        @Override
        public String toString() {
            return "TreeNode{" +
                    "value=" + value +
                    '}';
        }
    }
}
