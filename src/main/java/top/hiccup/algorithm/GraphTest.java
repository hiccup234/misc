package top.hiccup.algorithm;

/**
 * 图相关的问题主要集中在深度优先搜索（depth head search）和广度优先搜索（breath head search）
 *
 * @author wenhy
 * @date 2019/2/22
 */
public class GraphTest {

    public static void breathFirstSearch(GraphNode root, int x) {
        if (root.getVal() == x) {
            System.out.println("find in root");
        }
        Queue queue = new Queue();
        root.setVisited(true);
        queue.enqueue(root);

        GraphNode c = queue.dequeue();
        while (c != null) {
            queue.dequeue();
            for (GraphNode n : c.getNeighbors()) {

                if (!n.isVisited()) {
                    System.out.println(n);
                    n.setVisited(true);
                    if (n.getVal() == x) {
                        System.out.println("Find " + n);
                    }
                    queue.enqueue(n);
                }
            }
            c = queue.dequeue();
        }
    }

    public static void main(String[] args) {
        GraphNode n1 = new GraphNode(1);
        GraphNode n2 = new GraphNode(2);
        GraphNode n3 = new GraphNode(3);
        GraphNode n4 = new GraphNode(4);
        GraphNode n5 = new GraphNode(5);

        n1.setNeighbors(new GraphNode[]{n2, n3, n5});
        n2.setNeighbors(new GraphNode[]{n1, n4});
        n3.setNeighbors(new GraphNode[]{n1, n4, n5});
        n4.setNeighbors(new GraphNode[]{n2, n3, n5});
        n5.setNeighbors(new GraphNode[]{n1, n3, n4});

        breathFirstSearch(n1, 5);
    }
}


class GraphNode {
    private int val;
    private GraphNode next;
    private GraphNode[] neighbors;
    private boolean visited;

    public GraphNode(int x) {
        val = x;
    }

    public GraphNode(int x, GraphNode[] n) {
        val = x;
        neighbors = n;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public GraphNode getNext() {
        return next;
    }

    public void setNext(GraphNode next) {
        this.next = next;
    }

    public GraphNode[] getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(GraphNode[] neighbors) {
        this.neighbors = neighbors;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public String toString() {
        return "value: " + this.val;
    }
}

class Queue {
    GraphNode head, tail;

    public void enqueue(GraphNode n) {
        if (tail != null) {
            tail.setNext(n);
        } else {
            tail = head = n;
        }
    }

    public GraphNode dequeue() {
        if (head != null) {
            GraphNode node = head;
            head = head.getNext();
            return node;
        }
        return head;
    }
}