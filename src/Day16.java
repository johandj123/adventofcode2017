import lib.InputUtil;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day16 {
    public static void main(String[] args) throws IOException {
        String[] sp = InputUtil.readAsString("input16.txt").split(",");
        List<Character> list = IntStream.rangeClosed('a', 'p').mapToObj(x -> (char) x).collect(Collectors.toList());
        for (String s : sp) {
            char c = s.charAt(0);
            if (c == 's') {
                Deque<Character> deque = new ArrayDeque<>(list);
                int count = Integer.parseInt(s.substring(1));
                for (int i = 0; i < count; i++) {
                    deque.addFirst(deque.removeLast());
                }
                list = new ArrayList<>(deque);
            } else if (c == 'x') {
                List<Integer> parameters = InputUtil.extractPositiveIntegers(s);
                Integer indexA = parameters.get(0);
                Integer indexB = parameters.get(1);
                swap(list, indexA, indexB);
            } else if (c == 'p') {
                int indexA = list.indexOf(s.charAt(1));
                int indexB = list.indexOf(s.charAt(3));
                swap(list, indexA, indexB);
            } else {
                throw new IllegalArgumentException("Unknown operation " + c);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (char c : list) {
            sb.append(c);
        }
        System.out.println(sb);
    }

    private static void swap(List<Character> list, Integer indexA, Integer indexB) {
        char h = list.get(indexA);
        list.set(indexA, list.get(indexB));
        list.set(indexB, h);
    }
}
