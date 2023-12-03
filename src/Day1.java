import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.lang.StringTemplate.STR;

public class Day1 {
    public static void part1() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day1/input.txt"));
        Set<Character> digits = Set.of('1','2','3','4','5','6','7','8','9');
        int result = 0;
        for (String line : input) {
            List<Integer> lineNumbers = new ArrayList<Integer>();

            for (Character c : line.toCharArray()) {
                if (digits.contains(c)) {
                    lineNumbers.add(Character.getNumericValue(c));
                }

            }
            int firstNumber = lineNumbers.getFirst();
            int lastNumber = lineNumbers.getLast();

            result += firstNumber * 10 + lastNumber;

            // System.out.println(line);
            // System.out.printf("First number %s, last number %s%n", lineNumbers.getFirst(), lineNumbers.getLast());
        }

        System.out.println(STR."Part 1 result: \{result}");
    }

    public static void part2() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day1/input.txt"));
        int result = 0;

        for (String line : input) {
            char[] lineChars = line.toCharArray();
            List<Integer> lineNumbers = new ArrayList<>();
            for (int i = 0; i < lineChars.length; i++) {
                if (Character.isDigit(lineChars[i])) {
                    lineNumbers.add(Character.getNumericValue(lineChars[i]));
                } else if (line.substring(i).startsWith("one")) {
                    lineNumbers.add(1);
                }  else if (line.substring(i).startsWith("two")) {
                    lineNumbers.add(2);
                } else if (line.substring(i).startsWith("three")) {
                    lineNumbers.add(3);
                } else if (line.substring(i).startsWith("four")) {
                    lineNumbers.add(4);
                }  else if (line.substring(i).startsWith("five")) {
                    lineNumbers.add(5);
                } else if (line.substring(i).startsWith("six")) {
                    lineNumbers.add(6);
                } else if (line.substring(i).startsWith("seven")) {
                    lineNumbers.add(7);
                }  else if (line.substring(i).startsWith("eight")) {
                    lineNumbers.add(8);
                } else if (line.substring(i).startsWith("nine")) {
                    lineNumbers.add(9);
                }
            }
            int firstNumber = lineNumbers.getFirst();
            int lastNumber = lineNumbers.getLast();

            result += firstNumber * 10 + lastNumber;
            /*
             System.out.println(line);
             System.out.printf("First number %s, last number %s%n", firstNumber, lastNumber);
            */
        }

        System.out.println(STR."Part 2 result: \{result}");
    }
}
