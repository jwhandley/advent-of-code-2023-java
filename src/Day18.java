import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day18 {
    public static void part1() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day18/input.txt"));
        List<Point> points = new ArrayList<>();
        int totalSize = 2;
        points.add(new Point(0,0));
        for (String line : input) {
            var currentPoint = points.getLast();
            String[] instructions = line.split(" ");
            String direction = instructions[0];
            int distance = Integer.parseInt(instructions[1]);


            switch (direction) {
                case "U" -> points.add(new Point(currentPoint.x, currentPoint.y - distance));
                case "D" -> points.add(new Point(currentPoint.x, currentPoint.y + distance));
                case "L" -> points.add(new Point(currentPoint.x - distance, currentPoint.y));
                case "R" -> points.add(new Point(currentPoint.x + distance, currentPoint.y));
            }

            totalSize += distance;

        }
        points.add(new Point(0,0));

        int A = 0;
        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i+1) % points.size());
            A += (p1.y + p2.y) * (p2.x - p1.x) / 2;
        }
        System.out.println(STR."Result for part 1: \{Math.abs(A) + totalSize/2}");

    }

    public static void part2() throws IOException {
        List<String> input = Files.readAllLines(Path.of("Inputs/Day18/input.txt"));

        long totalSize = 2;
        List<Point> points = new ArrayList<>();
        points.add(new Point(0,0));
        for (String line : input) {
            var currentPoint = points.getLast();
            String instructions = line.split(" ")[2].replaceAll("[#()]","");
            int direction = instructions.charAt(instructions.length()-1) - '0';
            int distance = Integer.parseInt(instructions.substring(0, instructions.length()-1),16);
            totalSize += distance;

            switch (direction) {
                case 0 -> points.add(new Point(currentPoint.x + distance, currentPoint.y));
                case 1 -> points.add(new Point(currentPoint.x, currentPoint.y + distance));
                case 2 -> points.add(new Point(currentPoint.x - distance, currentPoint.y));
                case 3 -> points.add(new Point(currentPoint.x, currentPoint.y - distance));
            }
        }
        points.add(new Point(0,0));

        long A = 0;
        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i+1) % points.size());
            A += (long) (p1.y + p2.y) * (p2.x - p1.x) / 2;
        }
        System.out.println(STR."Result for part 2: \{Math.abs(A) + totalSize/2}");

    }
}
