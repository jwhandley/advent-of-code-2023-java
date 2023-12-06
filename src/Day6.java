import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day6 {
    public static void part1() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day6/input.txt"));
        int[] times = parseValuesPart1(input.get(0));
        int[] distances = parseValuesPart1(input.get(1));

        System.out.println("Times: " + Arrays.toString(times));
        System.out.println("Distances: " + Arrays.toString(distances));

        int result = 1;
        for (int i = 0; i < times.length; i++) {

            int possibleWays = 0;
            int maxTime = times[i];
            for (int a = 1; a < maxTime; a++) {
                if (times[i] * a - a * a > distances[i]) {
                    possibleWays++;
                }
            }

            result *= possibleWays;
        }

        System.out.println(STR. "Result for part 1: \{ result }" );
    }

    private static int[] parseValuesPart1(String input) {
        String[] values = input.split("\\s+");
        int[] result = new int[values.length - 1];
        for (int i = 1; i < values.length; i++) {
            result[i - 1] = Integer.parseInt(values[i]);
        }

        return result;
    }

    private static long parseValuePart2(String input) {
        String[] values = input.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < values.length; i++) {
            result.append(values[i]);
        }
        return Long.parseLong(result.toString());
    }

    public static void part2() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day6/input.txt"));
        long time = parseValuePart2(input.get(0));
        long distance = parseValuePart2(input.get(1));

        double low = (double) time / 2.0 - Math.sqrt((double) time * (double) time - 4.0 * (double) distance) / 2.0;
        double high = (double) time / 2.0 + Math.sqrt((double) time * (double) time - 4.0 * (double) distance) / 2.0;

        long lowInt = (long) Math.ceil(low);
        long highInt = (long) Math.floor(high);

        System.out.println(STR. "Result for part 2: \{ highInt - lowInt + 1 }" );

    }
}
