import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day20 {

    static HashMap<String, Module> modules;
    static Queue<Message> messages;
    public static void part1() throws IOException {
        modules = parseInput(Path.of("Inputs/Day20/input.txt"));
        messages = new LinkedList<>();
        int lowTotal = 0;
        int highTotal = 0;
        int buttonPresses = 0;
        boolean foundLoop = false;
        while (!foundLoop && buttonPresses < 1000) {
            modules.get("broadcaster").receivePulse(0, null);
            buttonPresses++;

            int lowPulses = 1;
            int highPulses = 0;
            while (!messages.isEmpty()) {
                var next = messages.poll();
                modules.get(next.destination).receivePulse(next.activation, next.source);
                if (next.activation == 1) highPulses++;
                if (next.activation == 0) lowPulses++;
            }

            lowTotal += lowPulses;
            highTotal += highPulses;
            foundLoop = modules.values().stream().allMatch(m -> m.getClass() != FlipFlop.class || m.activation == 0);
        }

        System.out.println(STR."There were \{highTotal} high pulses and \{lowTotal} low pulses after \{buttonPresses} button presses");
        System.out.println(STR."After 1000 button presses, there would be \{highTotal * 1000/buttonPresses} high pulses and \{lowTotal * 1000/buttonPresses} low pulses");
        long result = highTotal * 1000L / buttonPresses * lowTotal * 1000L / buttonPresses;
        System.out.println(STR."Result for part 1: \{result}");
    }

    public static void part2() throws IOException {
        modules = parseInput(Path.of("Inputs/Day20/input.txt"));
        messages = new LinkedList<>();
        long buttonPresses = 0;

        HashMap<String, Long> loopTimes = new HashMap<>();
        boolean foundAllLoops = false;
        while (!foundAllLoops) {
            modules.get("broadcaster").receivePulse(0, null);
            buttonPresses++;

            while (!messages.isEmpty()) {
                var next = messages.poll();
                if (next.destination.equals("rx")) {
                    Conjunction source = (Conjunction) modules.get(next.source);

                    if (loopTimes.keySet().containsAll(source.incomingConnections.keySet())) foundAllLoops = true;

                    for (var entry : source.incomingConnections.entrySet()) {
                        String input = entry.getKey();
                        if (entry.getValue() == 1) {
                            loopTimes.put(input, buttonPresses);
                        }
                    }
                }
                modules.get(next.destination).receivePulse(next.activation, next.source);
            }
        }
        System.out.println(STR."Result for part 2: \{lcm(loopTimes.values().stream().toList())}");
    }

    private static long gcd(long x, long y) {
        //noinspection SuspiciousNameCombination
        return (y == 0) ? x : gcd(y, x % y);
    }

    public static long lcm(List<Long> numbers) {
        return numbers.stream().reduce(1L, (x, y) -> x * (y / gcd(x, y)));
    }

    private static HashMap<String, Module> parseInput(Path filepath) throws IOException {
        List<String> input = Files.readAllLines(filepath);
        HashMap<String, List<String>> edges = new HashMap<>();
        HashMap<String, Module> modules = new HashMap<>();
        for (String line : input) {
            String[] config = line.split(" -> ");
            String name = config[0].substring(1);
            switch (config[0].charAt(0)) {
                case 'b' -> {
                    name = "broadcaster";
                    var broadcaster = new Broadcaster(name);
                    modules.put(name, broadcaster);
                }
                case '%' -> {
                    var flipflop = new FlipFlop(name);
                    modules.put(name, flipflop);
                }
                case '&' -> {
                    var conjunction = new Conjunction(name);
                    modules.put(name, conjunction);
                }
            }
            String[] destinations = config[1].split(", ");
            edges.put(name, List.of(destinations));
        }

        for (var entry : edges.entrySet()) {
            String name = entry.getKey();
            List<String> destinations = entry.getValue();
            var mod = modules.get(name);
            for (String destination : destinations) {
                if (!modules.containsKey(destination)) {
                    modules.put(destination, new Receiver(destination));
                }
                mod.addOutgoingConnection(modules.get(destination));
            }
        }
        return modules;
    }

    static class Message {
        String source;
        String destination;
        int activation;

        public Message(String source, String destination, int activation) {
            this.source = source;
            this.destination = destination;
            this.activation = activation;
        }

        @Override
        public String toString() {
            return STR."\{source} -> \{destination}: \{activation}";
        }
    }

    static class Receiver extends Module {

        public Receiver(String name) {
            super(name);
        }

        @Override
        public void receivePulse(int value, String source) {

        }
    }

    static class Broadcaster extends Module {

        public Broadcaster(String name) {
            super(name);
        }

        @Override
        public void receivePulse(int value, String source) {
            activation = value;
            sendPulse();
        }
    }
    static class Conjunction extends Module {
        HashMap<String, Integer> incomingConnections;

        public Conjunction(String name) {
            super(name);
            this.activation = 0;
            this.incomingConnections = new HashMap<>();
            this.outgoingConnections = new ArrayList<>();
        }

        public void addIncomingConnection(String name) {
            incomingConnections.put(name, 0);
        }

        @Override
        public void receivePulse(int value, String source) {
            incomingConnections.put(source, value);
            if (incomingConnections.values().stream().allMatch(v -> v == 1)) {
                activation = 0;
            } else {
                activation = 1;
            }
            sendPulse();
        }

        public String toString() {
            return STR."\{name}: \{activation}, \{incomingConnections}";
        }
    }

    static class FlipFlop extends Module {
        public FlipFlop(String name) {
            super(name);
        }

        @Override
        public void receivePulse(int value, String source) {
            if (value == 0) {
                activation = 1 - activation;
                sendPulse();
            }
        }
    }

    static abstract class Module {
        int activation;
        String name;
        ArrayList<Module> outgoingConnections;

        public Module(String name) {
            this.activation = 0;
            this.outgoingConnections = new ArrayList<>();
            this.name = name;
        }

        public void addOutgoingConnection(Module module) {
            outgoingConnections.add(module);
            if (module.getClass() == Conjunction.class) {
                ((Conjunction) module).addIncomingConnection(this.name);
            }
        }

        public void sendPulse() {
            for (var destination : outgoingConnections) {
                var message = new Message(this.name, destination.name, activation);
                messages.add(message);
            }
        }

        public abstract void receivePulse(int value, String source);

        public String toString() {
            return STR."\{name}: \{activation}, \{outgoingConnections}";
        }
    }
}
