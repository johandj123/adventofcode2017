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
        // TODO Too slow
        Computer computer = new Computer(instructions);
        computer.registers.put("a", 1L);
        computer.run();
        System.out.println(computer.getValue("h"));
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
