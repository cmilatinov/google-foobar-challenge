public class Solution {

    private static boolean divides(int a, int b) {
        return b % a == 0;
    }

    public static int solution(int[] l) {
        int total = 0;
        for (int i = 0; i < l.length; i++) {
            for (int j = i + 1; j < l.length; j++) {

                if (!divides(l[i], l[j]))
                    continue;

                for (int k = j + 1; k < l.length; k++) {
                    if (divides(l[j], l[k])) {
                        total++;
                    }
                }
            }
        }

        return total;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[] { 1, 2, 3, 4, 5, 6 }));
    }

}
