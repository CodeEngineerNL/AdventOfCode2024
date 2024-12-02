package nl.codeengineer.aoc.aoc2024;

import nl.codeengineer.aoc.AocSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day02 implements AocSolver {

    public Object part1() throws IOException {
        var data = getInput();

        int total = 0;
        for (var row: data) {
            if (isSafe(row)) {
                total++;
            }
        }

        return total;
    }

    public Object part2() throws IOException {
        var data = getInput();

        int total = 0;
        for (var row: data) {


            if (isSafe(row)) {
                total++;
            } else {
                for (int i = 0; i < row.length; i++) {
                    var newRow = removeItem(row, i);

                    if (isSafe(newRow)) {
                        total++;
                        break;
                    }
                }
            }
        }

        return total;
    }

    public int[] removeItem(int[] arr, int i) {
        var result = new int[arr.length - 1];

        if (i == 0) {
            System.arraycopy(arr, 1, result, 0, arr.length -1);
            return result;
        }

        System.arraycopy(arr, 0, result, 0, i );
        System.arraycopy(arr, i + 1, result, i, arr.length - i - 1);
        return result;
    }

    public boolean isSafe(int[] nums) {
        int direction = nums[0] - nums[1];

        int min;
        int max;

        if (direction < 0) {
            min = -3;
            max = -1;
        } else {
            min = 1;
            max = 3;
        }


        for (int i = 1; i < nums.length; i++) {
            var diff = nums[i - 1] - nums[i];
            if (diff < min || diff > max) {
                return false;
            }
        }

        return true;
    }

    public List<int[]> getInput() throws IOException {
        var lines = Files.readAllLines(Path.of("inputs", "day2.txt"));

        var data = new ArrayList<int[]>();

        for (String line: lines) {
            var parts = line.split(" ");
            var row = new int[parts.length];
            for (int x = 0; x < parts.length; x++) {
                row[x] = Integer.parseInt(parts[x]);
            }

            data.add(row);
        }

        return data;
    }
}
