import lib.InputUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day18 {
    public static void main(String[] args) throws IOException {
        List<String> input = InputUtil.readAsLines("input18.txt");
        Computer computer = new Computer(input);
        computer.run();
    }

    static class Computer {
        final List<String> instructions;
        int pc = 0;
        Map<String, Long> registers = new HashMap<>();
        long frequency;

        public Computer(List<String> instructions) {
            this.instructions = instructions;
        }

        public void run() {
            while (0 <= pc && pc < instructions.size()) {
                String[] sp = instructions.get(pc).split(" ");
                if ("set".equals(sp[0])) {
                    registers.put(sp[1], getValue(sp[2]));
                } else if ("add".equals(sp[0])) {
                    registers.put(sp[1], getValue(sp[1]) + getValue(sp[2]));
                } else if ("mul".equals(sp[0])) {
                    registers.put(sp[1], getValue(sp[1]) * getValue(sp[2]));
                } else if ("mod".equals(sp[0])) {
                    registers.put(sp[1], Math.floorMod(getValue(sp[1]), getValue(sp[2])));
                } else if ("rcv".equals(sp[0])) {
                    if (getValue(sp[1]) != 0) {
                        System.out.println(frequency);
                        return;
                    }
                } else if ("snd".equals(sp[0])) {
                    frequency = getValue(sp[1]);
                } else if ("jgz".equals(sp[0])) {
                    if (getValue(sp[1]) > 0) {
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
