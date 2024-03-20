import lib.InputUtil;

import java.io.IOException;

public class Day3 {
    public static void main(String[] args) throws IOException {
        int input = Integer.parseInt(InputUtil.readAsString("input3.txt"));
        System.out.println(distance(input));
    }

    private static int distance(int value) {
        int level = 0;
        while (levelSquares(level) < value) {
            level++;
        }
        if (level == 0) {
            return 0;
        }
        int count = levelSquares(level) - levelSquares(level - 1);
        int countSide = count / 4;
        int x = (value - levelSquares(level - 1) - 1) % (count / 4);
        int middle = (countSide / 2) - 1;
        return level + Math.abs(x - middle);
    }

    private static int levelSquares(int level)
    {
        int side = 2 * level + 1;
        return side * side;
    }
}
