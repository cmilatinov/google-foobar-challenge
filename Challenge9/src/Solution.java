import java.math.BigInteger;

public class Solution {

    private static final BigInteger ROOT_TWO_N = new BigInteger("141421356237309504880168872420969807856967187537694807317667973799073247846210703885038753432764157273");
    private static final BigInteger ROOT_TWO_D = new BigInteger("100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
    private static final BigInteger TWO = new BigInteger("2");
    private static final BigInteger MINUS_ONE = new BigInteger("-1");

    public static String solution(String strN) {

        // Beatty sequence:
        // A Beatty sequence is defined as the sequence containing the numbers
        // floor(nr) where n is an element of Z, n > 0, and r is an element of Q

        // Let S(a, n) be the partial sum up to the n-th number of the Beatty sequence floor(an)
        // We are looking to compute S(a, n) for large values of n where a = sqrt(2)

        // 1.
        // Let m = floor(an)
        // an - floor(an) <= 1
        // an - 1 <= floor(an)
        // n - 1/a <= floor(an)/a
        // n - 1/a <= m/a
        // n - 1 < n - 1/a <= m/a since 1 < a
        // n - 1 < m/a

        // 2.
        // m <= an < m + 1
        // m/a <= n < (m + 1)/a
        // m/a <= n
        // n - 1 < m/a <= n (from 1.)
        // n = ceil(m/a)

        // Beatty's Theorem
        // Given two irrational numbers a and b, their corresponding
        // Beatty sequences Ba and Bb partition N+ (the set of all natural numbers, starting at 1)
        // if and only if 1/a + 1/b = 1

        // 3.
        // 1/a + 1/b = 1
        // m/a + m/b = m
        // ceil(m/a) + floor(m/b) = m       (because 1/a + 1/b = 1)
        // n + floor(m/b) = m               (from 2.)
        // let n' = floor(m/b) = m - n = floor(na) - n = floor(na - n) = floor(n(a - 1))
        // n' = m - n
        // m = n + n'

        // From 3.
        // S(a, n) + S(b, floor(m/b)) = Sum from i = 1 to m of i
        // = m(m + 1)/2 = (n + n')(n + n' + 1)/2

        // 4.
        // S(a, n) = (n + n')(n + n' + 1)/2 - S(b, n')

        // 5.
        // S(b, n') = sum k -> n' { floor((2 + sqrt(2)) * k) }
        // S(b, n') = sum k -> n' { floor(2k + k * sqrt(2)) }
        // S(b, n') = sum k -> n' { 2k + floor(k * sqrt(2)) }
        // S(b, n') = sum k -> n' { 2k } + sum k -> n' { floor(k * sqrt(2)) }
        // S(b, n') = 2n'(n' + 1)/2 + S(a, n')
        // S(b, n') = n'(n' + 1) + S(a, n')

        // From 4. and 5.
        // S(a, n) = (n + n')(n + n' + 1)/2 - n'(n' + 1) - S(a, n')

        // We now have an explicit recursive formula with which to compute S(a, n)

        // NOTE: You guys should allow unicode characters because it makes the proof more legible.

        BigInteger n = new BigInteger(strN);
        BigInteger nprime;
        BigInteger sign = BigInteger.ONE;
        BigInteger sum = BigInteger.ZERO;

        do {

            // let n' = floor(n(a - 1)) = floor(an) - n
            nprime = n.multiply(ROOT_TWO_N).divide(ROOT_TWO_D).subtract(n);

            // Compute n + n'
            BigInteger nPlusNPrime = n.add(nprime);

            // Compute (n + n')(n + n' + 1)/2 - n'(n' + 1)
            BigInteger firstPart = nPlusNPrime.multiply(nPlusNPrime.add(BigInteger.ONE)).divide(TWO)
                    .subtract(nprime.multiply(nprime.add(BigInteger.ONE)));

            // Add to sum
            sum = sum.add(firstPart.multiply(sign));

            // Set new n
            n = nprime;

            // Alternate sign
            sign = sign.multiply(MINUS_ONE);

        } while (nprime.compareTo(BigInteger.ZERO) > 0);

        return sum.toString();
    }

    public static void main(String[] args) {
        System.out.println(solution("64236194105921003280261498745665996888740679561673918595728886424734635858868644968223860069833526427990562831656139139425576490620651860216472630333629750756978706066068564981600927187092921531323682813569889370974165044745909605374727965244770940992412387106144705439867436473384774548191008728862221495895295911878"));
    }

}
