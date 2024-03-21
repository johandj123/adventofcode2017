import lib.InputUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day4 {
    public static void main(String[] args) throws IOException {
        List<String> input = InputUtil.readAsLines("input4.txt");
        System.out.println(input.stream().filter(Day4::validFirst).count());
        System.out.println(input.stream().filter(Day4::validSecond).count());
    }

    private static boolean validFirst(String s) {
        String[] sp = s.split(" ");
        Set<String> seen = new HashSet<>();
        for (String ss : sp) {
            if (!seen.add(ss)) {
                return false;
            }
        }
        return true;
    }

    private static boolean validSecond(String s) {
        String[] sp = s.split(" ");
        Set<String> seen = new HashSet<>();
        for (String ss : sp) {
            if (!seen.add(sortLetters(ss))) {
                return false;
            }
        }
        return true;
    }

    private static String sortLetters(String s) {
        return s.codePoints().sorted().collect(                                // Collect the results of processing each code point.
                StringBuilder::new,                  // Supplier<R> supplier
                StringBuilder::appendCodePoint,      // ObjIntConsumer<R> accumulator
                StringBuilder::append                // BiConsumer<R,​R> combiner
        ).toString();
    }
}
