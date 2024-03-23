import lib.CharMatrix;
import lib.InputUtil;

import java.io.IOException;
import java.util.Objects;

public class Day19 {
    public static void main(String[] args) throws IOException {
        CharMatrix charMatrix = new CharMatrix(InputUtil.readAsLines("input19.txt"));
        CharMatrix.Position startPosition = null;
        for (int x = 0; x < charMatrix.getWidth(); x++) {
            if (charMatrix.get(x, 0) == '|') {
                startPosition = charMatrix.new Position(x, 0);
                break;
            }
        }
        CharMatrix.Position position = startPosition;
        Direction direction = new Direction(0, 1);
        StringBuilder sb = new StringBuilder();
        int steps = 0;
        while (position != null) {
            char c = position.get();
            if (c >= 'A' && c <= 'Z') {
                sb.append(c);
            }
            CharMatrix.Position p1 = direction.move(position);
            CharMatrix.Position p2 = direction.turnLeft().move(position);
            CharMatrix.Position p3 = direction.turnRight().move(position);
            if (p1.getUnbounded() != ' ') {
                position = p1;
            } else if (p2.getUnbounded() != ' ') {
                position = p2;
                direction = direction.turnLeft();
            } else if (p3.getUnbounded() != ' ') {
                position = p3;
                direction = direction.turnRight();
            } else {
                position = null;
            }
            steps++;
        }
        System.out.println(sb);
        System.out.println(steps);
    }

    static class Direction {
        final int dx;
        final int dy;

        public Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        CharMatrix.Position move(CharMatrix.Position position) {
            return position.add(dx, dy);
        }

        Direction turnLeft() {
            return new Direction(dy, -dx);
        }

        Direction turnRight() {
            return new Direction(-dy, dx);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Direction position = (Direction) o;
            return dx == position.dx && dy == position.dy;
        }

        @Override
        public int hashCode() {
            return Objects.hash(dx, dy);
        }
    }
}
