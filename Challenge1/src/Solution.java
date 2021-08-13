import java.util.HashMap;
import java.util.Map;

public class Solution {

    private static final String BRAILLE_CAPITALIZATION_MARK = "000001";

    private static final Map<Character, String> BRAILLE_CHARACTERS = new HashMap<Character, String>() {{
        put('a', "100000");
        put('b', "110000");
        put('c', "100100");
        put('d', "100110");
        put('e', "100010");
        put('f', "110100");
        put('g', "110110");
        put('h', "110010");
        put('i', "010100");
        put('j', "010110");
        put('k', "101000");
        put('l', "111000");
        put('m', "101100");
        put('n', "101110");
        put('o', "101010");
        put('p', "111100");
        put('q', "111110");
        put('r', "111010");
        put('s', "011100");
        put('t', "011110");
        put('u', "101001");
        put('v', "111001");
        put('w', "010111");
        put('x', "101101");
        put('y', "101111");
        put('z', "101011");
        put(' ', "000000");
    }};

    public static String solution(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(BRAILLE_CAPITALIZATION_MARK);
            }
            sb.append(BRAILLE_CHARACTERS.get(Character.toLowerCase(c)));
        }
        return sb.toString();
    }

}
