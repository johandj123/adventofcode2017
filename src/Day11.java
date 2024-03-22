import lib.InputUtil;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class Day11 {
    public static void main(String[] args) throws IOException {
        String[] moves = InputUtil.readAsString("input11.txt").split(",");
        HexPosition child = new HexPosition(0, 0);
        int maxDistance = 0;
        for (String move : moves) {
            child = child.move(move);
            maxDistance = Math.max(maxDistance, child.getDistanceFromOrigin());
        }
        System.out.println(child.getDistanceFromOrigin());
        System.out.println(maxDistance);
    }

    static class HexPosition {
        static final Map<String, HexPosition> DIRECTIONS = Map.of(
                "n", new HexPosition(0, -2),
                "nw", new HexPosition(-1, -1),
                "ne", new HexPosition(1, -1),
                "sw", new HexPosition(-1, 1),
                "se", new HexPosition(1, 1),
                "s", new HexPosition(0, 2)
        );

        final int x;
        final int y;

        public HexPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public HexPosition move(String direction) {
            HexPosition delta = DIRECTIONS.get(direction);
            if (delta == null) {
                throw new IllegalArgumentException("Unknown direction " + delta);
            }
            return add(delta);
        }

        private HexPosition add(HexPosition delta) {
            return new HexPosition(x + delta.x, y + delta.y);
        }

        public int getDistanceFromOrigin() {
            int xx = Math.abs(x);
            int yy = Math.abs(y);
            return xx / 2 + yy / 2 + (xx % 2);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HexPosition that = (HexPosition) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
