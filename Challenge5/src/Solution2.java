import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Solution2 {

    static class Node {

        public int x, y;
        public int wallsBroken = 0;
        public boolean visited = false;
        public int level = 0;
        public Node parent = null;

        private Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "(" + x + ", " + y + ") " + wallsBroken;
        }

    }

    public static int solution(int[][] map) {

        final int w = map.length;
        final int h = map[0].length;

        Node[][] nodes = new Node[w][h];
        for (int x = 0; x < w; x++)
            for (int y = 0; y < h; y++)
                nodes[x][y] = new Node(x, y);

        // Add start node to queue
        final Queue<Node> queue = new ArrayDeque<>();
        nodes[0][0].level = 1;
        queue.add(nodes[0][0]);

        while (!queue.isEmpty()) {

            Node q = queue.remove();

            // We hit the goal
            if (q.x == w - 1 && q.y == h - 1)
                break;

            // Generate cardinal neighbours
            List<Node> neighbours = new ArrayList<>();
            if (q.x - 1 >= 0) {
                Node s = new Node(q.x - 1, q.y);
                s.wallsBroken = q.wallsBroken;
                if (map[q.x - 1][q.y] == 0) {
                    neighbours.add(s);
                } else if (q.wallsBroken < 1) {
                    s.wallsBroken = 1;
                    neighbours.add(s);
                }
            }
            if (q.x + 1 < w) {
                Node s = new Node(q.x + 1, q.y);
                s.wallsBroken = q.wallsBroken;
                if (map[q.x + 1][q.y] == 0) {
                    neighbours.add(s);
                } else if (q.wallsBroken < 1) {
                    s.wallsBroken = 1;
                    neighbours.add(s);
                }
            }
            if (q.y - 1 >= 0) {
                Node s = new Node(q.x, q.y - 1);
                s.wallsBroken = q.wallsBroken;
                if (map[q.x][q.y - 1] == 0) {
                    neighbours.add(s);
                } else if (q.wallsBroken < 1) {
                    s.wallsBroken = 1;
                    neighbours.add(s);
                }
            }
            if (q.y + 1 < h) {
                Node s = new Node(q.x, q.y + 1);
                s.wallsBroken = q.wallsBroken;
                if (map[q.x][q.y + 1] == 0) {
                    neighbours.add(s);
                } else if (q.wallsBroken < 1) {
                    s.wallsBroken = 1;
                    neighbours.add(s);
                }
            }

            // Loop for every neighbour and add to queue if not visited
            for (Node n : neighbours) {
                if (!nodes[n.x][n.y].visited || n.wallsBroken < nodes[n.x][n.y].wallsBroken) {
                    n.level = q.level + 1;
                    n.visited = true;
                    n.parent = q;
                    nodes[n.x][n.y] = n;
                    queue.add(n);
                }
            }

        }

        // Return end node level
        return nodes[w - 1][h - 1].level;
    }

    public static void main(String[] args) {
        int[][] map = {
                {0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0}
        };
        System.out.println(solution(map));
    }

}
