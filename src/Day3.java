import lib.InputUtil;

import java.io.IOException;
import java.util.*;

public class Day3 {
    public static void main(String[] args) throws IOException {
        int input = Integer.parseInt(InputUtil.readAsString("input3.txt"));
        first(input);
        second(input);
    }

    private static void first(int input) {
        int result = 0;
        int level = 0;
        while (levelSquares(level) < input) {
            level++;
        }
        if (level != 0) {
            int count = levelSquares(level) - levelSquares(level - 1);
            int countSide = count / 4;
            int x = (input - levelSquares(level - 1) - 1) % (count / 4);
            int middle = (countSide / 2) - 1;
            result = level + Math.abs(x - middle);
        }
        System.out.println(result);
    }

    private static void second(int input) {
        Map<Position, Integer> map = new HashMap<>();
        map.put(new Position(0, 0), 1);
        Position current = new Position(1, 0);
        map.put(current, 1);
        Position direction = new Position(0, -1);
        while (map.get(current) < input) {
            Position next = current.add(direction.turnLeft());
            if (!map.containsKey(next)) {
                direction = direction.turnLeft();
            } else {
                next = current.add(direction);
            }
            if (map.containsKey(next)) {
                throw new IllegalStateException("Next position already filled; this should not occur");
            }
            int value = next.getNeighbours().stream()
                    .mapToInt(key -> map.getOrDefault(key, 0))
                    .sum();
            map.put(next, value);
            current = next;
        }
        System.out.println(map.get(current));
    }

    private static int levelSquares(int level)
    {
        int side = 2 * level + 1;
        return side * side;
    }

    static class Position {
        final int x;
        final int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position add(Position o) {
            return new Position(x + o.x, y + o.y);
        }

        public Position turnLeft() {
            return new Position(y, -x);
        }

        public int manhattanNorm() {
            return Math.abs(x) + Math.abs(y);
        }

        public List<Position> getNeighbours() {
            List<Position> result = new ArrayList<>();
            for (int dy = -1; dy <= 1; dy++) {
                for (int dx= -1; dx <= 1; dx++) {
                    if (dx != 0 || dy != 0) {
                        result.add(new Position(x + dx, y + dy));
                    }
                }
            }
            return result;
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
