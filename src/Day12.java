import lib.Graph;
import lib.GraphUtil;
import lib.InputUtil;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Day12 {
    public static void main(String[] args) throws IOException {
        List<String> input = InputUtil.readAsLines("input12.txt");
        Graph<Integer> graph = new Graph<>();
        for (String s : input) {
            List<Integer> values = InputUtil.extractPositiveIntegers(s);
            int first = values.get(0);
            for (int second : values.subList(1, values.size())) {
                graph.addLinkBidirectional(first, second);
            }
        }
        Set<Integer> reachable = GraphUtil.reachable(0, graph::getNeighbours);
        System.out.println(reachable.size());
        List<Set<Integer>> components = graph.components();
        System.out.println(components.size());
    }
}
