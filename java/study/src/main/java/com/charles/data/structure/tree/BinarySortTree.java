package com.charles.data.structure.tree;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 二叉排序树, 也叫做二叉查找树(BinarySearchTree)
 * <p>
 * BST:对于二叉排序树种的任何一个非叶子节点,要求左子节点的值比当前节点的值小,右边节点的值比当前节点大,如果相同,可以随意左右放置
 * <p>
 * 二叉树的删除问题
 * <pre>
 *   1, 删除叶子节点
 *   2, 删除只有一个子树的节点
 *   3, 删除有两颗子树的节点
 * </pre>
 *
 * @author CharlesLee
 */
public class BinarySortTree {

    /**
     * 根节点
     */
    private TreeNode root;

    public static void main(String[] args) {
        BinarySortTree binarySortTree = new BinarySortTree();
        int[] array = new int[]{7, 3, 10, 12, 5, 1, 9, 2, 0, 4, 6};
        for (int value : array) {
            binarySortTree.addNode(new TreeNode(value));
        }
        binarySortTree.removeNode(7);
        binarySortTree.midOrder(binarySortTree.root);

    }

    /**
     * 查找节点值为value的父节点
     *
     * @param value 需要查找的节点的值
     * @return 查找到的节点, 如果为null则说明找不到对应的父节点
     */
    public TreeNode searchParent(int value) {
        if (root == null) {
            return null;
        } else {
            return root.searchParent(value);
        }
    }

    public TreeNode search(int value) {
        if (root == null) {
            return null;
        } else {
            return root.search(value);
        }
    }

    /**
     * 删除节点值为value的节点
     *
     * @param value 需要删除的节点的值
     * @return 返回删除的状态, true就是删除成功, false就是删除失败
     */
    public void removeNode(int value) {
        // 树是空的,那就不用删了
        if (root == null) {
            return;
        }
        TreeNode node = root.search(value);
        // 查找到了这个需要删除的节点
        if (node != null) {
            // 首先判断这个树是否只有根节点这一个节点
            if (root.value.equals(node.value) && root.left == null && root.right == null) {
                root = null;
                // 如果不是跟节点, 再判断是否为叶子节点
            } else if (node.left == null && node.right == null) {
                // 如果它是叶子节点,那么一定有其父节点, 找到父节点, 并判断是哪个子节点, 并将指向指空
                TreeNode parent = root.searchParent(value);
                if (parent != null && parent.left != null && parent.left.value == value) {
                    parent.left = null;
                    // 如果是右子节点
                } else if (parent != null && parent.right != null && parent.right.value == value) {
                    parent.right = null;
                }
                // 如果需要删除的节点有左右子节点
            } else if (node.right != null && node.left != null) {
                // 左边找最大实现, 左边的最大一定是一个叶子节点或者只有一个子节点的子树
                TreeNode temp = node.left;
                while (temp.right != null) {
                    temp = temp.right;
                }
                removeNode(temp.value);
                node.value = temp.value;
                // 到了这里就只会存在只有一个子节点的可能性
            } else {
                TreeNode parent = root.searchParent(value);
                // 如果左子节点不为空
                if (node.left != null) {
                    if (parent != null) {
                        // 判断该节点node是父节点parent的左子节点还是右子节点
                        if (parent.left.value == value) {
                            // 将父节点的左子节点直接指向当前节点的左子节点
                            parent.left = node.left;
                        } else {
                            parent.right = node.left;
                        }
                    } else {
                        // 如果该父节点为空,说明要删除的是根节点,并且只有一个子节点, 这里翻转树即可
                        root = node.left;
                    }
                    // 如果右子节点不为空
                } else if (node.right != null) {
                    if (parent != null) {
                        if (parent.left.value == value) {
                            parent.left = node.right;
                        } else {
                            parent.right = node.right;
                        }
                    } else {
                        // 如果该父节点为空,说明要删除的是根节点,并且只有一个子节点, 这里翻转树即可
                        root = node.right;
                    }
                }
            }
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
     * node对象
     */
    private static class TreeNode {

        public TreeNode(int value) {
            this.value = value;
        }

        public TreeNode() {
        }

        /**
         * 查找节点值为 searchValue 的value
         */
        public TreeNode search(int searchValue) {
            if (value == searchValue) {
                return this;
            }
            // 向左边查找
            if (value > searchValue && left != null) {
                return left.search(searchValue);
            } else if (value < searchValue && right != null) {
                return right.search(searchValue);
            } else {
                return this;
            }
        }

        /**
         * 查找一个节点的父节点, 从当前节点开始查找
         *
         * @param searchValue 需要查找的节点的值
         * @return 返回查找到的节点信息, 如果啥都没找到则返回null
         */
        public TreeNode searchParent(int searchValue) {
            // 左节点满足查找条件
            if (left != null && left.value == searchValue) {
                return this;
            }
            // 右节点满足条件
            if (right != null && right.value == searchValue) {
                return this;
            }
            // 都不满足条件,则需要进行递归查找

            // 判断是否可以向左边查找
            if (left != null) {
                // 如果左子节点的值大于需要查找的节点的值,就说明可以向左节点的左边继续查找
                TreeNode result = left.searchParent(searchValue);
                if (result != null) {
                    return result;
                    // 反之 向右查找
                }
                if (right != null) {
                    return right.searchParent(searchValue);
                }
                // 那么就是查找不到了
                return null;

            }
            return null;
        }

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
            }
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
