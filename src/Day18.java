import lib.InputUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Day18 {
    public static void main(String[] args) throws IOException {
        List<String> input = InputUtil.readAsLines("input18.txt");
        first(input);
        second(input);
    }

    private static void first(List<String> input) {
        Computer computer = new Computer(input);
        computer.run();
    }

    private static void second(List<String> input) {
        BlockingQueue<Long> queue1 = new LinkedBlockingQueue<>();
        BlockingQueue<Long> queue2 = new LinkedBlockingQueue<>();
        MultiComputer multiComputer0 = new MultiComputer(input, 0, queue1, queue2);
        MultiComputer multiComputer1 = new MultiComputer(input, 1, queue2, queue1);
        multiComputer0.start();
        multiComputer1.start();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(multiComputer1.sendCounter);
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

    static class MultiComputer extends Thread {
        final List<String> instructions;
        final BlockingQueue<Long> incomingQueue;
        final BlockingQueue<Long> outgoingQueue;
        int pc = 0;
        Map<String, Long> registers = new HashMap<>();
        long sendCounter = 0;

        public MultiComputer(List<String> instructions, int processID, BlockingQueue<Long> incomingQueue, BlockingQueue<Long> outgoingQueue) {
            this.instructions = instructions;
            registers.put("p", (long) processID);
            this.incomingQueue = incomingQueue;
            this.outgoingQueue = outgoingQueue;
        }

        @Override
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
                    try {
                        registers.put(sp[1], incomingQueue.take());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else if ("snd".equals(sp[0])) {
                    try {
                        outgoingQueue.put(getValue(sp[1]));
                        sendCounter++;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
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
