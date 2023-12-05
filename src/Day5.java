import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day5 {
    public static void part1() throws IOException {
        String input = Files.readString(Path.of("Inputs/Day5/input.txt"));

        // Parse seeds
        String seedData = input.split("\n")[0].split(": ")[1];
        List<Long> seeds = new ArrayList<>();
        for (String seed : seedData.split("\\s+")) {
            seeds.add(Long.parseLong(seed));
        }

        // Parse maps
        List<long[][]> maps = Arrays.stream(input.split("\n\n")).skip(1).map(Day5::parseMap).toList();


        for (long[][] map : maps) {
            seeds = seeds.stream().map(i -> convertSeed(i, map)).toList();
        }
        System.out.println(STR. "Result for part 1: \{ Collections.min(seeds) }" );
    }

    static long convertSeed(long input, long[][] map) {
        for (long[] line : map) {
            if (input >= line[1] && input <= line[1] + line[2]) {
                return input + line[0] - line[1];
            }
        }

        return input;
    }

    static long[][] parseMap(String map) {
        String[] mapData = map.split("\n");
        int n = mapData.length;
        long[][] result = new long[n - 1][3];

        for (int i = 0; i < n - 1; i++) {
            String[] lineData = mapData[i + 1].split(" ");
            result[i][0] = Long.parseLong(lineData[0]);
            result[i][1] = Long.parseLong(lineData[1]);
            result[i][2] = Long.parseLong(lineData[2]);
        }

        return result;
    }

    static long feedForward(long input, List<long[][]> maps) {
        for (long[][] map : maps) {
            input = convertSeed(input, map);
        }

        return input;
    }

    public static void part2() throws IOException {
        String input = Files.readString(Path.of("Inputs/Day5/input.txt"));

        // Gather seeds
        String[] seedData = input.split("\n")[0].split(": ")[1].split("\\s+");

        // Parse maps
        List<long[][]> maps = Arrays.stream(input.split("\n\n")).skip(1) // skips the first part (seeds)
                .map(Day5::parseMap).toList();

        long startTime = System.currentTimeMillis();
        long minValue = Long.MAX_VALUE;
        for (int i = 0; i < seedData.length; i += 2) {
            long low = Long.parseLong(seedData[i]);
            long high = low + Long.parseLong(seedData[i + 1]);
            System.out.println(STR. "Processing range \{ low } - \{ high }" );
            long start = System.currentTimeMillis();

            while (low < high) {
                minValue = Math.min(feedForward(low, maps), minValue);
                low++;
            }
            System.out.println(STR. "Took \{ System.currentTimeMillis() - start }ms to finish" );
        }
        System.out.println(STR."Took \{ System.currentTimeMillis() - startTime }ms to find the minimum value");


        System.out.println(STR. "Result for part 2: \{ minValue }" );


    }
}
