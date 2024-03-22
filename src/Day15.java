import lib.InputUtil;

import java.io.IOException;
import java.util.List;

public class Day15 {
    public static void main(String[] args) throws IOException {
        List<Integer> input = InputUtil.extractPositiveIntegers(InputUtil.readAsString("input15.txt"));
        first(input);
        second(input);
    }

    private static void first(List<Integer> input) {
        Generator a = new Generator(input.get(0), 16807);
        Generator b = new Generator(input.get(1), 48271);
        int count = 0;
        for (int i = 0; i < 40000000; i++) {
            a.generate();
            b.generate();
            if (a.matches(b)) {
                count++;
            }
        }
        System.out.println(count);
    }

    private static void second(List<Integer> input) {
        Generator a = new Generator(input.get(0), 16807);
        Generator b = new Generator(input.get(1), 48271);
        int count = 0;
        for (int i = 0; i < 5000000; i++) {
            while ((a.generate() % 4) != 0);
            while ((b.generate() % 8) != 0);
            if (a.matches(b)) {
                count++;
            }
        }
        System.out.println(count);
    }

    static class Generator {
        long value;
        long multiplier;

        public Generator(long startValue, long multiplier) {
            this.value = startValue;
            this.multiplier = multiplier;
        }

        public long generate() {
            value = (value * multiplier) % 2147483647;
            return value;
        }

        public boolean matches(Generator o) {
            return (value & 0xffff) == (o.value & 0xffff);
        }
    }
}
