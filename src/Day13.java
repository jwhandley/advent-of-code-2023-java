import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day13 {
    static String input;
    static List<char[][]> grids;
    public static void part1() throws IOException {
        input = Files.readString(Path.of("Inputs/Day13/input.txt"));
        grids = Arrays.stream(input.split("\n\n")).map(Day13::buildGrid).toList();
        int result = 0;
        for (char[][] grid : grids) {
            int maxCol = 0;
            int maxRow = 0;
            int rowIndex = 0;
            int colIndex = 0;

            // Rows
            int rows = grid.length;
            for (int i = 1; i < rows; i++) {
                char[][] sliceLeft = sliceGrid(grid, i, rows, 0);
                char[][] sliceRight = sliceGrid(grid,0,rows-i,0);
                if (Arrays.deepEquals(sliceLeft, reflectGrid(sliceLeft,0))) {
                    if (rows-i > maxRow) {
                        maxRow = (rows-i)/2;
                        rowIndex = i + (rows-i)/2;
                    }
                }

                if (Arrays.deepEquals(sliceRight, reflectGrid(sliceRight,0))) {
                    if (rows - i > maxRow) {
                        maxRow = (rows - i) / 2;
                        rowIndex = (rows - i) / 2;
                    }
                }
            }

            // Cols
            int cols = grid[0].length;
            for (int i = 1; i < cols; i++) {
                char[][] sliceTop = sliceGrid(grid, i, cols, 1);
                char[][] sliceBottom = sliceGrid(grid, 0, cols-i, 1);
                if (Arrays.deepEquals(sliceTop, reflectGrid(sliceTop, 1))) {
                    if (cols-i > maxCol) {
                        maxCol = (cols-i)/2;
                        colIndex = i + (cols-i)/2;
                    }
                }

                if (Arrays.deepEquals(sliceBottom, reflectGrid(sliceBottom, 1))) {
                    if (cols-i > maxCol) {
                        maxCol = (cols-i)/2;
                        colIndex = (cols-i)/2;
                    }
                }
            }
            if (maxRow > maxCol) {
                result += 100*rowIndex;
            } else {
                result += colIndex;
            }
        }
        System.out.println(STR."Result for part 1: \{result}");
    }

    public static void part2() {
        int result = 0;
        for (char[][] grid : grids) {
            int maxCol = 0;
            int maxRow = 0;
            int rowIndex = 0;
            int colIndex = 0;

            // Rows
            int rows = grid.length;
            for (int i = 1; i < rows; i++) {
                char[][] sliceLeft = sliceGrid(grid, i, rows, 0);
                char[][] sliceRight = sliceGrid(grid,0,rows-i,0);
                if (countDiff(sliceLeft, reflectGrid(sliceLeft,0))==2) {
                    if (rows-i > maxRow) {
                        maxRow = (rows-i)/2;
                        rowIndex = i + (rows-i)/2;
                    }
                }

                if (countDiff(sliceRight, reflectGrid(sliceRight,0))==2) {
                    if (rows - i > maxRow) {
                        maxRow = (rows - i) / 2;
                        rowIndex = (rows - i) / 2;
                    }
                }
            }

            // Cols
            int cols = grid[0].length;
            for (int i = 1; i < cols; i++) {
                char[][] sliceTop = sliceGrid(grid, i, cols, 1);
                char[][] sliceBottom = sliceGrid(grid, 0, cols-i, 1);
                if (countDiff(sliceTop, reflectGrid(sliceTop, 1))==1) {
                    if (cols-i > maxCol) {
                        maxCol = (cols-i)/2;
                        colIndex = i + (cols-i)/2;
                    }
                }

                if (countDiff(sliceBottom, reflectGrid(sliceBottom, 1))==2) {
                    if (cols-i > maxCol) {
                        maxCol = (cols-i)/2;
                        colIndex = (cols-i)/2;
                    }
                }
            }
            if (maxRow > maxCol) {
                result += 100*rowIndex;
            } else {
                result += colIndex;
            }
        }
        System.out.println(STR."Result for part 2: \{result}");
    }

    private static int countDiff(char[][] grid1, char[][] grid2) {
        int count = 0;
        for (int i = 0; i < grid1.length; i++) {
            for (int j = 0; j < grid1[0].length; j++) {
                if (grid1[i][j] != grid2[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    private static char[][] sliceGrid(char[][] grid, int start, int end, int axis) {
        char[][] result;
        if (axis == 0) {
            result = new char[end - start][grid[0].length];
            if (end - start >= 0) System.arraycopy(grid, start, result, 0, end - start);
        } else {
            result = new char[grid.length][end - start];
            for (int i = 0; i < grid.length; i++) {
                if (end - start >= 0) System.arraycopy(grid[i], start, result[i], 0, end - start);
            }
        }
        return result;
    }

    private static char[][] reflectGrid(char[][] grid, int axis) {
        int rows = grid.length;
        int cols = grid[0].length;
        char[][] reflected = new char[rows][cols];
        if (axis == 0) {
            for (int i = 0; i < rows; i++) {
                System.arraycopy(grid[rows - i - 1], 0, reflected[i], 0, cols);
            }
        } else {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    reflected[i][j] = grid[i][cols-j-1];
                }
            }

        }
        return reflected;
    }

    private static char[][] buildGrid(String text) {
        String[] lines = text.split("\n");

        char[][] grid = new char[lines.length][];

        for (int i = 0; i < lines.length; i++){
            grid[i] = lines[i].toCharArray();
        }
        return grid;

    }
}
