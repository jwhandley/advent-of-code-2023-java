import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day17 {
    static int rows;
    static int cols;
    static int[][] grid;

    public static void part1() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("Inputs/Day17/input.txt"));
        rows = lines.size();
        cols = lines.getFirst().length();
        Queue<Node> queue = new PriorityQueue<>();
        grid = new int[rows][cols];
        for (int r = 0; r < rows; r++) {
            String line = lines.get(r);
            for (int c = 0; c < cols; c++) {
                int v = line.charAt(c) - '0';
                grid[r][c] = v;
                var node = new Node(r, c, 0, 0, Integer.MAX_VALUE, 0);
                if (r == 0 && c == 0) {
                    node.dist = 0;
                }
                queue.add(node);
            }
        }

        Set<Node> seen = new HashSet<>();
        while (!queue.isEmpty()) {
            var node = queue.poll();
            //System.out.println(node);

            if (node.r == rows - 1 && node.c == cols - 1) {
                System.out.println(STR."Result for part 1: \{node.dist}");
                break;
            }

            if (seen.contains(node)) continue;
            seen.add(node);

            if (node.forwardCount < 3 && (node.dr != 0 || node.dc != 0)) {
                int nr = node.r + node.dr;
                int nc = node.c + node.dc;
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                    queue.add(new Node(nr, nc, node.dr, node.dc, node.dist + grid[nr][nc], node.forwardCount + 1));
                }
            }

            for (int ndr = -1; ndr <= 1; ndr++) {
                for (int ndc = -1; ndc <= 1; ndc++) {
                    if ((ndr != node.dr || ndc != node.dc) && (ndr != -node.dr || ndc != -node.dc) && (Math.abs(ndr) != Math.abs(ndc))) {
                        int nr = node.r + ndr;
                        int nc = node.c + ndc;
                        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                            queue.add(new Node(nr, nc, ndr, ndc, node.dist + grid[nr][nc], 1));
                        }
                    }
                }
            }
        }
    }

    public static void part2() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("Inputs/Day17/input.txt"));
        rows = lines.size();
        cols = lines.getFirst().length();
        Queue<Node> queue = new PriorityQueue<>();
        grid = new int[rows][cols];
        for (int r = 0; r < rows; r++) {
            String line = lines.get(r);
            for (int c = 0; c < cols; c++) {
                int v = line.charAt(c) - '0';
                grid[r][c] = v;
                var node = new Node(r, c, 0, 0, Integer.MAX_VALUE, 0);
                if (r == 0 && c == 0) {
                    node.dist = 0;
                }
                queue.add(node);
            }
        }

        Set<Node> seen = new HashSet<>();
        while (!queue.isEmpty()) {
            var node = queue.poll();
            //System.out.println(node);

            if (node.r == rows - 1 && node.c == cols - 1 && node.forwardCount >= 4) {
                System.out.println(STR."Result for part 2: \{node.dist}");
                break;
            }

            if (seen.contains(node)) continue;
            seen.add(node);

            if (node.forwardCount < 10 && (node.dr != 0 || node.dc != 0)) {
                int nr = node.r + node.dr;
                int nc = node.c + node.dc;
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                    queue.add(new Node(nr, nc, node.dr, node.dc, node.dist + grid[nr][nc], node.forwardCount + 1));
                }
            }

            if ((node.forwardCount >= 4) || (node.dr == 0 && node.dc == 0)) {
                for (int ndr = -1; ndr <= 1; ndr++) {
                    for (int ndc = -1; ndc <= 1; ndc++) {
                        if ((ndr != node.dr || ndc != node.dc) && (ndr != -node.dr || ndc != -node.dc) && (Math.abs(ndr) != Math.abs(ndc))) {
                            int nr = node.r + ndr;
                            int nc = node.c + ndc;
                            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                                queue.add(new Node(nr, nc, ndr, ndc, node.dist + grid[nr][nc], 1));
                            }
                        }
                    }
                }
            }
        }

    }

    static class Node implements Comparable<Node> {
        int dist;
        int r;
        int c;
        int dr;
        int dc;
        int forwardCount;

        Node(int r, int c, int dr, int dc, int d, int forwardCount) {
            this.r = r;
            this.c = c;
            this.dist = d;
            this.dr = dr;
            this.dc = dc;
            this.forwardCount = forwardCount;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Node other = (Node) obj;
            return r == other.r && c == other.c;
        }

        @Override
        public int compareTo(@NotNull Day17.Node o) {
            return Integer.compare(dist, o.dist);
        }

        @Override
        public String toString() {
            return STR."Position: (\{r},\{c}), Distance: \{dist}, Direction: (\{dr},\{dc}), Forward count: \{forwardCount}";
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c, dr, dc, forwardCount);
        }
    }
}
