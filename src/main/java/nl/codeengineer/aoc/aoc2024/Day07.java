package nl.codeengineer.aoc.aoc2024;

import nl.codeengineer.aoc.AocSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day07 implements AocSolver {

    public record Equ(long total, long[] numbers){}

    public enum Op {
        PLUS, MUL, CONCAT
    }

    public Object part1() throws IOException {
        var equs = getInput();

        long total = 0;
        for (var e: equs) {
            if (startSolve(e, false)) {
                total += e.total;
            }
        }

        return total;
    }

    public Object part2() throws IOException {
        var equs = getInput();

        long total = 0;
        for (var e: equs) {
            if (startSolve(e, true)) {
                total += e.total;
            }
        }

        return total;
    }


    public boolean startSolve(Equ equ, boolean doConcat) {

        for (var op: Op.values()) {
            if (op == Op.CONCAT && !doConcat) {
                continue;
            }

            var result = solve(equ, 1, op, equ.numbers[0], doConcat);
            if (result == equ.total) {
                return true;
            }
        }

        return false;
    }


    public long solve(Equ equ, int i, Op op, long current, boolean doConcat) {
        long result = switch (op) {
            case MUL -> current * equ.numbers[i];
            case PLUS -> current + equ.numbers[i];
            case CONCAT -> {
                // Long.parseLong(String.valueOf(current).concat(String.valueOf(equ.numbers[i])));
                long pow = 10;
                while (equ.numbers[i] > pow) {
                    pow *= 10;
                }

                yield current * pow + equ.numbers[i];
            }
        };

        if (i == equ.numbers.length - 1) {
            return result;
        }

        var curResult = 0L;
        for (var curOp: Op.values()) {
            if (op == Op.CONCAT && !doConcat) {
                continue;
            }

            curResult = solve(equ, i + 1, curOp, result, doConcat);
            if (curResult == equ.total) {
                break;
            }
        }

        return curResult;
    }

    public List<Equ> getInput() throws IOException {
        var lines = Files.readAllLines(Path.of("inputs", "day07.txt"));
        List<Equ> result = new ArrayList<>(lines.size());

        for (var line : lines) {
            var parts = line.split(" ", 2);
            var total = Long.parseLong(parts[0].split(":")[0]);
            var numParts = parts[1].split(" ");

            var nums = new long[numParts.length];
            for (int i = 0; i < numParts.length; i++) {
                nums[i] = Long.parseLong(numParts[i]);
            }

            result.add(new Equ(total, nums));
        }

        return result;
    }
}
