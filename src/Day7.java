import lib.Graph;
import lib.InputUtil;

import java.io.IOException;
import java.util.*;

public class Day7 {
    public static void main(String[] args) throws IOException {
        List<String> input = InputUtil.readAsLines("input7.txt");
        Map<String, Node> nodes = new HashMap<>();
        for (String s : input) {
            String[] sp = s.split("[ ()]+");
            Node node = new Node(sp[0], Integer.parseInt(sp[1]));
            nodes.put(node.name, node);
        }
        Graph<Node> graph = new Graph<>();
        for (String s : input) {
            Node from = nodes.get(s.split("[ ()]+")[0]);
            int index = s.indexOf("->");
            if (index >= 0) {
                String t = s.substring(index + 3);
                String[] sp = t.split(", ");
                for (String ss : sp) {
                    Node to = nodes.get(ss);
                    graph.addLink(from, to);
                }
            }
        }
        Set<Node> roots = graph.getRootNodes();
        System.out.println(roots.iterator().next().name);
    }

    static class Node {
        final String name;
        final int weight;

        public Node(String name, int weight) {
            this.name = name;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return weight == node.weight && Objects.equals(name, node.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, weight);
        }
    }
}
