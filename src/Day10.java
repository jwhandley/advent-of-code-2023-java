import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.*;

public class Day10 {
    static char[][] grid;
    static int gridSize;
    static List<Point> visited;
    static Point startLocation;

    public static void part1() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("Inputs/Day10/input.txt"));
        gridSize = lines.size();
        grid = new char[gridSize][gridSize];
        startLocation = findStartAndPopulateGrid(lines);
        visited = new ArrayList<>();
        bfs(startLocation, visited);
        System.out.println(STR."Result for part 1: \{visited.size() / 2}");
    }

    private static Point findStartAndPopulateGrid(List<String> lines) throws Error {
        Point p = null;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                grid[j][i] = line.charAt(j);
                if (line.charAt(j) == 'S') p = new Point(j, i);
            }
        }
        return p;
    }

    public static void part2() {
        int A = 0;
        for (int i = 0; i < visited.size(); i++) {
            Point p1 = visited.get(i);
            Point p2 = visited.get((i+1) % visited.size());
            A += (p1.y + p2.y) * (p2.x - p1.x) / 2;
        }

        int inside = (A - visited.size()/2);
        System.out.println(STR."Result for part 2: \{inside}");
    }

    private static void bfs(Point origin, List<Point> visited) {
        var goesDown = Set.of('7','F','|','S');
        var goesUp = Set.of('J','L','|','S');
        var goesLeft = Set.of('7','J','-','S');
        var goesRight = Set.of('L','F','-','S');


        Stack<Point> stack = new Stack<>();
        stack.push(origin);


        while (!stack.isEmpty()) {
            Point p = stack.pop();

            if (!visited.contains(p)) {
                if (p.y < 0 || p.y > gridSize || p.x < 0 || p.x > gridSize) continue;
                visited.add(p);
                char charAtPos = grid[p.x][p.y];

                if (charAtPos == '.') {
                    visited.remove(p);
                }

                // Process directions
                if (goesDown.contains(charAtPos)) {
                    stack.add(new Point(p.x, p.y + 1)); // South
                }
                if (goesUp.contains(charAtPos)) {
                    stack.add(new Point(p.x, p.y - 1)); // North
                }
                if (goesLeft.contains(charAtPos)) {
                    stack.add(new Point(p.x - 1, p.y)); // West
                }
                if (goesRight.contains(charAtPos)) {
                    stack.add(new Point(p.x + 1, p.y)); // East
                }
            }
        }
    }

    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Point point = (Point) obj;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return y + x * gridSize; // Cheaper hashing since we know the unique points
        }
    }

}
