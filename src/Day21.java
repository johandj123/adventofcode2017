import lib.CharMatrix;
import lib.InputUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 {
    public static void main(String[] args) throws IOException {
        Map<CharMatrix, CharMatrix> map = readInput();
        CharMatrix charMatrix = new CharMatrix(List.of(".#.", "..#", "###"), '.');
        iterate(charMatrix, map, 5);
        iterate(charMatrix, map, 18);
    }

    private static void iterate(CharMatrix charMatrix, Map<CharMatrix, CharMatrix> map, int iterationCount) {
        for (int i = 0; i < iterationCount; i++) {
            charMatrix = evolve(charMatrix, map);
        }
        int count = 0;
        for (int y = 0; y < charMatrix.getHeight(); y++) {
            for (int x = 0; x < charMatrix.getWidth(); x++) {
                if (charMatrix.get(x, y) == '#') {
                    count++;
                }
            }
        }
        System.out.println(count);
    }

    private static Map<CharMatrix, CharMatrix> readInput() throws IOException {
        List<String> input = InputUtil.readAsLines("input21.txt");
        Map<CharMatrix, CharMatrix> map = new HashMap<>();
        for (String s : input) {
            String[] sp = s.split(" => ");
            CharMatrix key = new CharMatrix(sp[0].replace("/", "\n"), '.');
            CharMatrix value = new CharMatrix(sp[1].replace("/", "\n"), '.');
            map.put(key, value);
        }
        boolean change;
        do {
            change = false;
            for (var entry : new ArrayList<>(map.entrySet())) {
                CharMatrix key = entry.getKey();
                CharMatrix variant1 = key.mirrorHorizontal();
                CharMatrix variant2 = key.transpose().mirrorHorizontal();
                if (!map.containsKey(variant1)) {
                    map.put(variant1, entry.getValue());
                    change = true;
                }
                if (!map.containsKey(variant2)) {
                    map.put(variant2, entry.getValue());
                    change = true;
                }
            }
        } while (change);
        return map;
    }

    private static CharMatrix evolve(CharMatrix charMatrix, Map<CharMatrix, CharMatrix> map) {
        if ((charMatrix.getWidth() % 2) == 0) {
            CharMatrix result = new CharMatrix(charMatrix.getHeight() / 2 * 3, charMatrix.getWidth() / 2 * 3);
            for (int y = 0; y < charMatrix.getHeight() / 2; y++) {
                for (int x = 0; x < charMatrix.getWidth() / 2; x++) {
                    CharMatrix part = charMatrix.part(x * 2,  y * 2, 2, 2);
                    CharMatrix newPart = map.get(part);
                    result.insert(x * 3, y * 3, newPart);
                }
            }
            return result;
        } else {
            CharMatrix result = new CharMatrix(charMatrix.getHeight() / 3 * 4, charMatrix.getWidth() / 3 * 4);
            for (int y = 0; y < charMatrix.getHeight() / 3; y++) {
                for (int x = 0; x < charMatrix.getWidth() / 3; x++) {
                    CharMatrix part = charMatrix.part(x * 3,  y * 3, 3, 3);
                    CharMatrix newPart = map.get(part);
                    result.insert(x * 4, y * 4, newPart);
                }
            }
            return result;
        }
    }
}
