import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class Day3 {
    public static void part1() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day3/input.txt"));

        char[][] charGrid = new char[input.size()][input.get(0).length()];

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                charGrid[i][j] = c;
            }
        }

        StringBuilder numberBuffer = new StringBuilder();
        boolean nearSpecial = false;
        int result = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(0).length(); j++) {
                char value = charGrid[i][j];
                if (Character.isDigit(value)) {
                    numberBuffer.append(value);

                    int[] offset = new int[] {-1, 1};
                    for (int k : offset) {
                        for (int l : offset) {
                            if (i + k < 0 || i + k >= charGrid.length) {
                                continue;
                            }

                            if (j + l < 0 || j + l >= charGrid[0].length) {
                                continue;
                            }

                            char adjacentValue = charGrid[i+k][j+l];

                            if (!Character.isDigit(adjacentValue) && adjacentValue != '.') {
                                nearSpecial = true;
                            }
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

    static char[][] buildGrid(Path inputPath) throws IOException {
        List<String> input = Files.readAllLines(inputPath);
        char[][] grid = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                grid[i][j] = line.charAt(j);
            }
        }

        return grid;
    }

    public static void part2() throws IOException {
        var grid = buildGrid(Path.of("Inputs/Day3/input.txt"));


        StringBuilder numberBuffer = new StringBuilder();

        HashMap<Point, List<Integer>> specialCount = new HashMap<>();
        HashSet<Point> nearbyPoints = new HashSet<>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int[] offsets = new int[] {-1, 1};

                char value = grid[i][j];
                if (Character.isDigit(value)) {
                    numberBuffer.append(value);

                    for (int k : offsets) {
                        for (int l : offsets) {
                            if (i + k < 0 || i + k >= grid.length) {
                                continue;
                            }

                            if (j + l < 0 || j + l >= grid[0].length) {
                                continue;
                            }

                            if (grid[i+k][j+l] == '*') {
                                nearbyPoints.add(new Point(i+k, j+l));
                            }
                        }
                    }
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
