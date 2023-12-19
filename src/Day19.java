import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Day19 {
    public static void part1() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day19/input.txt"));
        HashMap<String, Workflow> workflows = new HashMap<>();

        int totalPoints = 0;
        boolean parsingScores = false;
        for (String line : input) {
            if (line.isEmpty()) {
                parsingScores = true;
                continue;
            }

            if (parsingScores) {
                var score = parseScore(line);
                String outcome = "in";
                while (true) {
                    outcome = workflows.get(outcome).scoreInput(score);
                    if (outcome.equals("A")) {
                        totalPoints += score.values().stream().mapToInt(Integer::intValue).sum();
                        break;
                    } else if (outcome.equals("R")) {
                        break;
                    }
                }
            } else {
                var workflow = parseWorkflow(line);
                workflows.put(workflow.name, workflow);
            }
        }
        System.out.println(STR."Result for part 1: \{totalPoints}");

    }

    public static void part2() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day19/input.txt"));
        HashMap<String, Workflow> workflows = new HashMap<>();

        for (String line : input) {
            if (line.isEmpty()) break;
            var workflow = parseWorkflow(line);
            workflows.put(workflow.name, workflow);
        }
        var ranges = Map.of("x", new Pair<>(1,4000),"m", new Pair<>(1,4000),"a", new Pair<>(1,4000),"s", new Pair<>(1,4000));
        long result = count(ranges, "in", workflows);

        System.out.println(STR."Result for part 2: \{result}");

    }

    private static long count(Map<String, Pair<Integer>> ranges, String name, HashMap<String, Workflow> workflows) {
        if (name.equals("R")) return 0;
        if (name.equals("A")) {
            long product = 1;
            for (var p : ranges.values()) {
                product *= p.high - p.low + 1;
            }
            return product;
        }

        var wf = workflows.get(name);
        long total = 0;
        for (var entry : wf.rules.entrySet()) {
            String condition = entry.getKey();
            String result = entry.getValue();
            String dimension = null;

            Pair<Integer> T = null;
            Pair<Integer> F = null;
            if (condition.contains(">")) {
                int value = Integer.parseInt(condition.split(">")[1]);
                dimension = condition.split(">")[0];
                var p = ranges.get(dimension);

                T = new Pair<>(Math.max(value + 1, p.low), p.high);
                F = new Pair<>(p.low, Math.min(value, p.high));
            } else if (condition.contains("<")) {
                int value = Integer.parseInt(condition.split("<")[1]);
                dimension = condition.split("<")[0];
                var p = ranges.get(dimension);

                T = new Pair<>(p.low, Math.min(value-1, p.high));
                F = new Pair<>(Math.max(p.low, value), p.high);
            }

            assert T != null;
            if (T.low <= T.high) {
                var newRanges = new HashMap<>(ranges);
                newRanges.put(dimension, T);
                total += count(newRanges, result, workflows);
            }

            if (F.low <= F.high) {
                ranges = new HashMap<>(ranges);
                ranges.put(dimension, F);
            } else {
                break;
            }
        }
        total += count(ranges, wf.last, workflows);
        return total;
    }

    private static Workflow parseWorkflow(String input) {
        var result = new Workflow();
        input = input.replaceAll("[{}]", " ");
        String[] workflow = input.split(" ");
        result.name = workflow[0];
        String[] rules = workflow[1].split(",");

        for (String rule : rules) {
            if (rule.contains(">") || rule.contains("<")) {
                var condition = rule.split(":")[0];
                var outcome = rule.split(":")[1];
                result.rules.put(condition, outcome);
            } else {
                result.last = rule;
            }
        }

        return result;
    }

    private static LinkedHashMap<String, Integer> parseScore(String input) {
        var score = new LinkedHashMap<String, Integer>();
        input = input.replaceAll("[{}]", "");
        String[] values = input.split(",");
        for (String value : values) {
            String[] record = value.split("=");
            String dimension = record[0];
            Integer number = Integer.parseInt(record[1]);
            score.put(dimension, number);
        }
        return score;
    }

    static class Workflow {
        String name;
        LinkedHashMap<String, String> rules;

        String last;

        Workflow() {
            this.rules = new LinkedHashMap<>();
        }

        @Override
        public String toString() {
            return STR."\{name}, \{rules}, \{last}";
        }

        String scoreInput(Map<String, Integer> score) {
            for (var entry : rules.entrySet()) {
                String condition = entry.getKey();
                String result = entry.getValue();

                if (condition.contains(">")) {
                    int value = Integer.parseInt(condition.split(">")[1]);
                    String dimension = condition.split(">")[0];

                    if (score.get(dimension) > value) {
                        return result;
                    }
                } else if (condition.contains("<")) {
                    int value = Integer.parseInt(condition.split("<")[1]);
                    String dimension = condition.split("<")[0];

                    if (score.get(dimension) < value) {
                        return result;
                    }
                }
            }
            return last;
        }
    }
}
