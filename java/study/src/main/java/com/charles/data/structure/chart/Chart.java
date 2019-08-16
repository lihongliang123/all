package com.charles.data.structure.chart;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 邻接矩阵实现简单的平面图
 * <pre>
 *  图
 *     当需要使用多对多关系的时候,我们需要用到图
 *     图是一种数据结构,其中的节点可以具有零或多个相邻元素,两个节点之间的连接被称之为边,节点也被称之为顶点
 *     图的常用概念:
 *         1) 顶点, 每一个节点又被称之为顶点
 *         2) 边, 两个节点之间连接被称之为边
 *         3) 路径, 从一个顶点走到另一个顶点所经历的边被称之为路径, 路径一般不唯一
 *         4) 无向, 顶点之间的连接没有任何的方向
 *         5) 有向, 顶点之间的连接是有固定方向的图, 比如顶点AB相连, A可以到B, 但是B不可以直接到A, 这种图被称之为有向图
 *         6) 带权, 边上面带有权值
 *     图的表现形式:
 *     二维数组表示(邻接矩阵); 链表表示(邻接表)
 *     邻接矩阵: 表示图形中顶点之间的相邻关系的矩阵,对于n个顶点的图而言,矩阵的大小为 n * n;
 *              缺点: 需要为每个顶点都分配n个边的空间,其实有很多边都是不存在的,会造成空间的浪费
 *     邻接表:   只关系存在的边,不关心不存在的边,因此没有空间浪费,它是由一位数组和链表组成
 *   图的遍历,即是对图的节点的访问,一个图的节点有很多,如果遍历则需要制定特定的策略,常见的策略有
 *   1, 深度优先搜索(Depth First Search, DFS)
 *      1) 从初始访问点出发,初始访问点可能有多个邻接节点,策略为首先访问第一个邻节点,然后再以这个被访问的邻接点作为初始节点,访问第一个邻接点.
 *         也就是说,每次访问完当前节点以后首先访问当前节点的第一个邻接点
 *      2) 该策略是优先往纵向挖掘深入,而不是对一个节点的所有邻接点进行横向访问
 *      3) 从策略来看,深度优先搜索模式是一个递归搜索
 *
 *   2, 广度优先搜索(Broad First Search)
 *      类似于一个分层搜索的过程,该遍历需要使用一个队列以保持访问过的节点的顺序,以便按这个顺序来访问这些节点的邻接节点
 *
 *
 * </pre>
 *
 * @author CharlesLee
 * <p>
 */
public class Chart {

    public static void main(String[] args) throws Exception {

//        String[] vertex = {"A", "B", "C", "D", "E"};
        String[] vertex = {"1", "2", "3", "4", "5", "6", "7", "8"};
        Chart c = new Chart(vertex.length);
        for (String s : vertex) {
            c.insertVertex(s);
        }
//        c.insertEdge("A", "B", 1);
//        c.insertEdge("A", "C", 1);
//        c.insertEdge("B", "C", 1);
//        c.insertEdge("B", "D", 1);
        c.insertEdge("1", "2", 1);
        c.insertEdge("1", "4", 1);
        c.insertEdge("2", "5", 1);
        c.insertEdge("2", "5", 1);
        c.insertEdge("4", "8", 1);
        c.insertEdge("5", "8", 1);
        c.insertEdge("3", "6", 1);
        c.insertEdge("3", "7", 1);
        c.insertEdge("6", "7", 1);

//        c.ergodic();

        c.dfs();
//        c.bfs();

    }

    /**
     * 存储顶点的集合
     * <p>
     * 顶点的下标则代表该顶点所在的位置
     */
    private String[] vertexArray;

    /**
     * vertexArray 的下标信息
     */
    private int vertexArrayIndex;

    /**
     * 存储图所对应的邻接矩阵
     */
    private int[][] edges;

    /**
     * 节点是否已经被访问
     */
    private boolean[] isVisited;

    /**
     * 存放边的条数
     */
    private int numOfEdges;

    public Chart(int maxVertex) {
        vertexArray = new String[maxVertex];
        edges = new int[maxVertex][maxVertex];
        isVisited = new boolean[maxVertex];
    }

    /**
     * 遍历邻接矩阵
     */
    public void ergodic() {
        for (int[] edge : edges) {
            System.out.println(Arrays.toString(edge));
        }
    }

