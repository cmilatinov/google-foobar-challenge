import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

    private static int sum(List<Integer> digitList) {
       return digitList.stream().reduce(0, Integer::sum);
    }

    private static int toNumber(List<Integer> digitList) {
        int value = 0;
        for (int i = digitList.size() - 1; i >= 0; i--)
            value += digitList.get(i) * Math.pow(10, digitList.size() - 1 - i);
        return value;
    }

    public static int solution(int[] digits) {
        // Create list
        List<Integer> digitList = new ArrayList<>();
        for (int digit : digits)
            digitList.add(digit);
        digitList.sort((a, b) -> b - a);

        // Split list by modulo
        final int divisor = 3;
        List<Integer>[] digitsByModulo = new ArrayList[divisor];
        for (int i = 0; i < divisor; i++) {
            final int modulo = i;
            digitsByModulo[modulo] = digitList.stream()
                            .filter(d -> d % divisor == modulo)
                            .collect(Collectors.toList());
        }

        for (int i = 0; i < divisor; i++) {
            System.out.println(i);
            System.out.println(digitsByModulo[i]);
            System.out.println();
        }


        // Compute remainder
        int remainder = sum(digitList) % divisor;

        // No remainder, the number is already divisible by 3
        if (remainder == 0)
            return toNumber(digitList);

        // If we have at least one digit with the same remainder as the sum,
        // return the array minus that one digit
        if (!digitsByModulo[remainder].isEmpty()) {
            digitList.remove(digitsByModulo[remainder].get(digitsByModulo[remainder].size() - 1));
            return toNumber(digitList);
        }

        // Sum has remainder 1, if we have at least 2 digits with remainder 2,
        // remove the smallest of those digits
        if (remainder == 1 && digitsByModulo[2].size() >= 2) {
            digitList.remove(digitsByModulo[2].get(digitsByModulo[2].size() - 1));
            digitList.remove(digitsByModulo[2].get(digitsByModulo[2].size() - 2));
            return toNumber(digitList);
        }

        // Sum has remainder 2, if we have at least 2 digits with remainder 1,
        // remove the smallest of those digits
        if (remainder == 2 && digitsByModulo[1].size() >= 2) {
            digitList.remove(digitsByModulo[1].get(digitsByModulo[1].size() - 1));
            digitList.remove(digitsByModulo[1].get(digitsByModulo[1].size() - 2));
            return toNumber(digitList);
        }

        return 0;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int []{ 1, 7, 3, 6, 4, 4, 3 }));
    }

}
