package nl.codeengineer.aoc.aoc2024;

import nl.codeengineer.aoc.AocSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day05 implements AocSolver {

    Map<Integer, Set<Integer>> ordering = new HashMap<>();


    public Object part1() throws IOException {
        var updates = getInput();

        long total = 0;

        for (List<Integer> update: updates) {
            if (isValidUpdate(update)) {
                var m = update.get((update.size() / 2));
                total += m;
            }
        }

        return total;
    }

    public Object part2() throws IOException {
        var updates = getInput();

        long total = 0;

        for (List<Integer> update: updates) {
            if (!isValidUpdate(update)) {
                while (!isValidUpdate(update)) {
                    update = fixList(update);
                }

                var m = update.get((update.size() / 2));
                total += m;
            }
        }


        return total;
    }

    public boolean isValidUpdate(List<Integer> check) {
        var update = check.reversed();

        for (int i = 0; i < update.size() - 1; i++) {
            Set<Integer> o = ordering.get(update.get(i));
            if (o == null) {
                continue;
            }

            for (int j = i; j < update.size(); j++) {
                if (o.contains(update.get(j))) {
                        return false;
                }
            }
        }

        return true;
    }

    public List<Integer> fixList(List<Integer> check) {
        var update = check.reversed();


        for (int i = 0; i < update.size() - 1; i++) {
            Set<Integer> o = ordering.get(update.get(i));
            if (o == null) {
                continue;
            }

            for (int j = i; j < update.size(); j++) {
                if (o.contains(update.get(j))) {
                   switchItems(update, i, j);
                   return update.reversed();
                }
            }
        }

        return update.reversed();
    }


    public void switchItems(List<Integer> items, int x, int y) {
        var b = items.get(x);
        items.remove(x);
        items.add(y, b);
    }

    public List<List<Integer>> getInput() throws IOException {
        var lines = Files.readAllLines(Path.of("inputs", "day05.txt"));

        int i;
        for (i = 0; i < lines.size(); i++) {
            var line = lines.get(i);

            if (line.isEmpty()) {
                break;
            }

            var parts = line.split("\\|");
            int first = Integer.parseInt(parts[0]);
            int second = Integer.parseInt(parts[1]);

            var l = ordering.getOrDefault(first, new HashSet<>());
            l.add(second);
            ordering.put(first, l);
        }

        var result = new ArrayList<List<Integer>>();

        for (int j = i + 1; j < lines.size(); j++) {
            String line = lines.get(j);
            var parts = line.split(",");

            List<Integer> update = new ArrayList<>();
            for (var p: parts) {
                update.add(Integer.parseInt(p));
            }
            result.add(update);
        }

        return result;
    }
}
