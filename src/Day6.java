import lib.InputUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day6 {
    public static void main(String[] args) throws IOException {
        List<Integer> input = InputUtil.readAsIntegers("input6.txt");
        Map<List<Integer>, Integer> seen = new HashMap<>(Map.of(input, 0));
        int steps = 0;
        while (true) {
            redistribute(input);
            steps++;
            if (seen.containsKey(input)) {
                break;
            }
            seen.put(input, steps);
        }
        System.out.println(steps);
        System.out.println(steps - seen.get(input));
    }

    private static void redistribute(List<Integer> list) {
        int maxIndex = Integer.MIN_VALUE;
        int maxValue = Integer.MIN_VALUE;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > maxValue) {
                maxIndex = i;
                maxValue = list.get(i);
            }
        }
        int index = (maxIndex + 1) % list.size();
        int dist = maxValue;
        list.set(maxIndex, 0);
        while (dist > 0) {
            list.set(index, list.get(index) + 1);
            index = (index + 1) % list.size();
            dist--;
        }
    }
}
