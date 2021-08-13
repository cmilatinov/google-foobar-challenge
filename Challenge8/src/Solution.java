import java.util.Arrays;

public class Solution {

    private static int choose(int n, int r) {
        return factorial(n) / (factorial(r) * factorial(n - r));
    }

    private static int factorial(int n) {
        int total = 1;
        for (int i = 1; i <= n; i++) {
            total *= i;
        }
        return total;
    }

    public static int[][] solution(int n, int r) {

        // Number of unique keys
        // d = n C (r - 1)
        int d = choose(n, r - 1);

        // Number of times to give each key
        // t = n - r + 1
        int t = n - r + 1;

        // Number of keys for each bunny
        // k = d * t / n
        int k = d * t / n;

        // Solution array and indices
        int[][] solution = new int[n][k];
        int[] solutionIndexes = new int[n];

        int[] keyIndexes = new int[t];
        for (int key = 0; key < d; key++) {

            if (key == 0) {

                // Set first bunny with consecutive keys
                for (int i = 0; i < keyIndexes.length; i++) {
                    keyIndexes[i] = i;
                }

            } else if (keyIndexes[keyIndexes.length - 1] + 1 >= n) {

                // Find first non-consecutive integer
                int lastN = n - 1;
                int firstNonConsecutiveIndex = 0;
                for (int i = 0; i < keyIndexes.length; i++) {
                    if (keyIndexes[keyIndexes.length - 1 - i] != lastN - i) {
                        firstNonConsecutiveIndex = keyIndexes.length - 1 - i;
                        break;
                    }
                }

                // Increment the first non-consecutive integer
                // Set the rest of the indices to be consecutive to the first
                int startValue = keyIndexes[firstNonConsecutiveIndex]++;
                for (int i = firstNonConsecutiveIndex + 1; i < keyIndexes.length; i++) {
                    keyIndexes[i] = startValue + i - firstNonConsecutiveIndex + 1;
                }

            } else {

                // Increment the last index by 1
                keyIndexes[keyIndexes.length - 1]++;

            }

            // Set the corresponding keys in the solution
            for (int index : keyIndexes) {
                solution[index][solutionIndexes[index]++] = key;
            }
        }

        return solution;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(solution(9,  8)));
    }

}
