import lib.CollectionUtil;
import lib.Graph;
import lib.InputUtil;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day7 {
    public static void main(String[] args) throws IOException {
        List<String> input = InputUtil.readAsLines("input7.txt");
        Graph<Node> graph = getNodeGraph(input);
        Node root = first(graph);
        second(graph, root);
    }

    private static Node first(Graph<Node> graph) {
        Set<Node> roots = graph.getRootNodes();
        Node root = roots.iterator().next();
        System.out.println(root.name);
        return root;
    }

    private static void second(Graph<Node> graph, Node root) {
        weights(graph, root);
    }

    private static int weights(Graph<Node> graph, Node node) {
        Map<Node, Integer> w = graph.getNeighbours(node).stream()
                .collect(Collectors.toMap(n -> n, n -> weights(graph, n)));
        Set<Integer> sw = new HashSet<>(w.values());
        if (sw.size() == 2) {
            int wrongWeight = CollectionUtil.leastCommonValue(w.values());
            int correctWeight = CollectionUtil.mostCommonValue(w.values());
            int adaptWeight = correctWeight - wrongWeight;
            int adaptedWeight = w.entrySet().stream()
                    .filter(entry -> entry.getValue() == wrongWeight)
                    .mapToInt(entry -> entry.getKey().weight + adaptWeight)
                    .findFirst()
                    .orElseThrow();
            System.out.println(adaptedWeight);
        }
        return node.weight + w.values().stream().mapToInt(x -> x).sum();
    }

    private static Graph<Node> getNodeGraph(List<String> input) {
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
        return graph;
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
