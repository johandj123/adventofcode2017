import lib.InputUtil;

import java.io.IOException;
import java.util.List;

public class Day2 {
    public static void main(String[] args) throws IOException {
        List<String> input = InputUtil.readAsLines("input2.txt");
        first(input);
        second(input);
    }

    private static void first(List<String> input) {
        int checksum = 0;
        for (String s : input) {
            List<Integer> list = InputUtil.splitIntoIntegers(s);
            int max = list.stream().mapToInt(x -> x).max().orElseThrow();
            int min = list.stream().mapToInt(x -> x).min().orElseThrow();
            checksum += (max - min);
        }
        System.out.println(checksum);
    }

    private static void second(List<String> input) {
        int sum = 0;
        for (String s : input) {
            List<Integer> list = InputUtil.splitIntoIntegers(s);
            outer:
            for (int i : list) {
                for (int j : list) {
                    if (i != j && (i % j) == 0) {
                        sum += (i / j);
                        break outer;
                    }
                }
            }
        }
        System.out.println(sum);
    }
}
