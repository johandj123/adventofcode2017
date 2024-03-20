import lib.InputUtil;

import java.io.IOException;

public class Day1 {
    public static void main(String[] args) throws IOException {
        String input = InputUtil.readAsString("input1.txt");
        first(input);
        second(input);
    }

    private static void first(String input) {
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == input.charAt((i + 1) % input.length())) {
                count += (input.charAt(i) - '0');
            }
        }
        System.out.println(count);
    }

    private static void second(String input) {
        int count = 0;
        int half = input.length() / 2;
        for (int i = 0; i < half; i++) {
            if (input.charAt(i) == input.charAt(i + half)) {
                count += (2 * (input.charAt(i) - '0'));
            }
        }
        System.out.println(count);
    }
}
