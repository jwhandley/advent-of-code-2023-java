import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day4 {
    public static void part1() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("Inputs/Day4/input.txt"));
        int result = 0;
        for (String line: lines) {
            String[] game = line.split(": ")[1].split(" \\| ");
            HashSet<Integer> winningNumbers = getNumberSet(game[0]);
            List<Integer> card = getNumberList(game[1]);
            int score = calculateCardScore(card, winningNumbers);
            result += (int) Math.pow(2, (score-1));
            // System.out.println(STR."Winning numbers: \{winningNumbers}, Card: \{card}, Score: \{score}");
        }

        System.out.println(STR."Result for part 1: \{result}");
    }

    static HashSet<Integer> getNumberSet(String input) {
        HashSet<Integer> numSet = new HashSet<>();
        Arrays.stream(input.trim().split("\\s+")).forEach(i -> {
            int num = Integer.parseInt(i);
            numSet.add(num);
        });
        return numSet;
    }

    static List<Integer> getNumberList(String input) {
        List<Integer> numList = new ArrayList<>();
        Arrays.stream(input.trim().split("\\s+")).forEach(i -> {
            int num = Integer.parseInt(i);
            numList.add(num);
        });
        return numList;
    }

    static int calculateCardScore(List<Integer> card, HashSet<Integer> winningNumbers) {
        int result = 0;
        for (Integer number : card) {
            if (winningNumbers.contains(number)) {
                result++;
            }
        }

        return result;
    }

    public static void part2() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("Inputs/Day4/input.txt"));
        HashMap<Integer, Integer> copyCount = new HashMap<>();
        int i = 0;
        for (String line: lines) {
            int numCopies;
            if (!copyCount.containsKey(i)) {
                numCopies = 1;
                copyCount.put(i, numCopies);
            } else {
                numCopies = copyCount.get(i);
            }


            String[] game = line.split(": ")[1].split(" \\| ");
            HashSet<Integer> winningNumbers = getNumberSet(game[0]);
            List<Integer> card = getNumberList(game[1]);
            int score = calculateCardScore(card, winningNumbers);

            for (int k = 0; k < numCopies; k++) {
                for (int j = 1; j <= score; j++) {
                    copyCount.put(i+j,copyCount.getOrDefault(i+j, 1)+1);
                }
            }
            i++;
        }

        System.out.println(STR."Result for part 1: \{copyCount.values().stream().mapToInt(Integer::intValue).sum()}");

    }
}
