import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {

    public static int solution(int[] entrances, int[] exits, int[][] path) {

        // We are given a directed weighted graph
        // To find the maximum number of bunnies that can get to escape pods:

        // 1. Remove corridors going from entrance to entrance, from exit to exit,
        // and self-looping corridors as they are effectively useless and only
        // mess up the calculation

        // 2. For each node of the graph, compute its throughput
        // Throughput is defined as the minimum value between the sum
        // of its inputs and the sum of its outputs. Starting nodes
        // are considered to have infinite input, and end nodes are
        // considered to have infinite output.

        // 3. Starting with the end nodes of the graph, sum their
        // throughput values and keep track of the lowest value encountered.

        // 4. Repeat the process, this time finding the subset of nodes directly
        // connected to the end nodes of the graph. Again, sum their throughput
        // values and keep track of the lowest value found. Repeat the process,
        // each time finding the subset of nodes that are 1 edge further away
        // from the last until we have visited all the nodes in the graph.
        // The lowest subset throughput value sum is the answer to the problem.

        int[] throughputValues = new int[path.length];
        for (int i = 0; i < path.length; i++) {

            // Check if node is a start or end node
            final int finalI = i;
            boolean isStartI = Arrays.stream(entrances).anyMatch(e -> e == finalI);
            boolean isEndI = Arrays.stream(exits).anyMatch(e -> e == finalI);

            // Sum outputs and inputs
            int sumOut = 0, sumIn = 0;
            for (int j = 0; j < path[i].length; j++) {
                final int finalJ = j;
                boolean isStartJ = Arrays.stream(entrances).anyMatch(e -> e == finalJ);
                boolean isEndJ = Arrays.stream(exits).anyMatch(e -> e == finalJ);

                // Do not count self-looping corridors
                if (i == j)
                    continue;

                // Do not count if corridor connects two entrances or two exits
                if ((isStartI && isStartJ) || (isEndI && isEndJ))
                    continue;

                sumOut += path[i][j];
                sumIn += path[j][i];
            }

            if (isStartI)
                sumIn = Integer.MAX_VALUE;
            if (isEndI)
                sumOut = Integer.MAX_VALUE;

            throughputValues[i] = Math.min(sumIn, sumOut);

        }

        int minValue = Integer.MAX_VALUE;
        Set<Integer> unvisitedNodes = new LinkedHashSet<>();
        for (int i = 0; i < path.length; i++)
            unvisitedNodes.add(i);
        Set<Integer> nodeSubset = Arrays.stream(exits)
                .boxed()
                .collect(Collectors.toCollection(LinkedHashSet::new));

        while (!unvisitedNodes.isEmpty() && !nodeSubset.isEmpty()) {

            // Compute throughput sum and store it if it is the smallest value
            int throughputSum = nodeSubset.stream()
                    .reduce(0, (sum, n) -> sum + throughputValues[n]);
            if (throughputSum < minValue)
                minValue = throughputSum;

            // Get new node subset, intersect with unvisited nodes
            Set<Integer> nextNodeSubset = new LinkedHashSet<>();
            nodeSubset.forEach(n -> {
                for (int i = 0; i < path.length; i++)
                    if (path[i][n] != 0)
                        nextNodeSubset.add(i);
            });
            nextNodeSubset.retainAll(unvisitedNodes);

            // Remove visited nodes
            unvisitedNodes.removeAll(nodeSubset);
            nodeSubset.clear();

            // Change node subset to new nodes
            nodeSubset.addAll(nextNodeSubset);
        }

        return minValue;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[]{0, 1}, new int[]{4, 5}, new int[][]{
                {0, 3, 0, 0, 5, 0},
                {0, 0, 4, 9, 0, 0},
                {0, 0, 0, 0, 6, 3},
                {0, 0, 0, 0, 0, 2},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
        }));
    }

}
