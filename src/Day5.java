import lib.InputUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day5 {
    public static void main(String[] args) throws IOException {
        List<Integer> input = InputUtil.readAsIntegers("input5.txt");
        first(new ArrayList<>(input));
        second(new ArrayList<>(input));
    }

    private static void first(List<Integer> input) {
        int current = 0;
        int steps = 0;
        while (0 <= current && current < input.size()) {
            int next = current + input.get(current);
            input.set(current, input.get(current) + 1);
            current = next;
            steps++;
        }
        System.out.println(steps);
    }

    private static void second(List<Integer> input) {
        int current = 0;
        int steps = 0;
        while (0 <= current && current < input.size()) {
            int next = current + input.get(current);
            if (input.get(current) >= 3) {
                input.set(current, input.get(current) - 1);
            } else {
                input.set(current, input.get(current) + 1);
            }
            current = next;
            steps++;
        }
        System.out.println(steps);
    }
}
