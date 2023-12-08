import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day8 {
    public static void part1() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day8/input.txt"));
        String directions = input.getFirst();
        int directionSize = directions.length();
        Graph graph = new Graph();
        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i);
            String node = line.split(" = ")[0];

            graph.addNode(node);
            String[] edge = line.split(" = ")[1].replaceAll("[()]", "").split(", ");

            graph.addEdge(node, edge[0], edge[1]);


        }

        String currentNode = "AAA";
        int i = 0;
        while (!currentNode.equals("ZZZ")) {
            int directionIndex = i % directionSize;
            char direction = directions.charAt(directionIndex);

            switch (direction) {
                case 'L' -> currentNode = graph.nodes.get(currentNode).left;
                case 'R' -> currentNode = graph.nodes.get(currentNode).right;
                default -> throw new IllegalStateException(STR."Unexpected value: \{direction}");
            }

            i++;
        }

        System.out.println(STR."Result for part 1: \{i}");
    }

    public static void part2() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day8/input.txt"));
        String directions = input.getFirst();
        int directionSize = directions.length();
        Graph graph = new Graph();
        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i);
            String node = line.split(" = ")[0];

            graph.addNode(node);
            String[] edge = line.split(" = ")[1].replaceAll("[()]", "").split(", ");

            // Left edge
            graph.addEdge(node, edge[0], edge[1]);
        }

        Set<String> startNodes = graph.nodes.keySet().stream().filter(k -> k.endsWith("A")).collect(Collectors.toSet());

        List<Long> times = new ArrayList<>();

        for (String startNode : startNodes) {
            String currentNode = startNode;
            long i = 0;
            while (!currentNode.endsWith("Z")) {
                int directionIndex = (int) i % directionSize;
                char direction = directions.charAt(directionIndex);

                switch (direction) {
                    case 'L' -> currentNode = graph.nodes.get(currentNode).left;
                    case 'R' -> currentNode = graph.nodes.get(currentNode).right;
                    default -> throw new IllegalStateException(STR."Unexpected value: \{direction}");
                }

                i++;
            }
            // System.out.println(STR."Iterations for node \{ startNode }: \{ i }");
            times.add(i);
        }
        long result = lcm(times);
        System.out.println(STR."Result for part 2: \{result}");

    }

    private static long gcd(long x, long y) {
        //noinspection SuspiciousNameCombination
        return (y == 0) ? x : gcd(y, x % y);
    }

    public static long lcm(List<Long> numbers) {
        return numbers.stream().reduce(1L, (x, y) -> x * (y / gcd(x, y)));
    }

    static class Node {
        String left;
        String right;

        Node() {
            this.left = null;
            this.right = null;
        }
    }

    static class Graph {
        HashMap<String, Node> nodes;

        public Graph() {
            this.nodes = new HashMap<>();
        }

        public void addNode(String id) {
            Node newNode = new Node();
            this.nodes.put(id, newNode);
        }

        public void addEdge(String from, String left, String right) {
            var node = this.nodes.get(from);
            if (node == null) {
                this.addNode(from);
                node = nodes.get(from);
            }
            node.left = left;
            node.right = right;
        }
    }
}
