package com.charles.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 克鲁斯卡尔算法,用来求加权连通图的最小生成树算法. 按照权值从小到大的顺序选择n-1(七个点只需要6条线就可以连通)条边,并保证这n-1条边不构成回路.
 * 具体做法: 构造一个只含有n个顶点的森林,然后依权值从小到大连通网中选择边加入到森林,并使森林不产生回路,直到森林变成一棵树为止
 * <p>
 * 回路: 加入的边的两个顶点不能指向同一个终点
 * <p>
 * 处理最短公交站路径问题
 *
 * @author CharlesLee
 */
public class Kruskal {

    /**
     * 定义一个代表为空的数字
     */
    private static final int EMPTY = Integer.MAX_VALUE;

    public static void main(String[] args) {
        // 初始化所有顶点
        char[] vers = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        MGraph m = new MGraph(vers.length);
        m.data = vers;

        // 克鲁斯卡尔算法的邻接矩阵
        m.weight = new int[][]{
                {0, 12, EMPTY, EMPTY, EMPTY, 16, 14},
                {12, 0, 10, EMPTY, EMPTY, 7, EMPTY},
                {EMPTY, 10, 0, 3, 5, 6, EMPTY},
                {EMPTY, EMPTY, 3, 0, 4, EMPTY, EMPTY},
                {EMPTY, EMPTY, 5, 4, 0, 2, 8},
                {16, 7, 6, EMPTY, 2, 0, 9},
                {14, EMPTY, EMPTY, EMPTY, 8, 9, 0},
        };

        m.kruskal();

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

        /**
         * 统计所有有效的边
         */
        private int edgeNumber;

        public MGraph(int ver) {
            this.ver = ver;
            data = new char[ver];
            weight = new int[ver][ver];
        }

        public void edgeNumberCount() {
            edgeNumber = 0;
            for (int[] ints : weight) {
                for (int anInt : ints) {
                    if (anInt != EMPTY) {
                        edgeNumber++;
                    }
                }
            }
        }

        public void show() {
            for (int[] ints : weight) {
                System.out.println(Arrays.toString(ints));
            }
        }

        /**
         * 克鲁斯卡尔算法
         */
        public void kruskal() {
            // 统计有效的边
            edgeNumberCount();
            List<ElementData> dataList = getEdges();
            // 将所有有效边对象进行排序,从小到大排序
            dataList.sort(Comparator.comparingInt(o -> o.weight));
            // 表示最后结果数组的索引
            int index = 0;
            // 结果数组
            ElementData[] result = new ElementData[edgeNumber];
            // 用来保存"已有最小生成树"中每个顶点在最小生成树中的终点
            int[] ends = new int[edgeNumber];

            // 将边添加到最小生成树中,判断准备加入的边是否构成了回路,如果没有则加入,反之不可加入
            for (ElementData elementData : dataList) {

                // 分别获取一条边的两个顶点的下标位置
                int startIndex = getIndex(elementData.startNode);
                int endIndex = getIndex(elementData.endNode);

                // 分别找到这两个顶点在最小生成树中的终点
                int m = getEndByStart(ends, startIndex);
                int n = getEndByStart(ends, endIndex);
                // 验证是否构成回路就看终点是否一样
                if (m != n) {
                    // 将m的终点在最小生成树中设置为n
                    ends[m] = n;
                    // 有一条边的数据加入至结果数组
                    result[index++] = elementData;
                }
            }
            System.out.println("结果为:");
            for (int i = 0; i < result.length; i++) {
                if (result[i] != null) {
                    System.out.println(result[i].toString());
                }
            }
        }


        /**
         * 获取对象顶点的下标所在位置
         */
        public int getIndex(char c) {
            for (int i = 0; i < data.length; i++) {
                if (c == data[i]) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * 获取所有边的信息,将之转换为集合
         */
        public List<ElementData> getEdges() {
            List<ElementData> result = new ArrayList<>(edgeNumber);
            for (int i = 0; i < weight.length; i++) {
                for (int j = i + 1; j < weight.length; j++) {
                    if (weight[i][j] != EMPTY) {
                        result.add(new ElementData(data[i], data[j], weight[i][j]));
                    }
                }
            }
            return result;
        }


        /**
         * 获取下标为i的顶点的终点下标
         *
         * @param ends 数组是记录了各个顶点所对应的终点是哪个,ends数组在遍历的过程中,是逐步形成的
         * @param i    表示传入的顶点的对应下标
         * @return 返回下标为i的顶点所对应终点的下标
         */
        public int getEndByStart(int[] ends, int i) {
            while (ends[i] != 0) {
                i = ends[i];
            }
            return i;
        }

    }

    /**
     * 用来表示两个顶点与一条边所连接在一起的对象
     */
    private static class ElementData {
        /**
         * 边开始节点(也可以视为结束顶点)
         */
        private char startNode;
        /**
         * 边的结束节点(也可以视为开始顶点)
         */
        private char endNode;
        /**
         * 边的权值
         */
        private int weight;

        public ElementData(char startNode, char endNode, int weight) {
            this.startNode = startNode;
            this.endNode = endNode;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "ElementData{" +
                    "startNode=" + startNode +
                    ", endNode=" + endNode +
                    ", weight=" + weight +
                    '}';
        }
    }
}
