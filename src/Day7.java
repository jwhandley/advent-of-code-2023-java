import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.util.Map.entry;

public class Day7 {
    public static void part1() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day7/input.txt"));
        TreeMap<Draw, Integer> drawMap = new TreeMap<>();
        Map<Character, Integer> cardMap = Map.ofEntries(entry('2', 2), entry('3', 3), entry('4', 4), entry('5', 5), entry('6', 6), entry('7', 7), entry('8', 8), entry('9', 9), entry('T', 10), entry('J', 11), entry('Q', 12), entry('K', 13), entry('A', 14));

        for (String drawAndBid : input) {
            String draw = drawAndBid.split(" ")[0];
            int bid = Integer.parseInt(drawAndBid.split(" ")[1]);
            drawMap.put(new Draw(draw, cardMap, false), bid);
        }

        int result = 0;
        int i = 1;
        for (int value : drawMap.values()) {
            result += i * value;
            i++;
        }

        System.out.println(STR."Result for part 1: \{result}");

    }

    public static void part2() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day7/input.txt"));
        TreeMap<Draw, Integer> drawMap = new TreeMap<>();
        Map<Character, Integer> cardMap = Map.ofEntries(entry('J', 1), entry('2', 2), entry('3', 3), entry('4', 4), entry('5', 5), entry('6', 6), entry('7', 7), entry('8', 8), entry('9', 9), entry('T', 10), entry('Q', 11), entry('K', 12), entry('A', 13));

        for (String drawAndBid : input) {
            String draw = drawAndBid.split(" ")[0];
            int bid = Integer.parseInt(drawAndBid.split(" ")[1]);
            drawMap.put(new Draw(draw, cardMap, true), bid);
        }

        int result = 0;
        int i = 1;
        for (int value : drawMap.values()) {
            result += i * value;
            i++;
        }

        System.out.println(STR."Result for part 2: \{result}");

    }

    enum DrawType {
        FiveOfAKind(7), FourOfAKind(6), FullHouse(5), ThreeOfAKind(4), TwoPair(3), OnePair(2), HighCard(1);
        final int value;

        DrawType(int i) {
            this.value = i;
        }
    }

    static class Draw implements Comparable<Draw> {
        DrawType type;
        Card[] draw;
        String input;

        Draw(String input, Map<Character, Integer> cardMap, boolean wildCard) {
            Card[] draw = new Card[5];
            for (int i = 0; i < 5; i++) {
                draw[i] = new Card(input.charAt(i), cardMap);
            }

            this.draw = draw;
            this.type = parseDraw(input, wildCard);
            this.input = input;
        }

        public static DrawType parseDraw(String input, boolean wildCard) {
            HashMap<Character, Integer> cardMap = new HashMap<>();
            int jokerCount = 0;
            if (wildCard) {
                for (Character c : input.toCharArray()) {
                    if (c == 'J') {
                        jokerCount++;
                        continue;
                    }
                    cardMap.merge(c, 1, Integer::sum);
                }
            } else {
                for (Character c : input.toCharArray()) {
                    cardMap.merge(c, 1, Integer::sum);
                }
            }


            List<Integer> values = new ArrayList<>(cardMap.values());
            Collections.sort(values);

            int maxVal = values.isEmpty() ? jokerCount : values.getLast() + jokerCount;

            return switch (maxVal) {
                case 5 -> DrawType.FiveOfAKind;
                case 4 -> DrawType.FourOfAKind;
                case 3 -> values.getFirst() == 1 ? DrawType.ThreeOfAKind : DrawType.FullHouse;
                case 2 -> values.get(1) == 2 ? DrawType.TwoPair : DrawType.OnePair;
                default -> DrawType.HighCard;
            };
        }

        @Override
        public String toString() {
            return this.input;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Draw that)) return false;

            if (this.type != that.type) {
                return false;
            }

            return this.compareTo(that) == 0;
        }

        @Override
        public int compareTo(@NotNull Draw that) {
            int typeComparison = Integer.compare(this.type.value, that.type.value);

            if (typeComparison == 0) {
                for (int i = 0; i < 5; i++) {
                    if (this.draw[i].value == that.draw[i].value) continue;
                    return Integer.compare(this.draw[i].value, that.draw[i].value);
                }
            }

            return typeComparison;
        }
    }

    static class Card {
        Map<Character, Integer> cardMap;

        int value;

        Card(char input, Map<Character, Integer> cardMap) {
            this.cardMap = cardMap;
            this.value = cardMap.get(input);
        }

    }
}