    /**
     * 得到指定节点的下一个邻接节点下标
     *
     * @return 如果存在返回对应的节点信息, 否则返回null;
     */
    public String getFirstNode(String vertex) {
        int index = getValueByIndex(vertex);
        if (index == -1) {
            return null;
        }
        for (int i = index + 1; i < vertexArray.length; i++) {
            if (edges[index][i] > 0) {
                return vertexArray[i];
            }
        }
        return null;
    }

    /**
     * 获取邻接节点下一个节点的下一个节点
     *
     * @param vertex 邻接节点
     */
    public String getFirstNodeNext(String vertex) {
        int index = getValueByIndex(vertex);
        if (index == -1) {
            return null;
        }
        for (int i = index + 1; i < vertexArray.length; i++) {
            if (edges[index][i] > 0) {
                return vertexArray[i];
            }
        }
        return null;
    }

    /**
     * 深度优先遍历算法
     */
    public void dfs() {
        for (String s : vertexArray) {
            dfs(s);
        }
    }

    /**
     * 深度优先遍历算法
     */
    public void dfs(String vertex) {
        int index = getValueByIndex(vertex);
        if (index == -1) {
            return;
        }
        if (!isVisited[index]) {
            System.out.printf("节点[%s]所在的位置为[%d] -> ", vertex, index);
        }
        // 将已经遍历的节点进行标记
        isVisited[index] = true;
        String w;
        if ((w = getFirstNode(vertex)) != null) {
            // 如果有下一个相邻节点并且还没有被访问,那么则访问
            index = getValueByIndex(w);
            if (!isVisited[index]) {
                dfs(w);
            } else {
                // 查找邻接节点的下一个节点
                dfs(getFirstNodeNext(w));
            }
        }
    }

    public int getValueByIndex(String vertex) {
        for (int i = 0; i < vertexArray.length; i++) {
            if (vertexArray[i] != null && vertexArray[i].equals(vertex)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 插入节点, 需要注意的是插入的节点不可以相同
     *
     * @param vertex 节点信息
     */
    public void insertVertex(String vertex) {
        // 同样的数据不可以插入
        for (String s : vertexArray) {
            if (s != null && s.equals(vertex)) {
                return;
            }
        }
        vertexArray[vertexArrayIndex] = vertex;
        vertexArrayIndex++;
    }

    /**
     * @param vertex1 边的一个顶点
     * @param vertex2 边的一个顶点
     * @param weight  边的权值
     * @author CharlesLee
     */
    public void insertEdge(String vertex1, String vertex2, int weight) {
        int vertex1Index = -1;
        int vertex2Index = -1;
        for (int i = 0; i < vertexArray.length; i++) {
            if (vertexArray[i] != null && vertexArray[i].equals(vertex1)) {
                vertex1Index = i;
            }
            if (vertexArray[i] != null && vertexArray[i].equals(vertex2)) {
                vertex2Index = i;
            }
        }
        if (vertex1Index == -1 || vertex2Index == -1) {
            return;
        }
        edges[vertex1Index][vertex2Index] = weight;
        edges[vertex2Index][vertex1Index] = weight;
        numOfEdges++;
    }

    public void bfs() {
        for (String s : vertexArray) {
            bfs(s);
        }
    }

    /**
     * 对节点vertex进行广度优先遍历
     *
     * @param vertex 需要进行遍历的节点
     */
    public void bfs(String vertex) {

        // 队列,记录节点的访问顺序
        Queue<String> queue = new ArrayBlockingQueue<>(vertexArray.length);

        int index = getValueByIndex(vertex);
        if (index == -1) {
            return;
        }
        // 如果该节点没有遍历过,那么加入队列
        if (!isVisited[index]) {
            queue.offer(vertex);
            System.out.printf("节点[%s]所在的位置为[%d] -> ", vertex, index);
            isVisited[index] = true;
        }

        // 如果队列不为空,则进行数据的处理
        while (!queue.isEmpty()) {
            String u = queue.poll();
            // 获取当前节点u的下一个节点
            String next = getFirstNode(u);
            int indexNext = getValueByIndex(next);
            if (indexNext != -1 && !isVisited[indexNext]) {
                queue.offer(next);
                System.out.printf("节点[%s]所在的位置为[%d] -> ", next, indexNext);
                isVisited[indexNext] = true;
            }
        }
    }
}
