import java.math.BigInteger;

public class Solution {

    public static int solution(String number) {
        String binary = new BigInteger(number).toString(2);

        int count = 0;
        while (!binary.equals("1")) {
            char last = binary.charAt(binary.length() - 1);
            if (last == '0') {
                int zeroCount = countEnd(binary, '0');
                binary = binary.substring(0, binary.length() - zeroCount);
                count += zeroCount;
            } else if (last == '1') {
                String add = new BigInteger(binary, 2)
                        .add(new BigInteger("1"))
                        .toString(2);
                String sub = new BigInteger(binary, 2)
                        .subtract(new BigInteger("1"))
                        .toString(2);

                if (binary.equals("11")) {
                    binary = sub;
                } else {
                    int addCount = countEnd(add, '0');
                    int subCount = countEnd(sub, '0');
                    if (addCount > subCount)
                        binary = add;
                    else
                        binary = sub;
                }
                count++;
            }
        }

        return count;
    }

    private static int countEnd(String s, char c) {
        int i = s.length() - 1;
        while (i > 0 && s.charAt(i) == c)
            i--;
        return s.length() - 1 - i;
    }

    public static void main(String[] args) {
        System.out.println("\n" + solution("59118"));
    }

}
