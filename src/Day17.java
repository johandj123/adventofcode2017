import lib.InputUtil;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Day17 {
    public static void main(String[] args) throws IOException {
        int input = Integer.parseInt(InputUtil.readAsString("input17.txt"));
        first(input);
        second(input);
    }

    private static void first(int input) {
        Deque<Integer> deque = new ArrayDeque<>(List.of(0));
        for (int i = 1; i <= 2017; i++) {
            int roll = input % deque.size();
            for (int j = 0; j < roll; j++) {
                deque.addLast(deque.removeFirst());
            }
            deque.addLast(i);
        }
        System.out.println(deque.peekFirst());
    }

    private static void second(int input) {
        Deque<Integer> deque = new ArrayDeque<>(List.of(0));
        for (int i = 1; i <= 50000000; i++) {
            int roll = input % deque.size();
            for (int j = 0; j < roll; j++) {
                deque.addLast(deque.removeFirst());
            }
            deque.addLast(i);
            if ((i % 1000000) == 0) {
                System.out.println("..." + i);
            }
        }
        while (deque.peekLast() != 0) {
            deque.addLast(deque.removeFirst());
        }
        System.out.println(deque.peekFirst());
    }
}
