import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class Day3 {
    public static void part1() throws IOException {
        char[][] charGrid = buildGrid(Path.of("Inputs/Day3/input.txt"));

        StringBuilder numberBuffer = new StringBuilder();
        boolean nearSpecial = false;
        int result = 0;
        for (int i = 0; i < charGrid.length; i++) {
            for (int j = 0; j < charGrid[0].length; j++) {
                char value = charGrid[i][j];
                if (Character.isDigit(value)) {
                    numberBuffer.append(value);

                    int[] offset = new int[] {-1, 0, 1};
                    for (int k : offset) {
                        for (int l : offset) {
                            if (k == 0 && l == 0) {
                                continue;
                            }

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
        char[][] grid = new char[input.size()][input.getFirst().length()];
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

        HashSet<Integer> nearbyIndices = new HashSet<>();
        HashMap<Integer, List<Integer>> indexCount = new HashMap<>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int[] offsets = new int[] {-1, 0, 1};

                char value = grid[i][j];
                if (Character.isDigit(value)) {
                    numberBuffer.append(value);

                    for (int k : offsets) {
                        for (int l : offsets) {
                            if (k == 0 && l == 0) {
                                continue;
                            }

                            if (i + k < 0 || i + k >= grid.length) {
                                continue;
                            }

                            if (j + l < 0 || j + l >= grid.length) {
                                continue;
                            }

                            if (grid[i+k][j+l] == '*') {
                                nearbyIndices.add(i+k + grid.length * (j + l));
                            }
                        }
                    }
                }


                if (!Character.isDigit(value) && !numberBuffer.isEmpty()) {
                    int num = Integer.parseInt(numberBuffer.toString());

                    nearbyIndices.forEach(p -> indexCount.computeIfAbsent(p, _ -> new ArrayList<>()).add(num));

                    numberBuffer = new StringBuilder();
                    nearbyIndices.clear();
                }
            }
        }


        int result = indexCount.values().stream().filter(count -> count.size() == 2).mapToInt(count -> count.getFirst() * count.getLast()).sum();

        System.out.println(STR. "Result for part 2: \{ result }" );
    }
}
