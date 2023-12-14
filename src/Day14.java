import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day14 {
    public static void part1() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day14/input.txt"));
        char[][] grid = new char[input.size()][input.getFirst().length()];
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                grid[i][j] = line.charAt(j);
            }
        }

        tiltUp(grid);

        int result = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] == 'O') {
                    result += grid.length - i;
                }
            }
        }

        System.out.println(STR."Result for part 1: \{result}");
    }
    public static void part2() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day14/input.txt"));
        char[][] grid = new char[input.size()][input.getFirst().length()];
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                grid[i][j] = line.charAt(j);
            }
        }

        HashMap<Integer,Integer> seen = new HashMap<>();

        int cycles = 0;
        while (cycles <= 1_000_000_000) {
            cycle(grid);
            cycles++;

            int hash = Arrays.deepHashCode(grid);
            if (seen.containsKey(hash) && cycles < 100_000) {
                int cycle_len = cycles - seen.get(hash);
                int remaining = (1_000_000_000 - cycles) % cycle_len;
                cycles = 1_000_000_000 - remaining + 1;
            }
            seen.put(hash, cycles);
        }

        int result = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] == 'O') {
                    result += grid.length - i;
                }
            }
        }

        System.out.println(STR."Result for part 2: \{result}");

    }

    private static void cycle(char[][] grid) {
        tiltUp(grid);
        tiltLeft(grid);
        tiltDown(grid);
        tiltRight(grid);
    }

    private static void tiltUp(char[][] grid) {
        int[] minRow = new int[grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] == '#') {
                    minRow[j] = i+1;
                }
                if (grid[i][j] == 'O') {
                    int newRow = minRow[j];
                    grid[i][j] = '.';
                    grid[newRow][j] = 'O';
                    minRow[j] = newRow+1;
                }
            }
        }
    }

    private static void tiltLeft(char[][] grid) {
        int[] minCol = new int[grid.length];
        for (int j = 0; j < grid.length; j++) {
            for (int i = 0; i < grid.length; i++) {
                if (grid[i][j] == '#') {
                    minCol[i] = j+1;
                }
                if (grid[i][j] == 'O') {
                    int newCol = minCol[i];
                    grid[i][j] = '.';
                    //System.out.println(STR."Moving rock from (\{i},\{j}) to (\{i},\{newCol})");
                    grid[i][newCol] = 'O';
                    minCol[i] = newCol+1;
                }
            }
        }
    }

    private static void tiltDown(char[][] grid) {
        int[] maxRow = new int[grid[0].length];
        Arrays.fill(maxRow, grid[0].length-1);
        for (int i = grid.length-1; i >= 0; i--) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] == '#') {
                    maxRow[j] = i-1;
                }
                if (grid[i][j] == 'O') {
                    int newRow = maxRow[j];
                    grid[i][j] = '.';
                    //System.out.println(STR."Moving rock from (\{i},\{j}) to (\{newRow},\{j})");
                    grid[newRow][j] = 'O';
                    maxRow[j] = newRow-1;
                }
            }
        }
    }

    private static void tiltRight(char[][] grid) {
        int[] maxCol = new int[grid.length];
        Arrays.fill(maxCol, grid.length-1);
        for (int j = grid[0].length-1; j >= 0; j--) {
            for (int i = 0; i < grid.length; i++) {
                if (grid[i][j] == '#') {
                    maxCol[i] = j-1;
                }
                if (grid[i][j] == 'O') {
                    int newCol = maxCol[i];
                    grid[i][j] = '.';
                    grid[i][newCol] = 'O';
                    maxCol[i] = newCol-1;
                }
            }
        }
    }
}
