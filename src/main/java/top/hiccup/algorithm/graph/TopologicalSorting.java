package top.hiccup.algorithm.graph;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 拓扑排序：要求有向图中不能有环，不止一种排序结果
 * 
 * 两种实现方式：
 * 1、Kahn算法 -- O(V+E)
 * 2、DFS深度优先搜索算法 -- O(V+E)
 *
 * @author wenhy
 * @date 2019/7/25
 */
public class TopologicalSorting {

    public class Graph {
        /**
         * 顶点的个数
         */
        private int v;
        /**
         * 邻接表，存储每个顶点指向的其他顶点的编号（编号大于等于0）
         */
        private LinkedList<Integer> adj[];

        public Graph(int v) {
            this.v = v;
            adj = new LinkedList[v];
            for (int i = 0; i < v; ++i) {
                adj[i] = new LinkedList<>();
            }
        }

        public void addEdge(int s, int t) { // s 先于 t，边 s->t
            adj[s].add(t);
        }

        /**
         * Kahn算法借助了队列
         * 找出图中一个入度为0的顶点进行访问，并“删除”已访问的节点，然后继续遍历入度为零的其他节点
         */
        public void topoSortByKahn() {
            // 统计每个顶点的入度
            int[] inDegree = new int[v];
            Arrays.fill(inDegree, 0);
            for (int i = 0; i < v; ++i) {
                for (int j = 0; j < adj[i].size(); ++j) {
                    // i->w
                    int w = adj[i].get(j);
                    inDegree[w]++;
                }
            }
            LinkedList<Integer> queue = new LinkedList<>();
            for (int i = 0; i < v; ++i) {
                // 把所有入度为0的顶点都先放到队列里
                if (inDegree[i] == 0) {
                    queue.add(i);
                }
            }
            while (!queue.isEmpty()) {
                int i = queue.remove();
                System.out.print("->" + i);
                for (int j = 0; j < adj[i].size(); ++j) {
                    int k = adj[i].get(j);
                    // 把所有顶点k的入度减一，即表示删除当前放问的顶点i
                    inDegree[k]--;
                    if (inDegree[k] == 0) {
                        queue.add(k);
                    }
                }
            }
        }

        /**
         * 深度优先遍历：1、通过邻接表构造逆邻接表  2、递归处理每个顶点
         * 通过逆邻接表，先输出它所有的依赖顶点，剩下再输出自己（有一个逆转的思维）
         *
         * 可以用来检测图中是否存在环，如果最后输出的顶点数少于图中顶点数，则证明图中存在环
         */
        public void topoSortByDFS() {
            // 先构建逆邻接表，边 s->t 表示，s 依赖于 t，t 先于 s
            LinkedList<Integer>[] inverseAdj = new LinkedList[v];
            for (int i = 0; i < v; ++i) {
                inverseAdj[i] = new LinkedList<>();
            }
            // 通过邻接表生成逆邻接表
            for (int i = 0; i < v; ++i) {
                for (int j = 0; j < adj[i].size(); ++j) {
                    // i->w
                    int w = adj[i].get(j);
                    // w->i
                    inverseAdj[w].add(i);
                }
            }
            boolean[] visited = new boolean[v];
            // 深度优先遍历图
            for (int i = 0; i < v; ++i) {
                if (visited[i] == false) {
                    visited[i] = true;
                    dfs(i, inverseAdj, visited);
                }
            }
        }

        private void dfs(int vertex, LinkedList<Integer>[] inverseAdj, boolean[] visited) {
            for (int i = 0; i < inverseAdj[vertex].size(); ++i) {
                int w = inverseAdj[vertex].get(i);
                if (visited[w] == true) {
                    continue;
                }
                visited[w] = true;
                dfs(w, inverseAdj, visited);
            }
            // 先把 vertex 这个顶点可达的所有顶点都打印出来之后，再打印它自己（递归树）
            System.out.print("->" + vertex);
        }
    }
}



