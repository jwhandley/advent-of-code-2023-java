import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9 {

    static List<String> input;
    public static void part1() throws IOException {
        input = Files.readAllLines(Path.of("Inputs/Day9/input.txt"));
        int result = 0;
        for (String line : input) {
            List<Integer> nums = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();

            result += predictNext(nums);
        }

        System.out.println(STR."Result for part 1: \{result}");
    }

    private static List<Integer> findSlope(List<Integer> values) {
        List<Integer> diffs = new ArrayList<>();

        for (int i = 1; i < values.size(); i++) {
            diffs.add(values.get(i) - values.get(i - 1));
        }

        return diffs;
    }

    private static int predictNext(List<Integer> values) {
        if (findSlope(values).stream().allMatch(x -> x == 0)) return values.getLast();

        return values.getLast() - predictNext(findSlope(values));
    }

    private static int predictPrevious(List<Integer> values) {
        if (findSlope(values).stream().allMatch(x -> x == 0)) return values.getFirst();

        return values.getFirst() - predictPrevious(findSlope(values));
    }

    public static void part2() throws IOException {
        int result = 0;
        for (String line : input) {
            List<Integer> nums = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();

            result += predictPrevious(nums);
        }

        System.out.println(STR."Result for part 2: \{result}");

    }
}
