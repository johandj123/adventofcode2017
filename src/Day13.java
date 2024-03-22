import lib.InputUtil;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class Day13 {
    public static void main(String[] args) throws IOException {
        Map<Integer, Integer> map = InputUtil.readAsLines("input13.txt").stream()
                .map(s -> s.split(": "))
                .collect(Collectors.toMap(sp -> Integer.parseInt(sp[0]), sp -> Integer.parseInt(sp[1])));
        first(map);
        second(map);
    }

    private static void first(Map<Integer, Integer> map) {
        int severity = map.entrySet().stream()
                .filter(entry -> (entry.getKey() % period(entry.getValue())) == 0)
                .mapToInt(entry -> entry.getKey() * entry.getValue())
                .sum();
        System.out.println(severity);
    }

    private static void second(Map<Integer, Integer> map) {
        for (int i = 0; ; i++) {
            if (!caught(map, i)) {
                System.out.println(i);
                break;
            }
        }
    }

    private static boolean caught(Map<Integer, Integer> map,int startTime) {
        return map.entrySet().stream()
                .anyMatch(entry -> ((entry.getKey() + startTime) % period(entry.getValue())) == 0);
    }

    private static int period(int range) {
        return 2 * (range - 1);
    }
}
