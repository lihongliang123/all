package com.charles.data.structure.tree;

import java.util.Random;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 无序二叉树
 *
 * <pre>
 *   二叉树,每个节点最多只能有两个子节点的树被称之为二叉树
 *   它的节点被分为左子节点和右子节点
 *   如果该二叉树的所有叶子节点都在最后一层,并且节点总数为 2^n -1, 其中n为层数,那么它就是一个满二叉树
 *   如果二叉树的所有叶子节点都在最后一层或者倒数第二层,而且最后一层的叶子节点在左边连续(满叶子节点),倒数第二层的叶子节点在右边连续(满叶子节点),则是一个完全二叉树
 *
 *   前序遍历: 父 -> 左 -> 右
 *   中序遍历: 左 -> 父 -> 右
 *   后序遍历: 左 -> 右 -> 父
 *
 *
 * </pre>
 *
 * @author CharlesLee
 */
public class DisorderBinaryTree {


    private int[] arrays;

    private int arraysIndex = 0;

    private Random random;

    /**
     * 树的根节点
     */
    private TreeNode root;

    /**
     * 创建一个二叉树,通过随机数生成的方式创建,因为是随机的数量,所以无法判断其树的高度
     *
     * @param count 需要一共生成count个节点
     */
    public TreeNode createTreeNode(int count, int max) {
        int c = 1;
        arrays = new int[count];
        if (count > max || count <= 0) {
            return null;
        }
        while (c <= count) {
            TreeNode node = new TreeNode();
            node.value = getRandomInt(max);
            if (root == null) {
                root = node;
            } else {
                TreeNode tempNode = root;
                // 计算节点需要添加的位置
                while (true) {
                    // 向左子叶节点遍历
                    if (node.value < tempNode.value) {
                        if (tempNode.left == null) {
                            tempNode.left = node;
                            break;
                        } else {
                            tempNode = tempNode.left;
                        }
                    } else {
                        // 向右子叶节点遍历
                        if (tempNode.right == null) {
                            tempNode.right = node;
                            break;
                        } else {
                            tempNode = tempNode.right;
                        }
                    }
                }
            }
            c++;
        }
        return root;
    }


    /**
     * 获取随机的数字
     *
     * @param max 最大的随机数区间
     */
    public int getRandomInt(int max) {
        if (max <= 0) {
            throw new RuntimeException("随机数区间不能小于1");
        }
        if (random == null) {
            random = new Random();
        }
        int result;
        while (true) {
            result = random.nextInt(max);
            for (int array : arrays) {
                if (array == result) {
                    break;
                }
            }
            arrays[arraysIndex] = result;
            arraysIndex++;
            return result;
        }
    }

    /**
     * 前序遍历
     */
    public void preOrder(TreeNode node) {
        if (node != null) {
            System.out.println(node.toString());
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    /**
     * 前序遍历查找
     * <p>
     * 前提条件,树中所有的数据没有一样的值
     */
    public TreeNode preOrderFind(TreeNode node, int value) {
        if (node != null) {
            if (node.value == value) {
                return node;
            }
            TreeNode result = preOrderFind(node.left, value);
            if (result != null) {
                return result;
            }
            return preOrderFind(node.right, value);
        }
        return null;
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
     * 中序遍历查找
     * <p>
     * 前提条件,树中所有的数据没有一样的值
     */
    public TreeNode midOrderFind(TreeNode node, int value) {
        if (node != null) {
            TreeNode result = midOrderFind(node.left, value);
            if (result != null) {
                return result;
            }
            if (node.value == value) {
                return node;
            }
            return midOrderFind(node.right, value);
        }
        return null;
    }

    /**
     * 后序遍历
     */
    public void postOrder(TreeNode node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.println(node.toString());
        }
    }

    /**
     * 后序遍历
     * <p>
     * 前提条件,树中所有的数据没有一样的值
     */
    public TreeNode postOrderFind(TreeNode node, int value) {
        if (node != null) {
            TreeNode result = postOrderFind(node.left, value);
            if (result != null) {
                return result;
            }
            result = postOrderFind(node.right, value);
            if (result != null) {
                return result;
            }
            if (node.value == value) {
                return node;
            }
        }
        return null;
    }


    /**
     * 二叉树的删除方法,删除的规则为如果是子叶,那么直接删除,否则删除整个子树
     * <p>
     * 需要注意的是该方法不能删除根节点,如果需要删除的是根节点,那么左右子节点会被置空,然后值会被归null
     */
    public boolean removeNodeAndSubtree(TreeNode node, int value) {
        if (node != null) {
            if (node.value == value) {
                node.value = null;
                node.left = null;
                node.right = null;
                return true;
            } else if (node.left != null && node.left.value == value) {
                node.left = null;
                return true;
            } else if (node.right != null && node.right.value == value) {
                node.right = null;
                return true;
            } else {
                boolean result = removeNodeAndSubtree(node.left, value);
                if (result) {
                    return true;
                }
                return removeNodeAndSubtree(node.right, value);
            }
        }
        return false;
    }

    /**
     * 二叉树的删除方法,删除的规则为如果是子叶,那么直接删除,否则左边有子节点提左边,右边有子节点提右边,顺序提
     * 需要注意的是该方法不能删除根节点,如果需要删除的是根节点,那么左右子节点会被置空,然后值会被归null
     */
    public TreeNode removeNode(TreeNode node, int value) {
        if (node != null) {
            if (node.value == value) {
                // 如果左边有节点,就将左边的节点往上提
                TreeNode rightNode = node.right;
                if (node.left != null) {
                    node = node.left;
                    node.right = rightNode;
                } else if (node.right != null) {
                    node = node.right;
                } else {
                    node.value = null;
                }
                return node;
            } else if (node.left != null && node.left.value == value) {
                TreeNode rightNode = node.left.right;
                if (node.left.left != null) {
                    node.left = node.left.left;
                    node.left.right = rightNode;
                } else if (node.left.right != null) {
                    node.left = node.left.right;
                } else {
                    node.left = null;
                }
                return node;
            } else if (node.right != null && node.right.value == value) {
                TreeNode rightNode = node.right.right;
                if (node.right.left != null) {
                    node.right = node.right.left;
                    node.right.right = rightNode;
                } else if (node.right.right != null) {
                    node.right = node.right.right;
                } else {
                    node.right = null;
                }
                return node;
            } else {
                TreeNode result = removeNode(node.left, value);
                if (result != null) {
                    return result;
                }
                return removeNode(node.right, value);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        DisorderBinaryTree tree = new DisorderBinaryTree();

        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode4 = new TreeNode(4);
        TreeNode treeNode5 = new TreeNode(5);
        TreeNode treeNode2 = new TreeNode(2);

        treeNode1.left = treeNode3;
        treeNode1.right = treeNode4;
        treeNode4.left = treeNode5;
        treeNode4.right = treeNode2;

        tree.preOrder(treeNode1);

        treeNode1 = tree.removeNode(treeNode1, 3);

        System.out.println("删除一下看看呢");
        tree.preOrder(treeNode1);
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

        private Integer value;


        @Override
        public String toString() {
            return "TreeNode{" +
                    "value=" + value +
                    '}';
        }
    }
}
