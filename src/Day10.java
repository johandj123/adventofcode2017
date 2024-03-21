import lib.InputUtil;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day10 {
    public static void main(String[] args) throws IOException {
        List<Integer> input = InputUtil.extractPositiveIntegers(InputUtil.readAsString("input10.txt"));
        Deque<Integer> deque = IntStream.range(0, 256).boxed().collect(Collectors.toCollection(ArrayDeque::new));
        int skip = 0;
        int forward = 0;
        for (int length : input) {
            List<Integer> take = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                take.add(deque.removeFirst());
            }
            Collections.reverse(take);
            for (int i : take) {
                deque.addLast(i);
                forward++;
            }
            for (int i = 0; i < skip; i++) {
                deque.addLast(deque.removeFirst());
                forward++;
            }
            skip++;
        }
        forward = forward % deque.size();
        for (int i = 0; i < forward; i++) {
            deque.addFirst(deque.removeLast());
        }
        System.out.println(deque.removeFirst() * deque.removeFirst());
    }
}
