import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day4 {
    public static void part1() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("Inputs/Day4/input.txt"));
        int result = 0;
        for (String line: lines) {
            String[] game = line.split(": ")[1].split(" \\| ");
            HashSet<Integer> winningNumbers = getNumberSet(game[0]);
            List<Integer> card = getNumberList(game[1]);
            int score = calculateCardScore(card, winningNumbers);
            result += 1 << (score - 1);
        }

        System.out.println(STR."Result for part 1: \{result}");
    }

    static HashSet<Integer> getNumberSet(String input) {
        return Arrays.stream(input.trim().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(HashSet::new));
    }

    static List<Integer> getNumberList(String input) {
        return Arrays.stream(input.trim().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    static int calculateCardScore(List<Integer> card, HashSet<Integer> winningNumbers) {
        return (int) card.stream().filter(winningNumbers::contains).count();
    }

    public static void part2() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("Inputs/Day4/input.txt"));
        HashMap<Integer, Integer> copyCount = new HashMap<>();
        int i = 0;
        for (String line: lines) {
            int numCopies = copyCount.getOrDefault(i, 1);
            if (numCopies == 1) copyCount.put(i, 1);

            String[] game = line.split(": ")[1].split(" \\| ");
            HashSet<Integer> winningNumbers = getNumberSet(game[0]);
            List<Integer> card = getNumberList(game[1]);
            int score = calculateCardScore(card, winningNumbers);


            for (int k = 0; k < numCopies; k++) { // Number of copies we have of card
                for (int j = 1; j <= score; j++) { // Number of copies we get from card
                    copyCount.put(i+j,copyCount.getOrDefault(i+j, 1)+1);
                }
            }


            i++;
        }

        System.out.println(STR."Result for part 2: \{copyCount.values().stream().mapToInt(Integer::intValue).sum()}");

    }
}
