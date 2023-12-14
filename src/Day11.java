import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11 {
    static List<String> lines;
    public static void part1() throws IOException {
        lines = Files.readAllLines(Path.of("Inputs/Day11/input.txt"));
        int expansion = 2;
        int[] colMap = new int[lines.size()];
        int[] rowMap = new int[lines.size()];
        Arrays.fill(colMap, expansion);
        Arrays.fill(rowMap, expansion);

        List<Point> galaxies = new ArrayList<>();

        for (int r = 0; r < lines.size(); r++) {
            String row = lines.get(r);
            for (int c = 0; c < row.length(); c++) {
                if (row.charAt(c) == '#') {
                    rowMap[r] = 1;
                    colMap[c] = 1;
                    galaxies.add(new Point(r, c));
                }
            }
        }

        long totalDist = getTotalDist(galaxies, rowMap, colMap);

        System.out.println(STR."Result for part 1: \{totalDist}");
    }

    public static void part2() {
        int expansion = 1_000_000;
        int[] colMap = new int[lines.size()];
        int[] rowMap = new int[lines.size()];
        Arrays.fill(colMap, expansion);
        Arrays.fill(rowMap, expansion);

        List<Point> galaxies = new ArrayList<>();

        for (int r = 0; r < lines.size(); r++) {
            String row = lines.get(r);
            for (int c = 0; c < row.length(); c++) {
                if (row.charAt(c) == '#') {
                    rowMap[r] = 1;
                    colMap[c] = 1;
                    galaxies.add(new Point(r, c));
                }
            }
        }

        long totalDist = getTotalDist(galaxies, rowMap, colMap);

        System.out.println(STR."Result for part 2: \{totalDist}");
    }

    private static long getTotalDist(List<Point> galaxies, int[] rowMap, int[] colMap) {
        long totalDist = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                Point p1 = galaxies.get(i);
                Point p2 = galaxies.get(j);
                int x1 = subArraySum(rowMap, p1.x);
                int x2 = subArraySum(rowMap, p2.x);

                int y1 = subArraySum(colMap, p1.y);
                int y2 = subArraySum(colMap, p2.y);

                totalDist += Math.abs(x1-x2) + Math.abs(y1-y2);
            }
        }
        return totalDist;
    }

    private static int subArraySum(int[] arr, int index) {
        int sum = 0;
        for (int i = 0; i < index; i++) {
            sum += arr[i];
        }

        return sum;
    }
}
