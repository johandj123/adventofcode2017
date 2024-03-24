import lib.InputUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23 {
    public static void main(String[] args) throws IOException {
        List<String> instructions = InputUtil.readAsLines("input23.txt");
        first(instructions);
        second(instructions);
    }

    private static void first(List<String> instructions) {
        Computer computer = new Computer(instructions);
        computer.run();
        System.out.println(computer.mulCount);
    }

    private static void second(List<String> instructions) {
        // Executing the actual program is too slow
//        Computer computer = new Computer(instructions);
//        computer.registers.put("a", 1L);
//        computer.run();
//        System.out.println(computer.getValue("h"));
        System.out.println(program(1));
    }

    private static int program(int a)
    {
        // Code below is the puzzle program translated to Java, with some optimizations
        int b = 0;
        int c = 0;
        int d = 0;
        int e = 0;
        int f = 0;
        int g = 0;
        int h = 0;

        b = 81;
        c = b;
        if (a != 0) {
            b *= 100;
            b += 100000;
            c = b;
            c += 17000;
        }
        while (true) {
            f = isPrime(b) ? 1 : 0;
            // Code below is a very ineffecient way to determine is a number is prime
//            f = 1;
//            d = 2;
//            do {
//                e = 2;
//                do {
//                    if (d * e == b) {
//                        f = 0;
//                    }
//                    e++;
//                } while (e != b);
//                d++;
//            } while (d != b);
            if (f == 0) {
                h++;
            }
            if (b == c) {
                return h;
            }
            b += 17;
        }
    }

    private static boolean isPrime(int number) {
        for (int i = 2; i < number / 2; i++) {
            if ((number % i) == 0) {
                return false;
            }
        }
        return true;
    }

    static class Computer {
        final List<String> instructions;
        int pc = 0;
        Map<String, Long> registers = new HashMap<>();
        int mulCount = 0;

        public Computer(List<String> instructions) {
            this.instructions = instructions;
        }

        public void run() {
            while (0 <= pc && pc < instructions.size()) {
                String[] sp = instructions.get(pc).split(" ");
                if ("set".equals(sp[0])) {
                    registers.put(sp[1], getValue(sp[2]));
                } else if ("sub".equals(sp[0])) {
                    registers.put(sp[1], getValue(sp[1]) - getValue(sp[2]));
                } else if ("mul".equals(sp[0])) {
                    registers.put(sp[1], getValue(sp[1]) * getValue(sp[2]));
                    mulCount++;
                } else if ("jnz".equals(sp[0])) {
                    if (getValue(sp[1]) != 0) {
                        pc += (int) getValue(sp[2]);
                        continue;
                    }
                } else {
                    throw new UnsupportedOperationException("Unknown instruction " + sp[0]);
                }
                pc++;
            }
        }

        public long getValue(String s) {
            try {
                return Long.parseLong(s);
            } catch (NumberFormatException e) {
                return registers.getOrDefault(s, 0L);
            }
        }
    }
}
