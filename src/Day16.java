import lib.InputUtil;
import lib.StepUtil;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Day16 {

    public static final String START_STRING = "abcdefghijklmnop";

    public static void main(String[] args) throws IOException {
        String[] sp = InputUtil.readAsString("input16.txt").split(",");
        first(sp);
        second(sp);
    }

    private static void first(String[] sp) {
        String result = dance(START_STRING, sp);
        System.out.println(result);
    }

    private static void second(String[] sp) {
        String result = StepUtil.performStepsWithCycleDetection(START_STRING, 1000000000, string -> dance(string, sp));
        System.out.println(result);
    }

    private static String dance(String string, String[] sp) {
        List<Character> list = new ArrayList<>();
        for (char c : string.toCharArray()) {
            list.add(c);
        }
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
        String result = sb.toString();
        return result;
    }

    private static void swap(List<Character> list, Integer indexA, Integer indexB) {
        char h = list.get(indexA);
        list.set(indexA, list.get(indexB));
        list.set(indexB, h);
    }
}
