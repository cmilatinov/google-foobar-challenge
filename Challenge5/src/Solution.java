import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Solution {

    static class Node {
        public int h = 0, g = 0, f = 0;
        public int x, y;
        public int wallsBroken = 0;
        public Node parent = null;

        private Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean equals(Object obj) {
            if (obj == null)
                return false;

            if (!(obj instanceof Node))
                return false;

            Node other = (Node) obj;

            return other.x == x && other.y == y;
        }

        public String toString() {
            return "(" + x + ", " + y + ") " + wallsBroken;
        }

    }

    public static int solution(int[][] map) {

        final int w = map.length;
        final int h = map[0].length;

        List<Node> open = new ArrayList<>();
        List<Node> closed = new ArrayList<>();

        final Node start = new Node(0, 0);
        start.g = 1;
        final Node goal = new Node(w - 1, h - 1);
        goal.f = Integer.MAX_VALUE;

        open.add(start);

        out:
        while (!open.isEmpty()) {

            // Get node with least f value
            open.sort(Comparator.comparingInt(a -> a.f));
            Node q = open.get(0);
            open.remove(0);

            // Generate successor nodes
            List<Node> successors = new ArrayList<>();
            if (q.x - 1 >= 0) {
                Node s = new Node(q.x - 1, q.y);
                s.wallsBroken = q.wallsBroken;
                s.parent = q;
                if (map[q.x - 1][q.y] == 0) {
                    successors.add(s);
                } else if (q.wallsBroken < 1) {
                    s.wallsBroken = 1;
                    successors.add(s);
                }
            }
            if (q.x + 1 < w) {
                Node s = new Node(q.x + 1, q.y);
                s.wallsBroken = q.wallsBroken;
                s.parent = q;
                if (map[q.x + 1][q.y] == 0) {
                    successors.add(s);
                } else if (q.wallsBroken < 1) {
                    s.wallsBroken = 1;
                    successors.add(s);
                }
            }
            if (q.y - 1 >= 0) {
                Node s = new Node(q.x, q.y - 1);
                s.wallsBroken = q.wallsBroken;
                s.parent = q;
                if (map[q.x][q.y - 1] == 0) {
                    successors.add(s);
                } else if (q.wallsBroken < 1) {
                    s.wallsBroken = 1;
                    successors.add(s);
                }
            }
            if (q.y + 1 < h) {
                Node s = new Node(q.x, q.y + 1);
                s.wallsBroken = q.wallsBroken;
                s.parent = q;
                if (map[q.x][q.y + 1] == 0) {
                    successors.add(s);
                } else if (q.wallsBroken < 1) {
                    s.wallsBroken = 1;
                    successors.add(s);
                }
            }

            // Loop for each successor
            for (Node s : successors) {

                // Compute g, h, f
                s.g = q.g + 1;
                s.h = Math.abs(goal.x - s.x) + Math.abs(goal.y - s.y);
                s.f = s.g + s.h;

                // If s is the goal and s is the fastest path found, overwrite the current goal node
                if (s.equals(goal)) {
                    goal.g = s.g;
                    goal.h = s.h;
                    goal.f = s.f;
                    goal.wallsBroken = s.wallsBroken;
                    goal.parent = s.parent;
                    break out;
                }

                // If successor is in open node list with lower f, skip
                Optional<Node> openNode = open.stream()
                        .filter(n -> n.equals(s) && n.f < s.f && n.wallsBroken <= s.wallsBroken)
                        .findFirst();
                if (openNode.isPresent())
                    continue;

                // If successor is in closed node list, skip
                // Otherwise, add it to open node list
                Optional<Node> closedNode = closed.stream()
                        .filter(n -> n.equals(s) && n.f < s.f && n.wallsBroken <= s.wallsBroken)
                        .findFirst();
                if (!closedNode.isPresent())
                    open.add(s);

            }

            closed.add(q);

        }

        Node current = goal;
        while (current != null) {
            System.out.println(current);
            current = current.parent;
        }

        return goal.f;
    }

    private static void printMap(int[][] map) {
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                System.out.print("\t" + map[x][y]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        long time = System.nanoTime();
        int[][] map = new int[][]{
                {0, 0, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 1},
                {0, 0, 1, 0, 0, 0},
                {1, 1, 1, 1, 0, 1},
                {0, 0, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0}
        };
        printMap(map);
        System.out.println(solution(map));
        System.out.println((System.nanoTime() - time) / 1e9f + " s");
    }

}
