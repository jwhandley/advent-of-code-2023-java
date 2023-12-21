import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day21 {
    static char[][] grid;
    static Set<Point> queue;

    public static void part1() throws IOException {
        parseInput(Path.of("Inputs/Day21/input.txt"));
        int targetSteps = 64;
        for (int i = 0; i < targetSteps; i++) {
            Set<Point> nextQueue = new HashSet<>();
            for (Point p : queue) {
                int r = p.r;
                int c = p.c;

                if (r - 1 >= 0 && r - 1 < grid.length && grid[r-1][c] != '#') {
                    nextQueue.add(new Point(r - 1, c));
                }

                if (r + 1 >= 0 && r + 1 < grid.length && grid[r+1][c] != '#') {
                    nextQueue.add(new Point(r + 1, c));
                }

                if (c - 1 >= 0 && c - 1 < grid[0].length && grid[r][c-1] != '#') {
                    nextQueue.add(new Point(r, c - 1));
                }

                if (c + 1 >= 0 && c + 1 < grid[0].length && grid[r][c+1] != '#') {
                    nextQueue.add(new Point(r, c + 1));
                }
            }

            queue = nextQueue;
        }

        System.out.println(STR."Result for part 1: \{queue.size()}");
    }

    public static void part2() throws IOException {
        parseInput(Path.of("Inputs/Day21/input.txt"));
        int targetSteps = 26501365;
        for (int i = 0; i < targetSteps; i++) {
            Set<Point> nextQueue = new HashSet<>();
            for (Point p : queue) {
                int r = p.r;
                int c = p.c;

                int up = ((r - 1) % grid.length + grid.length) % grid.length;
                int down = ((r + 1) % grid.length + grid.length) % grid.length;
                int left = ((c - 1) % grid[0].length + grid[0].length) % grid[0].length;
                int right = ((c + 1) % grid[0].length + grid[0].length) % grid[0].length;
                int row = (r % grid.length + grid.length) % grid.length;;
                int col = (c % grid[0].length + grid[0].length) % grid[0].length;;

                //System.out.println(STR."Up: \{up}, Down: \{down}, Left: \{left}, Right: \{right}, Row: \{row}, Col: \{col}, Abs row: \{r}, Abs col: \{c}");

                if (grid[up][col] != '#') {
                    nextQueue.add(new Point(r - 1, c));
                }

                if (grid[down][col] != '#') {
                    nextQueue.add(new Point(r + 1, c));
                }

                if (grid[row][left] != '#') {
                    nextQueue.add(new Point(r, c - 1));
                }

                if (grid[row][right] != '#') {
                    nextQueue.add(new Point(r, c + 1));
                }
            }

            if (i % 131 == 65) {
                System.out.println(STR."Iteration \{i}, \{queue.size()}");
            }

            queue = nextQueue;
        }

        System.out.println(STR."Result for part 2: \{queue.size()}");

    }

    private static void parseInput(Path filepath) throws IOException {
        List<String> lines = Files.readAllLines(filepath);
        grid = new char[lines.size()][lines.getFirst().length()];
        queue = new HashSet<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                grid[i][j] = line.charAt(j);

                if (grid[i][j] == 'S') {
                    queue.add(new Point(i, j));
                }
            }
        }
    }

    static class Point {
        int r;
        int c;

        Point(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }

        @Override
        public String toString() {
            return STR."(\{r}, \{c})";
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof Point other)) return false;
            return (r == other.r) && (c == other.c);
        }
    }
}
