import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.util.Map.entry;

public class Day7 {
    public static void part1() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day7/input.txt"));
        TreeMap<Hand, Integer> drawMap = new TreeMap<>();
        Map<Character, Integer> cardMap = Map.ofEntries(entry('2', 2), entry('3', 3), entry('4', 4), entry('5', 5), entry('6', 6), entry('7', 7), entry('8', 8), entry('9', 9), entry('T', 10), entry('J', 11), entry('Q', 12), entry('K', 13), entry('A', 14));

        for (String data : input) {
            String hand = data.split(" ")[0];
            int bid = Integer.parseInt(data.split(" ")[1]);
            drawMap.put(new Hand(hand, cardMap, false), bid);
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
        TreeMap<Hand, Integer> drawMap = new TreeMap<>();
        Map<Character, Integer> cardMap = Map.ofEntries(entry('J', 1), entry('2', 2), entry('3', 3), entry('4', 4), entry('5', 5), entry('6', 6), entry('7', 7), entry('8', 8), entry('9', 9), entry('T', 10), entry('Q', 11), entry('K', 12), entry('A', 13));

        for (String data : input) {
            String hand = data.split(" ")[0];
            int bid = Integer.parseInt(data.split(" ")[1]);
            drawMap.put(new Hand(hand, cardMap, true), bid);
        }

        int result = 0;
        int i = 1;
        for (int value : drawMap.values()) {
            result += i * value;
            i++;
        }

        System.out.println(STR."Result for part 2: \{result}");

    }

    enum HandType {
        FiveOfAKind(), FourOfAKind(), FullHouse(), ThreeOfAKind(), TwoPair(), OnePair(), HighCard();

        HandType() {
        }
    }

    static class Hand implements Comparable<Hand> {
        final HandType type;
        final int[] draw;
        final String input;

        Hand(String input, Map<Character, Integer> cardMap, boolean wildCard) {
            int[] draw = new int[5];
            for (int i = 0; i < 5; i++) {
                draw[i] = cardMap.get(input.charAt(i));
            }

            this.draw = draw;
            this.type = parseHand(input, wildCard);
            this.input = input;
        }

        public static HandType parseHand(String input, boolean wildCard) {
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
                case 5 -> HandType.FiveOfAKind;
                case 4 -> HandType.FourOfAKind;
                case 3 -> values.getFirst() == 1 ? HandType.ThreeOfAKind : HandType.FullHouse;
                case 2 -> values.get(1) == 2 ? HandType.TwoPair : HandType.OnePair;
                default -> HandType.HighCard;
            };
        }

        @Override
        public String toString() {
            return this.input;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Hand that)) return false;

            if (this.type != that.type) {
                return false;
            }

            return this.compareTo(that) == 0;
        }

        @Override
        public int compareTo(@NotNull Hand that) {
            int typeComparison = Integer.compare(that.type.ordinal(), this.type.ordinal());

            if (typeComparison == 0) {
                for (int i = 0; i < 5; i++) {
                    if (this.draw[i] == that.draw[i]) continue;
                    return Integer.compare(this.draw[i], that.draw[i]);
                }
            }

            return typeComparison;
        }
    }
}


