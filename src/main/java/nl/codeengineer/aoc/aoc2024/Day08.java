package nl.codeengineer.aoc.aoc2024;

import nl.codeengineer.aoc.AocSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day08 implements AocSolver {

    public record MyPoint(int x, int y){}

    private int width;
    private int height;

    public Object part1() throws IOException {
        var input = getInput();

        Set<MyPoint> antiNodes = new HashSet<>();
        for (var entry: input.entrySet()) {
            antiNodes.addAll(getAntiNodes(entry.getValue(), false));
        }

        return antiNodes.size();
    }

    public Object part2() throws IOException {
        var input = getInput();

        Set<MyPoint> antiNodes = new HashSet<>();
        for (var entry: input.entrySet()) {
            antiNodes.addAll(getAntiNodes(entry.getValue(), true));
            if (entry.getValue().size() > 1) {
                antiNodes.addAll(entry.getValue());
            }

        }

        return antiNodes.size();
    }

    public void printMap(List<MyPoint> nodes, List<MyPoint> antiNodes) {
        var map = new char[height][width];

        nodes.forEach(n -> map[n.y][n.x] = 'O');
        antiNodes.forEach(n -> map[n.y][n.x] = 'A');

        for (var line: map) {
            System.out.println(new String(line));
        }
        System.out.println();
    }

    public Set<MyPoint> getAntiNodes(List<MyPoint> nodes, boolean isLine) {
        Set<MyPoint> antiNodes = new HashSet<>();

        for (int i = 0; i < nodes.size() - 1; i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                var node1 = nodes.get(i);
                var node2 = nodes.get(j);

                int xdiff = node1.x - node2.x;
                int ydiff = node1.y - node2.y;

                var antiNode1 = new MyPoint(node1.x + xdiff, node1.y + ydiff);
                if (!isLine && isValidCoord(antiNode1)) {
                    antiNodes.add(antiNode1);
                } else while (isValidCoord(antiNode1)) {
                    antiNodes.add(antiNode1);
                    antiNode1 = new MyPoint(antiNode1.x + xdiff, antiNode1.y + ydiff);

                }

                var antiNode2 = new MyPoint(node2.x - xdiff, node2.y - ydiff);

                if (!isLine && isValidCoord(antiNode2)) {
                    antiNodes.add(antiNode2);
                } else while (isValidCoord(antiNode2)) {
                    antiNodes.add(antiNode2);
                    antiNode2 = new MyPoint(antiNode2.x - xdiff, antiNode2.y - ydiff);
                }
            }
        }

        return antiNodes;
    }

    public boolean isValidCoord(MyPoint p) {
        return (p.x >= 0 && p.x < width) && (p.y >= 0 && p.y < height);
    }

    public Map<String, List<MyPoint>> getInput() throws IOException {
        var lines = Files.readAllLines(Path.of("inputs", "day08.txt"));
        HashMap<String, List<MyPoint>> result = new HashMap<>();

        this.height = lines.size();
        this.width = lines.getFirst().length();
        
        for (int y = 0; y < height; y++) {
            String line = lines.get(y);
            for (int x = 0; x < width; x++) {
                var c = line.charAt(x);
                if ('.' != c)  {
                    var list = result.getOrDefault("" + c, new ArrayList<>());
                    list.add(new MyPoint(x, y));
                    result.put("" + c, list);

                }
            }
        }

        return result;
    }
}
