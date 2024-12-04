package nl.codeengineer.aoc.aoc2024;

import nl.codeengineer.aoc.AocSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day04 implements AocSolver {

    private int height;
    private int width;

    public Object part1() throws IOException {
        final String SEARCH = "XMAS";

        var map = getInput();

        var total = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (map[y][x] == SEARCH.charAt(0)) {

                    for (int ydir = -1; ydir <= 1; ydir++) {
                        for (int xdir = -1; xdir <= 1; xdir++) {
                            if (ydir == 0 && xdir == 0)
                                continue;

                            var xpos = x;
                            var ypos = y;
                            int i = 1;

                            for (int count = 1; count < SEARCH.length(); count++) {
                                xpos += xdir;
                                ypos += ydir;

                                if (isValidCoord(xpos, ypos) && map[ypos][xpos] == SEARCH.charAt(i)) {
                                    i++;
                                } else {
                                    break;
                                }
                            }

                            if (i == SEARCH.length()) {
                                total++;
                            }
                        }
                    }

                }

            }
        }

        return total;
    }

    public boolean isValidCoord(int x, int y) {
        return (x >= 0 && x < width) && (y >= 0 && y < height);
    }

    public Object part2() throws IOException {
        var map = getInput();
        var total = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (map[y][x] =='A') {
                    var count = 0;

                    if (isValidCoord(x - 1, y - 1) && isValidCoord(x + 1, y + 1)) {
                        if ((map[y - 1][x - 1] == 'M' && map[y + 1][x + 1] == 'S') ||
                              (map[y - 1][x - 1] == 'S' && map[y + 1][x + 1] == 'M')) {
                            count++;
                        }
                    }

                    if (isValidCoord(x - 1, y + 1) && isValidCoord(x + 1, y -1)) {
                        if ((map[y + 1][x - 1] == 'M' && map[y - 1][x + 1] == 'S') ||
                               (map[y + 1][x - 1] == 'S' && map[y - 1][x + 1] == 'M')) {
                            count++;
                        }
                    }

                    if (count == 2) {
                        total++;
                    }
                }
            }
        }

        return total;
    }

    public char[][] getInput() throws IOException {
        var lines = Files.readAllLines(Path.of("inputs", "day4.txt"));

        height = lines.size();
        width = lines.getFirst().length();

        var map = new char[height][width];

        for (int y = 0; y < height; y++) {
            var row = lines.get(y);
            for (int x = 0; x < width; x++) {
                map[y][x] = row.charAt(x);
            }
        }

        return map;
    }
}
