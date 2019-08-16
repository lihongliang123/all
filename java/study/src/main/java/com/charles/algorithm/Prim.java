package com.charles.algorithm;

import java.util.Arrays;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 普里姆算法,求最小生成树,也就是在包含n个顶点的连通图中,找出只有(n-1)条边包含所有n个顶点的连通子图,也就是所谓的极小连通子图
 * <p>
 * 最小生成树问题(Minimum Cost Spanning Tree), 简称 MST;
 * <pre>
 *     1,给定一个带权的无向连通图,如何选取一颗生成树,使树上所有边上权的总和为最小,这叫做最小生成树.
 *     2,N个顶点,一定有N-1条边
 *     3,包含全部定点
 *     4,N-1条边全都存在与图中
 *     5,最小生成树算法主要有普里姆算法,和克鲁斯卡尔算法
 * </pre>
 *
 * @author CharlesLee
 */
public class Prim {

    /**
     * 定义一个代表为空的数字
     */
    private static final int EMPTY = 10000;

    public static void main(String[] args) {
        MGraph mGraph = new MGraph(7);
        mGraph.data[0] = 'A';
        mGraph.data[1] = 'B';
        mGraph.data[2] = 'C';
        mGraph.data[3] = 'D';
        mGraph.data[4] = 'E';
        mGraph.data[5] = 'F';
        mGraph.data[6] = 'G';

        // 构建邻接矩阵
        mGraph.weight = new int[][]{
                {EMPTY, 5, 7, EMPTY, EMPTY, EMPTY, 2},
                {5, EMPTY, EMPTY, 9, EMPTY, EMPTY, 3},
                {7, EMPTY, EMPTY, EMPTY, 8, EMPTY, EMPTY},
                {EMPTY, 9, EMPTY, EMPTY, EMPTY, 4, EMPTY},
                {EMPTY, EMPTY, 8, EMPTY, EMPTY, 5, 4},
                {EMPTY, EMPTY, EMPTY, 4, 5, EMPTY, 6},
                {2, 3, EMPTY, EMPTY, 4, 6, EMPTY}
        };
        // 查看邻接矩阵表
        mGraph.show();

        MinTree tree = new MinTree();

        tree.prim(mGraph, 'B');

    }

    /**
     * 最小生成树,村庄图
     */
    private static class MinTree {


        /**
         * 普里姆算法
         */
        public void prim(MGraph mGraph, char ver) {
            int verIndex = getVerIndex(mGraph, ver);
            // 该节点不存在与节点列表中,不处理
            if (verIndex == -1) {
                return;
            }
            // 创建标记数组,用来判断该节点是否已经访问
            boolean[] visited = new boolean[mGraph.ver];
            // 将当前节点标记为已经访问
            visited[verIndex] = true;

            // 临时变量,用来临时保存每一次查找到的最短的路径
            // sIndex 用来保存每一次寻找到的最短路径的初始节点
            int sIndex = -1;
            // eIndex 用来保存每一次寻找到的最短路径的结束节点
            int eIndex = -1;

            // 初始化一个最大值,该值记录每一次可查找的最短路径
            int minWeight = EMPTY;

            // 普里姆算法的规则, 边比顶点少一个,所以初始从1开始进行遍历
            for (int i = 1; i < mGraph.ver; i++) {

                // 从已访问的节点去找未访问的节点
                // j的for循环用来遍历所有已经遍历过的节点,也就是说从已经访问过的节点进行下一次的可达性寻找
                for (int j = 0; j < mGraph.ver; j++) {
                    // 如果该节点还没有被访问,那么节点所可连接的下一个节点就不需要被访问
                    if (!visited[j]) {
                        continue;
                    }
                    // k的for循环代表所有未被遍历的节点
                    for (int k = 0; k < mGraph.ver; k++) {
                        // 如果节点K没有被访问,那么就尝试将它访问并找到最小的权值的边
                        if (!visited[k] && mGraph.weight[j][k] < minWeight) {
                            minWeight = mGraph.weight[j][k];
                            sIndex = j;
                            eIndex = k;
                        }
                    }
                }
                // 这里就已经找到了最短的一条边
                System.out.printf("边<%s,%s> 最短, 权值为:%s \n", mGraph.data[sIndex], mGraph.data[eIndex], minWeight);
                // 将最小值重新置空
                minWeight = EMPTY;
                // 将未被访问的边标记为已访问
                visited[eIndex] = true;
            }
        }


        public int getVerIndex(MGraph mGraph, char ver) {
            if (mGraph == null || mGraph.data == null || mGraph.data.length == 0) {
                return 0;
            }
            for (int i = 0; i < mGraph.data.length; i++) {
                if (mGraph.data[i] == ver) {
                    return i;
                }
            }
            return -1;
        }
    }

    /**
     * 图信息
     */
    private static class MGraph {
        /**
         * 表示节点个数
         */
        private int ver;

        /**
         * 表示存放的节点
         */
        private char[] data;

        /**
         * 表示存放的边,也就是邻接矩阵
         */
        private int[][] weight;

        public MGraph(int ver) {
            this.ver = ver;
            data = new char[ver];
            weight = new int[ver][ver];
        }

        public void show() {
            for (int[] ints : weight) {
                System.out.println(Arrays.toString(ints));
            }
        }
    }
}
