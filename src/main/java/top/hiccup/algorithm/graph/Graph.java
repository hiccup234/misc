package top.hiccup.algorithm.graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 图的表示：
 * 1、邻接矩阵表示法：用二维数组来存储顶点和顶点之间的关系，计算方便但比较浪费空间
 * 2、邻接表表示法：即哈希表，每个数组元素存储一个链表，链表头为当前顶点，链表为顶点连接的其他顶点
 * 如果是有向图还可以再加一个逆邻接表来存储当前顶点的入度顶点
 * 3、数据库存储：用两列分别代表两个顶点来存储图的边，这两列可以组合成联合唯一索引
 * 
 * 图的广度优先搜索：Breadth-First-Search简称BFS
 * 适合搜索最短路径，使用队列来搜索，首先把当前节点的所有邻居节点入队，判断第一个邻居是否是终节点，不是则把它的所有邻居也入队
 * 直到找到终节点，或者队列为空（没有找到终节点），类似树的层序遍历
 * 
 * 图的深度优先搜索：Depth-First-Search简称DFS
 * 典型例子就是“走迷宫”，采用回溯的思想，基于栈来实现（递归）
 * 
 * 深度优先和广度优先搜索的时间复杂度都是 O(E)，空间复杂度都是 O(V)，其他高级搜索算法：A*，IDA*等
 *
 * @author wenhy
 * @date 2019/5/3
 */
public class Graph {

    /**
     * 顶点的个数
     */
    private int v;
    /**
     * 邻接表表示法
     */
    private LinkedList<Integer>[] adj;

    public Graph(int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            adj[i] = new LinkedList<>();
        }
    }

    /**
     * 无向图一条边存两次
     *
     * @param s
     * @param t
     */
    public void addEdge(int s, int t) {
        adj[s].add(t);
        adj[t].add(s);
    }

    public void bfs(int s, int t) {
        if (s == t) {
            return;
        }
        // 保存每个节点是否已经访问过
        boolean[] visited = new boolean[v];
        visited[s] = true;
        // 用来做层序遍历的辅助空间
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        // 用来记录搜索路径
        int[] prev = new int[v];
        for (int i = 0; i < v; ++i) {
            // 注意，这里填充的是-1，因为数组下标是从0开始
            prev[i] = -1;
        }
        while (queue.size() != 0) {
            int w = queue.poll();
            for (int i = 0; i < adj[w].size(); ++i) {
                int q = adj[w].get(i);
                if (!visited[q]) {
                    prev[q] = w;
                    if (q == t) {
                        print(prev, s, t);
                        return;
                    }
                    visited[q] = true;
                    queue.add(q);
                }
            }
        }
    }


    /**
     * 是否已经找到顶点t
     */
    boolean found = false;

    public void dfs(int s, int t) {
        found = false;
        boolean[] visited = new boolean[v];
        int[] prev = new int[v];
        for (int i = 0; i < v; ++i) {
            prev[i] = -1;
        }
        recurDfs(s, t, visited, prev);
        print(prev, s, t);
    }

    private void recurDfs(int w, int t, boolean[] visited, int[] prev) {
        if (found == true) {
            return;
        }
        visited[w] = true;
        if (w == t) {
            found = true;
            return;
        }
        for (int i = 0; i < adj[w].size(); ++i) {
            int q = adj[w].get(i);
            if (!visited[q]) {
                prev[q] = w;
                recurDfs(q, t, visited, prev);
            }
        }
    }

    /**
     * 递归打印 s->t 的路径（bfs出来的路径就是最短路径）
     */
    private void print(int[] prev, int s, int t) {
        if (prev[t] != -1 && t != s) {
            print(prev, s, prev[t]);
        }
        System.out.print(t + " ");
    }
}

