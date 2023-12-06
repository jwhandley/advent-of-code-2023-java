import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day1 {
    public static void part1() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day1/input.txt"));
        int result = 0;
        for (String line : input) {
            List<Integer> lineNumbers = new ArrayList<>();

            for (Character c : line.toCharArray()) {
                if (Character.isDigit(c)) lineNumbers.add(Character.getNumericValue(c));
            }

            int firstNumber = lineNumbers.getFirst();
            int lastNumber = lineNumbers.getLast();

            result += firstNumber * 10 + lastNumber;
        }

        System.out.println(STR. "Result for part 1: \{result}" );
    }

    public static void part2() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day1/input.txt"));
        int result = 0;

        for (String line : input) {
            List<Integer> lineNumbers = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                if (Character.isDigit(line.charAt(i))) {
                    lineNumbers.add(Character.getNumericValue(line.charAt(i)));
                }

                switch (line.substring(i)) {
                    case String s when s.startsWith("one") -> lineNumbers.add(1);
                    case String s when s.startsWith("two") -> lineNumbers.add(2);
                    case String s when s.startsWith("three") -> lineNumbers.add(3);
                    case String s when s.startsWith("four") -> lineNumbers.add(4);
                    case String s when s.startsWith("five") -> lineNumbers.add(5);
                    case String s when s.startsWith("six") -> lineNumbers.add(6);
                    case String s when s.startsWith("seven") -> lineNumbers.add(7);
                    case String s when s.startsWith("eight") -> lineNumbers.add(8);
                    case String s when s.startsWith("nine") -> lineNumbers.add(9);
                    default -> {
                    }
                }
            }
            int firstNumber = lineNumbers.getFirst();
            int lastNumber = lineNumbers.getLast();

            result += firstNumber * 10 + lastNumber;
        }

        System.out.println(STR. "Result for part 2: \{result}" );
    }
}
