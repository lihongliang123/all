package com.charles.algorithm.recursion;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 迷宫回溯问题
 * <p>
 * 约定, 0为没有走过的位置, 1为墙体,也就是死路; 2,表示通路; 3,表示已经走过了,但是走不通;
 *
 * 最短路径问题,应该怎么才可以找到最短的路径呢
 *
 * @author CharlesLee
 */
public class Maze {

    /**
     * 死胡同
     */
    public static final int DEAD_END = 3;

    /**
     * 通路
     */
    public static final int OK = 2;

    /**
     * 墙体
     */
    public static final int WALL = 1;

    /**
     * 构建一个8*8的迷宫
     */
    private int[][] maze = initMaze();

    private int[][] initMaze() {
        int[][] maze = new int[8][8];
        // 将迷宫的两个竖列进行封墙
        for (int i = 0; i < maze.length; i++) {
            maze[i][0] = WALL;
            maze[i][maze[i].length - 1] = WALL;
        }

        // 将迷宫的两个横列进行封墙
        for (int i = 0; i < maze[0].length; i++) {
            maze[0][i] = WALL;
            maze[maze[0].length - 1][i] = WALL;
        }

        // 设置墙体
        maze[3][1] = WALL;
        maze[3][2] = WALL;
        return maze;
    }

    /**
     * 使用递归回溯来找路
     *
     * @param maze 需要传入的地图
     * @param sx   启动坐标点X
     * @param sy   启动坐标点Y
     * @param ex   结束坐标点X
     * @param ey   结束坐标点Y
     * @author CharlesLee
     */
    public boolean setWay(int[][] maze, int sx, int sy, int ex, int ey) {
        // 如果已经找到了通路,那么就说明已经不用继续递归下去了
        if (maze[ex][ey] == OK) {
            return true;
        } else {
            // 如果当前的这个节点没有走过,那么就去尝试走一下
            if (maze[sx][sy] == 0) {
                // 那么就假设这个点是可以走通的
                maze[sx][sy] = OK;
                // 尝试着向下走
                if (setWay(maze, sx + 1, sy, ex, ey)) {
                    return true;
                } else if (setWay(maze, sx, sy + 1, ex, ey)) {
                    // 尝试着向右走
                    return true;
                } else if (setWay(maze, sx - 1, sy, ex, ey)) {
                    // 尝试着向上走
                    return true;
                } else if (setWay(maze, sx, sy - 1, ex, ey)) {
                    // 尝试着向左走
                    return true;
                } else {
                    maze[sx][sy] = DEAD_END;
                    return false;
                }
                // 如果节点走过了,那么就别走了
            } else {
                return false;
            }
        }
    }

    public static void main(String[] args) {
        Maze maze = new Maze();
        for (int i = 0; i < maze.maze.length; i++) {
            for (int j = 0; j < maze.maze[i].length; j++) {
                System.out.print(maze.maze[i][j] + " ");
            }
            System.out.println();
        }

        maze.setWay(maze.maze, 1, 1, 6, 6);

        System.out.println("查看路径：");
        for (int i = 0; i < maze.maze.length; i++) {
            for (int j = 0; j < maze.maze[i].length; j++) {
                System.out.print(maze.maze[i][j] + " ");
            }
            System.out.println();
        }
    }
}
