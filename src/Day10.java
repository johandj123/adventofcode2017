import lib.InputUtil;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day10 {
    public static void main(String[] args) throws IOException {
        String input = InputUtil.readAsString("input10.txt");
        first(input);
        second(input);
    }

    private static void first(String input) {
        List<Integer> integers = InputUtil.extractPositiveIntegers(input);
        List<Integer> result = knotHashRounds(integers, 1);
        System.out.println(result.get(0) * result.get(1));
    }

    private static void second(String input) {
        List<Integer> integers = input.codePoints().boxed().collect(Collectors.toList());
        integers.addAll(List.of(17, 31, 73, 47, 23));
        List<Integer> result = knotHashRounds(integers, 64);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.size(); i += 16) {
            int value = 0;
            for (int j = 0; j < 16; j++) {
                value ^= result.get(i + j);
            }
            sb.append(String.format("%02x", value));
        }
        System.out.println(sb);
    }

    private static List<Integer> knotHashRounds(List<Integer> input, int roundCount) {
        Deque<Integer> deque = IntStream.range(0, 256).boxed().collect(Collectors.toCollection(ArrayDeque::new));
        int skip = 0;
        int forward = 0;
        for (int round = 0; round < roundCount; round++) {
            for (int length : input) {
                List<Integer> take = new ArrayList<>();
                for (int i = 0; i < length; i++) {
                    take.add(deque.removeFirst());
                }
                Collections.reverse(take);
                for (int i : take) {
                    deque.addLast(i);
                    forward++;
                }
                for (int i = 0; i < skip; i++) {
                    deque.addLast(deque.removeFirst());
                    forward++;
                }
                skip++;
            }
            forward = forward % deque.size();
        }
        for (int i = 0; i < forward; i++) {
            deque.addFirst(deque.removeLast());
        }
        return new ArrayList<>(deque);
    }
}
