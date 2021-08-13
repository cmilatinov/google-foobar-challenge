import java.util.Arrays;

public class Solution {

    private static void simplify(int[] fraction) {
        int gcd = 1;
        for (int i = 1; i <= fraction[0] && i <= fraction[1]; i++) {
            if (fraction[0] % i == 0 && fraction[1] % i == 0) {
                gcd = i;
            }
        }
        fraction[0] /= gcd;
        fraction[1] /= gcd;
    }

    private static void invert(int[] fraction) {
        int temp = fraction[0];
        fraction[0] = fraction[1];
        fraction[1] = temp;
    }

    private static int[] mul(int[] fraction1, int[] fraction2) {
        return new int[] { fraction1[0] * fraction2[0], fraction1[1] * fraction2[1] };
    }

    private static int[] add(int[] fraction1, int[] fraction2) {
        int denominator = fraction1[1] * fraction2[1];
        int numerator = (fraction1[0] * fraction2[1]) + (fraction2[0] * fraction1[1]);
        return new int[] { numerator, denominator };
    }

    private static int[] sub(int[] fraction1, int[] fraction2) {
        int[] frac2 = { -fraction2[0], fraction2[1] };
        return add(fraction1, frac2);
    }

    private static boolean isLower(int[] fraction, int value) {
        return (float)fraction[0] / (float)fraction[1] < value;
    }

    public static int[] solution(int[] pegs) {

        if (pegs.length < 2)
            return new int[] { -1, -1 };
        else if (pegs.length == 2) {
            int[] factor = { 2, 3 };
            int[] dist = { pegs[1] - pegs[0], 1 };
            int[] result = mul(factor, dist);
            simplify(result);
            return result;
        }

        int[][] solutionVector = new int[pegs.length - 1][2];

        // Compute distances between pegs
        for (int i = 0; i < pegs.length - 1; i++) {
            solutionVector[i][0] = pegs[i + 1] - pegs[i];
            solutionVector[i][1] = 1;
        }

        // Fill last row of matrix with zeros
        int[] constant = { 1, 2 };
        final int lastIndex = solutionVector.length - 1;
        for (int i = 0; i < solutionVector.length - 1; i++) {
            constant[0] *= -1;
            solutionVector[lastIndex] = add(mul(constant, solutionVector[i]), solutionVector[lastIndex]);
        }

        // Multiply last row by inverse of the coefficient at the end
        constant[0] += 2;
        invert(constant);
        solutionVector[lastIndex] = mul(constant, solutionVector[lastIndex]);

        // Radius less than 1, no solution
        if (isLower(solutionVector[lastIndex], 1))
            return new int[] { -1, -1 };

        // Fill above the diagonal with zeros
        for (int i = solutionVector.length - 2; i >= 0; i--) {
            solutionVector[i] = sub(solutionVector[i], solutionVector[i + 1]);
            // Radius less than 1, no solution
            if (isLower(solutionVector[i], 1))
                return new int[] { -1, -1 };
        }

        // Simplify and return the resulting fraction
        simplify(solutionVector[0]);
        return solutionVector[0];
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new int[]{ 1, 7, 11, 15, 19 })));
    }

}
