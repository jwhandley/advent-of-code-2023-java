import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day5 {
    static String input;
    static List<long[][]> maps;
    public static void part1() throws IOException {
        input = Files.readString(Path.of("Inputs/Day5/input.txt"));

        // Parse seeds
        String seedData = input.split("\n")[0].split(": ")[1];
        List<Long> seeds = new ArrayList<>();
        for (String seed : seedData.split("\\s+")) {
            seeds.add(Long.parseLong(seed));
        }

        // Parse maps
        maps = Arrays.stream(input.split("\n\n")).skip(1).map(Day5::parseMap).toList();

        long minValue = Long.MAX_VALUE;
        for (long seed : seeds) {
            minValue = Math.min(minValue, feedForward(seed, maps));
        }
        System.out.println(STR. "Result for part 1: \{ minValue }" );
    }

    private static long[][] parseMap(String map) {
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

    private static long feedForward(long input, List<long[][]> maps) {
        for (long[][] map : maps) {
            input = convertSeed(input, map);
        }

        return input;
    }

    private static long convertSeed(long input, long[][] map) {
        for (long[] line : map) {
            if (input >= line[1] && input <= line[1] + line[2]) {
                return input + line[0] - line[1];
            }
        }

        return input;
    }

    private static List<long[]> getRanges(List<long[]> seedRanges, List<long[][]> maps) {
        for (long[][] map : maps) {
            seedRanges = processMap(map, new LinkedList<>(seedRanges));
        }
        return seedRanges;
    }

    private static List<long[]> processMap(long[][] map, LinkedList<long[]> seedRanges) {
        LinkedList<long[]> result = new LinkedList<>();
        while (!seedRanges.isEmpty()) {
            long[] range = seedRanges.poll();
            boolean isRangeProcessed = false;

            for (long[] line : map) {
                long[] overlap = calculateOverlap(range, line);
                if (overlap != null) {
                    result.addFirst(new long[]{overlap[0] - line[1] + line[0], overlap[1] - line[1] + line[0]});
                    updateSeedRanges(seedRanges, range, overlap);
                    isRangeProcessed = true;
                    break;
                }
            }

            if (!isRangeProcessed) {
                result.addFirst(range);
            }
        }
        return result;
    }

    private static long[] calculateOverlap(long[] range, long[] line) {
        long low = line[1];
        long high = line[1] + line[2];
        long overlapStart = Math.max(range[0], low);
        long overlapEnd = Math.min(range[1], high);

        if (overlapStart < overlapEnd) {
            return new long[]{overlapStart, overlapEnd};
        }
        return null;
    }

    private static void updateSeedRanges(LinkedList<long[]> seedRanges, long[] range, long[] overlap) {
        if (overlap[0] > range[0]) {
            seedRanges.addFirst(new long[]{range[0], overlap[0]});
        }
        if (range[1] > overlap[1]) {
            seedRanges.addFirst(new long[]{overlap[1], range[1]});
        }
    }


    public static void part2() throws IOException {
        // Gather seeds
        String[] seedData = input.split("\n")[0].split(": ")[1].split("\\s+");
        LinkedList<long[]> seedRanges = new LinkedList<>();
        for (int i = 0; i < seedData.length; i += 2) {
            long low = Long.parseLong(seedData[i]);
            long high = low + Long.parseLong(seedData[i + 1]);
            seedRanges.add(new long[]{low, high});
        }

        var result = getRanges(seedRanges, maps);

        long minValue = Long.MAX_VALUE;
        for (long[] range : result) {
            minValue = Math.min(minValue, range[0]);
        }

        System.out.println(STR. "Result for part 2: \{ minValue }" );


    }
}
