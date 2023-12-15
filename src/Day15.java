import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day15 {
    public static void part1() throws IOException {
        String[] input = Files.readString(Path.of("Inputs/Day15/input.txt")).split(",");
        int result = 0;
        for (String text : input) {
            result += hash(text);
        }
        System.out.println(STR."Result for part 1: \{result}");
    }

    public static void part2() throws IOException {
        String[] input = Files.readString(Path.of("Inputs/Day15/input.txt")).split(",");
        HashMap<Integer, LinkedHashMap<String, Integer>> boxes = new HashMap<>();

        for (String instruction : input) {
            int n = instruction.length()-1;
            int value = -1;
            String key;
            if (Character.isDigit(instruction.charAt(n))) {
                value = Integer.parseInt(String.valueOf(instruction.charAt(n)));
                key = instruction.split("=")[0];
            } else {
                key = instruction.split("-")[0];
            }

            int hashCode = hash(key);
            LinkedHashMap<String, Integer> box;
            if (boxes.containsKey(hashCode)) {
                box = boxes.get(hashCode);
            } else {
                box = new LinkedHashMap<>();
            }

            if (value != -1) {
                box.put(key, value);
            } else {
                box.remove(key);
            }
            boxes.put(hashCode, box);
        }
        int result = 0;
        for (var boxEntry : boxes.entrySet()) {
            int pos = boxEntry.getKey() + 1;
            var box = boxEntry.getValue();
            List<Integer> values = new ArrayList<>(box.values());
            for (int i = 0; i < values.size(); i++) {
                int entryNumber = i + 1;
                int focalLength = values.get(i);
                //System.out.println(STR."Box number: \{pos}, Slot: \{entryNumber}, Length: \{focalLength}, Result: \{pos*entryNumber*focalLength}");
                result += pos * entryNumber * focalLength;
            }
        }
        System.out.println(STR."Result for part 2: \{result}");
    }

    private static int hash(String input) {
        int current = 0;
        for (char c : input.toCharArray()) {
            current += c;
            current *= 17;
            current = current % 256;
        }

        return current;
    }
}
