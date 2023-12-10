import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day2 {
    public static void part1() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day2/input.txt"));
        Map<String, Integer> bagContents = Map.of("red", 12, "green", 13, "blue", 14);
        int result = 0;
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            String game = line.split(": ")[1];
            String[] round = game.split("; ");
            boolean possible = true;
            for (String draws : round) {

                for (String draw : draws.split(", ")) {
                    int count = Integer.parseInt(draw.split(" ")[0]);
                    String color = draw.split(" ")[1];

                    if (bagContents.getOrDefault(color, 0) < count) {
                        possible = false;
                        break;
                    }
                }

            }

            if (possible) {
                result += i + 1;
            }
        }
        System.out.println(STR. "Result for part 1: \{ result }" );
    }

    public static void part2() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day2/input.txt"));

        int result = 0;
        for (String line : input) {
            String game = line.split(": ")[1];
            HashMap<String, Integer> gameMaxCount = getGameMaxCount(game);

            result += gameMaxCount.get("red") * gameMaxCount.get("green") * gameMaxCount.get("blue");
        }
        System.out.println(STR. "Result for part 2: \{ result }" );

    }

    @NotNull
    private static HashMap<String, Integer> getGameMaxCount(String game) {
        String[] round = game.split("; ");
        HashMap<String, Integer> gameMaxCount = new HashMap<>();
        for (String draws : round) {

            for (String draw : draws.split(", ")) {
                int count = Integer.parseInt(draw.split(" ")[0]);
                String color = draw.split(" ")[1];

                if (count > gameMaxCount.getOrDefault(color, 0)) {
                    gameMaxCount.put(color, count);
                }
            }
        }
        return gameMaxCount;
    }
}
