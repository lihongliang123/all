package com.charles.algorithm;

import java.util.Arrays;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 迪杰斯特拉算法,典型的最短路径算法,用来计算一个节点到其它节点的最短路径,它的主要特点是以起点为中心向外层层层扩展(广度优先搜索思想),直到扩展到终点为止.
 *
 * @author CharlesLee
 */
public class Dijkstra {

    /**
     * 定义一个代表为空的数字,也代表为不可连接
     */
    private static final int EMPTY = Integer.MAX_VALUE;

    public static void main(String[] args) {
        // 初始化所有的顶点
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        // 初始化邻接矩阵表
        int[][] matrix = {
                {EMPTY, 5, 7, EMPTY, EMPTY, EMPTY, 2},
                {5, EMPTY, EMPTY, 9, EMPTY, EMPTY, 3},
                {7, EMPTY, EMPTY, EMPTY, 8, EMPTY, EMPTY},
                {EMPTY, 9, EMPTY, EMPTY, EMPTY, 4, EMPTY},
                {EMPTY, EMPTY, 8, EMPTY, EMPTY, 5, 4},
                {EMPTY, EMPTY, EMPTY, 4, 5, EMPTY, 6},
                {2, 3, EMPTY, EMPTY, 4, 6, EMPTY}
        };

        Graph graph = new Graph(vertex, matrix);

        graph.show();
        VisitedVertex.dijkstra(6, graph);
    }


    /**
     * 已访问的顶点集合
     */
    private static class VisitedVertex {
        /**
         * 记录各个顶点是否已经访问过,如果访问过则为true
         */
        private boolean[] alreadyArray;

        /**
         * 每个下标对应的值为前一个顶点下标,会动态更新
         */
        private int[] preVisited;

        /**
         * 记录出发顶点到其它所有顶点的距离
         */
        private int[] dis;

        private VisitedVertex visitedVertex;

        private Graph graph;

        /**
         * @param length 表示顶点的个数
         * @param index  出发顶点所对应的下标
         */
        public VisitedVertex(int length, int index) {
            alreadyArray = new boolean[length];
            preVisited = new int[length];
            dis = new int[length];

            Arrays.fill(dis, EMPTY);
            // 默认该顶点已经访问过了
            alreadyArray[index] = true;
            // 设置出发顶点的访问距离为0
            dis[index] = 0;
        }

        /**
         * 迪杰斯特拉算法的实现
         *
         * @param index 表示出发顶点所对应的下标
         */
        public static void dijkstra(int index, Graph graph) {

            VisitedVertex visitedVertex = new VisitedVertex(graph.vertex.length, index);
            visitedVertex.graph = graph;
            visitedVertex.visitedVertex = visitedVertex;
            // 更新index顶点到周围顶点距离和前驱节点
            visitedVertex.update(index);
            for (int i = 1; i < graph.vertex.length; i++) {
                visitedVertex.update(visitedVertex.updateArr());
            }
        }

        /**
         * 更新index顶点到周围顶点距离和前驱节点
         */
        public void update(int index) {
            int len = 0;
            // 遍历与该顶点相关的邻接矩阵表
            for (int i = 0; i < graph.matrix[index].length; i++) {
                // 出发顶点到index顶点的距离 加上 index顶点到i顶点的距离
                len = visitedVertex.getDis(index) + graph.matrix[index][i];
                // 如果i顶点并没有被访问,并且可以找到一条更短的路径,那么就更新该路径
                if (!visitedVertex.in(i) && len < visitedVertex.getDis(i)) {
                    // 更新顶点i的前驱为index顶点
                    visitedVertex.updatePre(i, index);
                    // 更新出发顶点到i顶点的最短距离
                    visitedVertex.updateDis(i, len);
                }
            }
        }

        /**
         * 继续选择并返回新的访问点
         */
        public int updateArr() {
            int min = EMPTY, index = 0;
            for (int i = 0; i < alreadyArray.length; i++) {
                if (!alreadyArray[i] && dis[i] < min) {
                    min = dis[i];
                    index = i;
                }
            }
            // 将该点设置为访问
            alreadyArray[index] = true;
            return index;
        }

        /**
         * 判断index是否被访问过
         */
        public boolean in(int index) {
            return alreadyArray[index];
        }


        /**
         * 更新出发顶点到index顶点的距离
         */
        public void updateDis(int index, int len) {
            dis[index] = len;
        }

        /**
         * 更新pre这个顶点的前驱顶点为index顶点
         */
        public void updatePre(int pre, int index) {
            preVisited[pre] = index;
        }

        /**
         * 返回出发顶点到index顶点的距离
         */
        public int getDis(int index) {
            return dis[index];
        }

    }

    private static class Graph {

        /**
         * 顶点数组
         */
        private char[] vertex;

        /**
         * 邻接矩阵
         */
        private int[][] matrix;

        public Graph(char[] vertex, int[][] matrix) {
            this.vertex = vertex;
            this.matrix = matrix;
        }

        public void show() {
            for (int[] ints : matrix) {
                System.out.println(Arrays.toString(ints));
            }
        }
    }
}
