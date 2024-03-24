import lib.InputUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day25 {
    public static void main(String[] args) throws IOException {
        List<String> input = InputUtil.readAsStringGroups("input25.txt");
        String first = input.get(0);
        char startState = first.charAt(15);
        int steps = InputUtil.extractPositiveIntegers(first).get(0);
        Map<Condition, Transition> map = new HashMap<>();
        for (String part : input.subList(1, input.size())) {
            String[] sp = part.split("\n");
            char state = sp[0].charAt(9);
            int i = 1;
            while (i < sp.length) {
                int value = InputUtil.extractPositiveIntegers(sp[i++]).get(0);
                int newValue = InputUtil.extractPositiveIntegers(sp[i++]).get(0);
                int move = sp[i++].contains("right") ? 1 : -1;
                char newState = sp[i++].charAt(26);
                map.put(new Condition(state, value), new Transition(newValue, move, newState));
            }
        }
        Map<Integer, Integer> tape = new HashMap<>();
        int position = 0;
        char state = startState;
        for (int i = 0; i < steps; i++) {
            int value = tape.getOrDefault(position, 0);
            Transition transition = map.get(new Condition(state, value));
            tape.put(position, transition.value);
            position += transition.move;
            state = transition.state;
        }
        System.out.println(tape.values().stream().mapToInt(x -> x).sum());
    }

    static class Condition {
        final char state;
        final int value;

        public Condition(char state, int value) {
            this.state = state;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Condition condition = (Condition) o;
            return state == condition.state && value == condition.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(state, value);
        }
    }

    static class Transition {
        final int value;
        final int move;
        final char state;

        public Transition(int value, int move, char state) {
            this.value = value;
            this.move = move;
            this.state = state;
        }
    }
}
