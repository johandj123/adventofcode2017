import lib.InputUtil;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day14 {
    public static void main(String[] args) throws IOException {
        String input = InputUtil.readAsString("input14.txt");
        first(input);
    }

    private static void first(String input) {
        int count = 0;
        for (int i = 0; i < 128; i++) {
            byte[] hash = knotHash(input + "-" + i);
            for (byte b : hash) {
                count += Integer.bitCount(b & 0xff);
            }
        }
        System.out.println(count);
    }

    private static byte[] knotHash(String input) {
        List<Integer> integers = input.codePoints().boxed().collect(Collectors.toList());
        integers.addAll(List.of(17, 31, 73, 47, 23));
        Deque<Integer> deque = IntStream.range(0, 256).boxed().collect(Collectors.toCollection(ArrayDeque::new));
        int skip = 0;
        int forward = 0;
        for (int round = 0; round < 64; round++) {
            for (int length : integers) {
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
        byte[] result = new byte[16];
        for (int i = 0; i < result.length; i++) {
            int value = 0;
            for (int j = 0; j < 16; j++) {
                value ^= deque.removeFirst();
            }
            result[i] = (byte) value;
        }
        return result;
    }
}
