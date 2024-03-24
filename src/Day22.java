import lib.CharMatrix;
import lib.InputUtil;

import java.io.IOException;
import java.util.*;

public class Day22 {
    public static void main(String[] args) throws IOException {
        CharMatrix charMatrix = new CharMatrix(InputUtil.readAsLines("input22.txt"));
        first(charMatrix);
        second(charMatrix);
    }

    private static void first(CharMatrix charMatrix) {
        Set<Position> infected = new HashSet<>();
        int w1 = charMatrix.getWidth() / 2;
        int h1 = charMatrix.getHeight() / 2;
        for (int y = 0; y < charMatrix.getHeight(); y++) {
            for (int x = 0; x < charMatrix.getWidth(); x++) {
                if (charMatrix.get(x, y) == '#') {
                    infected.add(new Position(x - w1, y - h1));
                }
            }
        }
        Position current = new Position(0, 0);
        Position direction = new Position(0, -1);
        int infection = 0;
        for (int i = 0; i < 10000; i++) {
            if (!infected.contains(current)) {
                infected.add(current);
                direction = direction.turnLeft();
                infection++;
            } else {
                infected.remove(current);
                direction = direction.turnRight();
            }
            current = current.add(direction);
        }
        System.out.println(infection);
    }

    private static void second(CharMatrix charMatrix) {
        Map<Position, State> infected = new HashMap<>();
        int w1 = charMatrix.getWidth() / 2;
        int h1 = charMatrix.getHeight() / 2;
        for (int y = 0; y < charMatrix.getHeight(); y++) {
            for (int x = 0; x < charMatrix.getWidth(); x++) {
                if (charMatrix.get(x, y) == '#') {
                    infected.put(new Position(x - w1, y - h1), State.INFECTED);
                }
            }
        }
        Position current = new Position(0, 0);
        Position direction = new Position(0, -1);
        int infection = 0;
        for (int i = 0; i < 10000000; i++) {
            State state = infected.getOrDefault(current, State.CLEAN);
            switch (state) {
                case CLEAN:
                    infected.put(current, State.WEAKENED);
                    direction = direction.turnLeft();
                    break;
                case WEAKENED:
                    infected.put(current, State.INFECTED);
                    infection++;
                    break;
                case INFECTED:
                    infected.put(current, State.FLAGGED);
                    direction = direction.turnRight();
                    break;
                case FLAGGED:
                    infected.put(current, State.CLEAN);
                    direction = direction.turnAround();
                    break;
            }
            current = current.add(direction);
        }
        System.out.println(infection);
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

        public Position turnRight() {
            return new Position(-y, x);
        }

        public Position turnAround() {
            return new Position(-x, -y);
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

    enum State {
        CLEAN,
        WEAKENED,
        INFECTED,
        FLAGGED
    }
}
