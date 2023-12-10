import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.*;

public class Day10 {
    static List<String> lines;

    public static void part1() throws IOException {
        lines = Files.readAllLines(Path.of("Inputs/Day10/input.txt"));
        Point startLocation = findStart();
        List<Point> visited = new ArrayList<>();
        bfs(startLocation, visited);
        System.out.println(STR."Result for part 1: \{visited.size() / 2}");
    }

    private static Point findStart() throws Error {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == 'S') return new Point(j, i);
            }
        }


        throw new Error("Failed to find start position");
    }

    public static void part2() throws IOException {
        lines = Files.readAllLines(Path.of("Inputs/Day10/input.txt"));
        Point startLocation = findStart();
        List<Point> visited = new ArrayList<>();
        bfs(startLocation, visited);

        int insidePoints = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            boolean within = false;
            Boolean up = null;
            for (int j = 0; j < line.length(); j++) {
                char c = getChar(i, j, line, visited);

                if (c == '|') {
                    within = !within;
                } else if ((c == 'L' || c == 'F')) {
                    up = (c == 'L');
                } else if (c == '7' || c == 'J') {
                    char compare = Boolean.TRUE.equals(up) ? 'J' : '7';
                    if (c != compare) within = !within;
                    up = null;
                }
                if (within && c == '.') insidePoints++;
            }
        }
        System.out.println(STR."Result for part 2: \{insidePoints}");
    }

    private static char getChar(int i, int j, String line, List<Point> visited) {
        if (!visited.contains(new Point(j, i))) return '.';
        if (line.charAt(j) == 'S') return convertS(i, j, line);
        return line.charAt(j);
    }

    private static char convertS(int i,int j, String line) {
        char below = lines.get(i+1).charAt(j);
        char above = lines.get(i-1).charAt(j);
        char left = line.charAt(j-1);
        char right = line.charAt(j+1);

        var takesBelow = Set.of('7','F','|');
        var takesAbove = Set.of('J','L','|');
        var takesLeft = Set.of('7','J','-');
        var takesRight = Set.of('L','F','-');

        if (takesBelow.contains(above) && takesAbove.contains(below)) return '|';
        if (takesLeft.contains(right) && takesRight.contains(left)) return '-';
        if (takesBelow.contains(above) && takesLeft.contains(right)) return  'L';
        if (takesBelow.contains(above) && takesRight.contains(left)) return 'J';
        if (takesAbove.contains(below) && takesLeft.contains(right)) return 'F';
        if (takesAbove.contains(below) && takesRight.contains(left)) return '7';

        throw new Error("Unable to identify correct value for S");
    }

    private static void bfs(Point origin, List<Point> visited) {
        Stack<Point> stack = new Stack<>();
        stack.push(origin);

        while (!stack.isEmpty()) {
            Point p = stack.pop();

            if (!visited.contains(p)) {
                if (p.y < 0 || p.y > lines.size() || p.x < 0 || p.x > lines.getFirst().length()) continue;
                visited.add(p);
                switch (lines.get(p.y).charAt(p.x)) {
                    case '|' -> {
                        stack.add(new Point(p.x, p.y + 1)); // South
                        stack.add(new Point(p.x, p.y - 1)); // North
                    }
                    case '-' -> {
                        stack.add(new Point(p.x + 1, p.y)); // East
                        stack.add(new Point(p.x - 1, p.y)); // West
                    }
                    case 'L' -> {
                        stack.add(new Point(p.x, p.y - 1)); // North
                        stack.add(new Point(p.x + 1, p.y)); // East
                    }
                    case 'J' -> {
                        stack.add(new Point(p.x, p.y - 1)); // North
                        stack.add(new Point(p.x - 1, p.y)); // West
                    }
                    case '7' -> {
                        stack.add(new Point(p.x, p.y + 1)); // South
                        stack.add(new Point(p.x - 1, p.y)); // West
                    }
                    case 'F' -> {
                        stack.add(new Point(p.x, p.y + 1)); // South
                        stack.add(new Point(p.x + 1, p.y)); // East
                    }
                    case '.' -> visited.remove(p);
                    case 'S' -> {
                        stack.add(new Point(p.x, p.y + 1)); // South
                        stack.add(new Point(p.x, p.y - 1)); // North
                        stack.add(new Point(p.x + 1, p.y)); // East
                        stack.add(new Point(p.x - 1, p.y)); // West
                    }
                    default -> throw new IllegalStateException(STR."Unexpected value: \{lines.get(p.y).charAt(p.x)}");
                }
            }
        }

    }
}
