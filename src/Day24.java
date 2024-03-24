import lib.InputUtil;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day24 {
    public static void main(String[] args) throws IOException {
        List<Component> components = InputUtil.readAsLines("input24.txt")
                .stream()
                .map(Component::new)
                .collect(Collectors.toList());
        System.out.println(strongestBridge(new HashSet<>(components), new ArrayList<>(), 0));
        System.out.println(longestBridge(new HashSet<>(components), new ArrayList<>(), 0).strength);
    }

    private static int strongestBridge(Set<Component> open, List<Component> current, int lastPort) {
        int maxStrength = current.stream().mapToInt(c -> c.a + c.b).sum();
        for (Component component : new ArrayList<>(open)) {
            if (component.a == lastPort) {
                open.remove(component);
                current.add(component);
                maxStrength = Math.max(maxStrength, strongestBridge(open, current, component.b));
                current.remove(component);
                open.add(component);
            }
            if (component.b == lastPort) {
                open.remove(component);
                current.add(component);
                maxStrength = Math.max(maxStrength, strongestBridge(open, current, component.a));
                current.remove(component);
                open.add(component);
            }
        }
        return maxStrength;
    }

    private static Score longestBridge(Set<Component> open, List<Component> current, int lastPort) {
        int maxStrength = current.stream().mapToInt(c -> c.a + c.b).sum();
        Score maxScore = new Score(current.size(), maxStrength);
        for (Component component : new ArrayList<>(open)) {
            if (component.a == lastPort) {
                open.remove(component);
                current.add(component);
                Score score = longestBridge(open, current, component.b);
                if (score.compareTo(maxScore) > 0) {
                    maxScore = score;
                }
                current.remove(component);
                open.add(component);
            }
            if (component.b == lastPort) {
                open.remove(component);
                current.add(component);
                Score score = longestBridge(open, current, component.a);
                if (score.compareTo(maxScore) > 0) {
                    maxScore = score;
                }
                current.remove(component);
                open.add(component);
            }
        }
        return maxScore;
    }

    static class Component {
        final int a;
        final int b;

        public Component(String s) {
            String[] sp = s.split("/");
            a = Integer.parseInt(sp[0]);
            b = Integer.parseInt(sp[1]);
        }

        @Override
        public String toString() {
            return String.format("%d/%d", a, b);
        }
    }

    static class Score implements Comparable<Score> {
        private static final Comparator<Score> COMPARATOR = Comparator.comparing((Score s) -> s.length).thenComparing(s -> s.strength);

        final int length;
        final int strength;

        public Score(int length, int strength) {
            this.length = length;
            this.strength = strength;
        }

        @Override
        public int compareTo(Score o) {
            return COMPARATOR.compare(this, o);
        }
    }
}
