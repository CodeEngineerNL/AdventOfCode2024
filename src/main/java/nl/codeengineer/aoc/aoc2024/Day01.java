package nl.codeengineer.aoc.aoc2024;

import nl.codeengineer.aoc.AocSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day01 implements AocSolver {

    public Object part1() throws IOException {
        var lines = getInput();

        var arr1 = new int[lines.size()];
        var arr2 = new int[lines.size()];


        for (int i = 0; i < lines.size(); i++) {
            var parts = lines.get(i).split(" ", 2);
            arr1[i] = Integer.parseInt(parts[0]);
            arr2[i] = Integer.parseInt(parts[1].trim());
        }

        Arrays.sort(arr1);
        Arrays.sort(arr2);

        long totalDist = 0;
        for (int i = 0; i < arr1.length; i++) {
            totalDist += Math.abs(arr2[i] - arr1[i]);
        }

        return totalDist;
    }

    public Object part2() throws IOException {
        var lines = getInput();

        var arr1 = new int[lines.size()];
        var arr2 = new int[lines.size()];


        for (int i = 0; i < lines.size(); i++) {
            var parts = lines.get(i).split(" ", 2);
            arr1[i] = Integer.parseInt(parts[0]);
            arr2[i] = Integer.parseInt(parts[1].trim());
        }

        HashMap<Integer, Integer> map = new HashMap<>();

        long simScore = 0;

        for (int i = 0; i < arr1.length; i++) {
            var searchVal = arr1[i];

            var score = map.get(searchVal);

            if (score == null) {
                var count = 0;
                for(int val: arr2) {
                    if (val == searchVal) {
                        count++;
                    }
                }
                score = count * searchVal;
                map.put(searchVal, score);
            }

            simScore += score;
        }

        return simScore;
    }

    public List<String> getInput() throws IOException {
        return Files.readAllLines(Path.of("inputs", "day1.txt"));
    }
}
