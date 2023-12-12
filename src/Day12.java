import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day12 {

    static HashMap<Long, Long> cache;

    public static void part1() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("Inputs/Day12/input.txt"));
        cache = new HashMap<>();
        long total = 0;
        for (String input : lines) {
            String sequence = input.split(" ")[0];
            List<Integer> numbers = Arrays.stream(input.split(" ")[1].split(",")).map(Integer::parseInt).toList();

            total += permutations(sequence, new ArrayList<>(numbers));
        }
        System.out.println(STR."Result for part 1: \{total}");
    }

    public static void part2() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("Inputs/Day12/input.txt"));
        cache.clear();
        long total = 0;
        for (String input : lines) {
            String fold = input.split(" ")[0];
            String sequence = repeatStringWithDelimiter(fold,"?", 5);
            String springs = input.split(" ")[1];
            List<Integer> numbers = Arrays.stream(repeatStringWithDelimiter(springs, ",", 5).split(",")).map(Integer::parseInt).toList();

            total += permutations(sequence, new ArrayList<>(numbers));
        }
        System.out.println(STR."Result for part 2: \{total}");
    }

    public static String repeatStringWithDelimiter(String str, String delimiter, int count) {
        if (count <= 0) {
            return "";
        }
        String[] array = new String[count];
        Arrays.fill(array, str);
        return String.join(delimiter, array);
    }

    public static long permutations(String input, List<Integer> nums) {
        if (input.isEmpty()) {
            return nums.isEmpty() ? 1 : 0;
        }

        if (nums.isEmpty()) {
            return input.contains("#") ? 0 : 1;
        }

        long key = (long) Integer.MAX_VALUE * (long) input.hashCode() + nums.hashCode();
        if (cache.containsKey(key)) {
            return cache.get(key);
        }


        char c = input.charAt(0);
        int n = nums.getFirst();

        long result = 0;
        if (c == '.' | c == '?') {
            result += permutations(input.substring(1), nums);
        }

        if (c == '#' | c == '?') {
            if (n <= input.length()) {
                if (!input.substring(0, n).contains(".")) {
                    if (n == input.length()) {
                        result += permutations("", nums.subList(1, nums.size()));
                    } else if (input.charAt(n) != '#') {
                        result += permutations(input.substring(n + 1), nums.subList(1, nums.size()));
                    }
                }
            }
        }
        cache.put(key, result);
        return result;
    }
}
