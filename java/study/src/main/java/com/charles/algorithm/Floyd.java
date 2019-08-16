package com.charles.algorithm;

import java.util.Arrays;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 弗洛伊德算法
 *
 * @author CharlesLee
 */
public class Floyd {

    private static final int EMPTY = Integer.MAX_VALUE;

    public static void main(String[] args) {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] matrix = {
                {0, 5, 7, EMPTY, EMPTY, EMPTY, 2},
                {5, 0, EMPTY, 9, EMPTY, EMPTY, 3},
                {7, EMPTY, 0, EMPTY, 8, EMPTY, EMPTY},
                {EMPTY, 9, EMPTY, 0, EMPTY, 4, EMPTY},
                {EMPTY, EMPTY, 8, EMPTY, 0, 5, 4},
                {EMPTY, EMPTY, EMPTY, 4, 5, 0, 6},
                {2, 3, EMPTY, EMPTY, 4, 6, 0}
        };
        Graph graph = new Graph(vertex, matrix, vertex.length);
    }

    /**
     * 构建图
     */
    private static class Graph {

        /**
         * 顶点数组
         */
        private char[] vertex;

        /**
         * 保存到达目标顶点的前驱节点
         */
        private int[][] pre;

        /**
         * 保存从各个顶点出发到其它顶点的距离,所以最后的结果也是保存到该数组的
         */
        private int[][] dis;


        public Graph(char[] vertex, int[][] matrix, int length) {
            this.vertex = vertex.clone();
            this.dis = matrix.clone();
            this.pre = new int[length][length];
            // 初始化pre数组,将前驱节点的坐标放入
            for (int i = 0; i < length; i++) {
                Arrays.fill(pre[i], i);
            }
        }

        /**
         * 佛洛依德算法的具体实现
         */
        public void floyd() {
            // 临时变量保存距离信息
            int len = 0;
            // 对中间顶点的遍历,k就是所有的中间节点的下标
            for (int k = 0; k < dis.length; k++) {
                // 从i顶点出发点开始
                for (int i = 0; i < dis.length; i++) {
                    // 从i顶点出发,经过k,最后到达j节点
                    for (int j = 0; j < dis.length; j++) {
                        // 这个距离是从i到顶点k,然后又从k到达顶点j的总距离
                        len = dis[i][k] + dis[k][j];
                        // 如果经过k点的距离小于ij直连的距离,那么将最短的距离进行替换
                        if (len < dis[i][j]) {
                            dis[i][j] = len;
                            // 将前驱节点进行更新
                            pre[i][j] = pre[k][j];
                        }
                    }
                }
            }
        }
    }
}
