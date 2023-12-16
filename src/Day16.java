import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day16 {
    static char[][] grid;

    public static void part1() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("Inputs/Day16/input.txt"));
        grid = new char[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                grid[i][j] = line.charAt(j);
            }
        }

        int result = exploreGrid(0, 0, Direction.Right);
        System.out.println(STR."Result for part 1: \{result}");
    }

    public static void part2() {
        int maxResult = 0;
        for (int i = 0; i < grid.length; i++) {
            maxResult = Math.max(maxResult, exploreGrid(0, i, Direction.Right));
            maxResult = Math.max(maxResult, exploreGrid(grid[0].length - 1, i, Direction.Left));
        }
        for (int i = 0; i < grid[0].length; i++) {
            maxResult = Math.max(maxResult, exploreGrid(i, 0, Direction.Down));
            maxResult = Math.max(maxResult, exploreGrid(i, grid.length - 1, Direction.Up));
        }
        System.out.println(STR."Result for part 2: \{maxResult}");
    }

    private static int exploreGrid(int startX, int startY, Direction startDir) {
        Point origin = new Point(startX, startY, startDir);
        HashSet<Integer> visited = new HashSet<>();
        Queue<Point> points = new LinkedList<>();
        points.add(origin);

        char[][] viz = new char[grid.length][grid[0].length];
        Arrays.stream(viz).forEach(r -> Arrays.fill(r, '.'));

        while (!points.isEmpty()) {
            Point point = points.poll();

            int x = point.x;
            int y = point.y;

            char v = grid[y][x];
            viz[y][x] = '#';
            if (visited.contains(point.hashCode())) {
                continue;
            } else {
                visited.add(point.hashCode());
            }

            boolean split = false;
            switch (v) {
                case '.' -> {
                }
                case '-' -> {
                    if (point.dir == Direction.Up || point.dir == Direction.Down) {
                        points.add(new Point(x, y, Direction.Left));
                        points.add(new Point(x, y, Direction.Right));
                        split = true;
                    }
                }
                case '|' -> {
                    if (point.dir == Direction.Left || point.dir == Direction.Right) {
                        points.add(new Point(x, y, Direction.Up));
                        points.add(new Point(x, y, Direction.Down));
                        split = true;
                    }
                }
                case '/' -> {
                    switch (point.dir) {
                        case Up -> point.dir = Direction.Right;
                        case Down -> point.dir = Direction.Left;
                        case Left -> point.dir = Direction.Down;
                        case Right -> point.dir = Direction.Up;
                    }

                }
                case '\\' -> {
                    switch (point.dir) {
                        case Up -> point.dir = Direction.Left;
                        case Down -> point.dir = Direction.Right;
                        case Left -> point.dir = Direction.Up;
                        case Right -> point.dir = Direction.Down;
                    }
                }
            }
            point.update();
            if (!split && point.x >= 0 && point.x < grid.length && point.y >= 0 && point.y < grid[0].length) {
                points.add(point);
            }
        }

        int result = 0;
        for (char[] row : viz) {
            for (char v : row) {
                if (v == '#') result++;
            }
        }
        return result;
    }

    enum Direction {
        Up(0),
        Down(1),
        Left(2),
        Right(3);
        final int val;

        Direction(int i) {
            this.val = i;
        }
    }

    static class Point {
        int x;
        int y;
        Direction dir;

        Point(int x, int y, Direction dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }

        void update() {
            switch (dir) {
                case Up -> this.y--;
                case Down -> this.y++;
                case Left -> this.x--;
                case Right -> this.x++;
            }
        }

        @Override
        public String toString() {
            return STR."(\{x},\{y}): \{dir}";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Point other = (Point) obj;
            return x == other.x && y == other.y && dir == other.dir;
        }


        @Override
        public int hashCode() {
            return y + grid.length * x + grid.length * grid[0].length * dir.val;
        }
    }
}
