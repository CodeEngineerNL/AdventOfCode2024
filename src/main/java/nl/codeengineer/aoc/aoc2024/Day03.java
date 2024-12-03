package nl.codeengineer.aoc.aoc2024;

import nl.codeengineer.aoc.AocSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day03 implements AocSolver {

    public Object part1() throws IOException {
        var input = getInput();

        long total = 0;

        var i = 0;
        int pos = input.indexOf("mul(", i);


        while (pos > 0) {
            var kommaPos = input.indexOf(",", pos);
            var closingPos = input.indexOf(")", kommaPos);

            try {
                var a = Integer.parseInt(input.substring(pos + 4, kommaPos));
                var b = Integer.parseInt(input.substring(kommaPos + 1, closingPos));

                total += a * b;
                 i = closingPos;
            } catch (NumberFormatException e) {
                i = pos + 4;
            }

            pos = input.indexOf("mul(", i);
        }

        return total;
    }

    public Object part2() throws IOException {
        var input = getInput();

        long total = 0;

        var i = 0;
        int pos = input.indexOf("mul(", i);

        var dontPos = input.indexOf("don't()");
        if (dontPos == -1) {
            dontPos = Integer.MAX_VALUE;
        }

        while (pos > 0) {
            if (pos > dontPos) {
                i = input.indexOf("do()", dontPos+ 7);
                if (i == -1) {
                    break;
                }

                pos = input.indexOf("mul(", i);
                if (pos == -1) {
                    break;
                }


                dontPos = input.indexOf("don't()", pos);
                if (dontPos == -1) {
                    dontPos = Integer.MAX_VALUE;
                }

                continue;
            }

            var kommaPos = input.indexOf(",", pos);
            var closingPos = input.indexOf(")", kommaPos);

            try {
                var a = Integer.parseInt(input.substring(pos + 4, kommaPos));
                var b = Integer.parseInt(input.substring(kommaPos + 1, closingPos));

                total += a * b;

                i = closingPos;
            } catch (NumberFormatException e) {
                i = pos + 4;
            }

            pos = input.indexOf("mul(", i);
        }

        return total;
    }

    public String getInput() throws IOException {
        var lines = Files.readAllLines(Path.of("inputs", "day3.txt"));
        StringBuilder buf = new StringBuilder();

        lines.forEach(buf::append);
        return buf.toString();
    }
}
