import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class Day3 {
    public static void part1() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day3/input.txt"));
        HashMap<Point, Character> grid = new HashMap<>();

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Point pt = new Point(i, j);
                grid.put(pt, c);
            }
        }

        StringBuilder numberBuffer = new StringBuilder();
        boolean nearSpecial = false;
        int result = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(0).length(); j++) {
                Point pt = new Point(i, j);
                Point[] adjacentPoints = {new Point(i + 1, j),        // Right
                        new Point(i - 1, j),        // Left
                        new Point(i - 1, j + 1), // Top left
                        new Point(i + 1, j + 1), // Top right
                        new Point(i, j + 1),        // Top
                        new Point(i - 1, j - 1), // Bottom left
                        new Point(i + 1, j - 1), // Bottom right
                        new Point(i, j - 1), // Bottom
                };


                char value = grid.getOrDefault(pt, '.');
                if (Character.isDigit(value)) {
                    numberBuffer.append(value);
                    for (Point p : adjacentPoints) {
                        char adjacentValue = grid.getOrDefault(p, '.');

                        if (!Character.isDigit(adjacentValue) && adjacentValue != '.') {
                            nearSpecial = true;
                        }
                    }
                } else if (!Character.isDigit(value) && !numberBuffer.isEmpty()) {
                    int num = Integer.parseInt(numberBuffer.toString());

                    if (nearSpecial) {
                        result += num;
                    }
                    numberBuffer = new StringBuilder();
                    nearSpecial = false;
                }


            }
        }

        System.out.println(STR. "Result for part 1: \{ result }" );

    }

    public static void part2() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day3/input.txt"));
        HashMap<Point, Character> grid = new HashMap<>();

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Point pt = new Point(i, j);
                grid.put(pt, c);
            }
        }

        StringBuilder numberBuffer = new StringBuilder();

        HashMap<Point, List<Integer>> specialCount = new HashMap<>();
        HashSet<Point> nearbyPoints = new HashSet<>();

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(0).length(); j++) {
                Point pt = new Point(i, j);
                Point[] adjacentPoints = {new Point(i + 1, j),        // Right
                        new Point(i - 1, j),        // Left
                        new Point(i - 1, j + 1), // Top left
                        new Point(i + 1, j + 1), // Top right
                        new Point(i, j + 1),        // Top
                        new Point(i - 1, j - 1), // Bottom left
                        new Point(i + 1, j - 1), // Bottom right
                        new Point(i, j - 1),        // Bottom
                };


                char value = grid.getOrDefault(pt, '.');
                if (Character.isDigit(value)) {
                    numberBuffer.append(value);
                    Arrays.stream(adjacentPoints)
                            .filter(p -> grid.getOrDefault(p, '.') == '*')
                            .forEach(nearbyPoints::add);
                }


                if (!Character.isDigit(value) && !numberBuffer.isEmpty()) {
                    int num = Integer.parseInt(numberBuffer.toString());

                    nearbyPoints.forEach(p -> specialCount.computeIfAbsent(p, _ -> new ArrayList<>()).add(num));

                    numberBuffer = new StringBuilder();
                    nearbyPoints.clear();
                }
            }
        }

        int result = specialCount.values().stream().filter(count -> count.size() == 2).mapToInt(count -> count.get(0) * count.get(1)).sum();

        System.out.println(STR. "Result for part 2: \{ result }" );
    }
}
