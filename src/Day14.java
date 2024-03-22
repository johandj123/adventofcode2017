import lib.GraphUtil;
import lib.InputUtil;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day14 {
    public static void main(String[] args) throws IOException {
        String input = InputUtil.readAsString("input14.txt");
        first(input);
        second(input);
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

    private static void second(String input) {
        Set<Position> positions = new HashSet<>();
        for (int y = 0; y < 128; y++) {
            byte[] hash = knotHash(input + "-" + y);
            int x = 0;
            for (byte b : hash) {
                String s = Integer.toBinaryString((b & 0xff) | 0x100).substring(1);
                for (char c : s.toCharArray()) {
                    if (c == '1') {
                        positions.add(new Position(x, y));
                    }
                    x++;
                }
            }
        }
        var components = GraphUtil.components(positions, position -> Position.DIRECTIONS.stream().map(position::add).filter(positions::contains).collect(Collectors.toList()));
        System.out.println(components.size());
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

    static class Position {
        static final List<Position> DIRECTIONS = List.of(
                new Position(-1, 0),
                new Position(1, 0),
                new Position(0, -1),
                new Position(0, 1)
        );

        final int x;
        final int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Position add(Position o) {
            return new Position(x + o.x, y + o.y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
