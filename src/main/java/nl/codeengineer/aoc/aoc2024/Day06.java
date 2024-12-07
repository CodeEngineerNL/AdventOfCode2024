package nl.codeengineer.aoc.aoc2024;

import nl.codeengineer.aoc.AocSolver;

import java.awt.*;
import java.awt.image.WritableRenderedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class Day06 implements AocSolver {

    char[][]startMap;


    int width;
    int height;

    int posx;
    int posy;

    int dir;

    char[] dirs = {'^', '>', 'v', '<'};

    record Step(int x, int y, int dir){};

    List<Step> taken = new ArrayList<>();

    public boolean isValidCoord(int x, int y) {
        return (x >= 0 && x < width) && (y >= 0 && y < height);
    }

    public Object part1() throws IOException {
        var map = getInput();
        this.startMap = cloneNap(map);

        var count = 1;

        while (true) {
            var step = new Step(posx, posy, dir);
            taken.add(step);

            var oldx = posx;
            var oldy = posy;

            switch (this.dirs[dir]) {
                case '^' -> posy -= 1;
                case 'v' -> posy += 1;
                case '<' ->  posx -= 1;
                case '>' -> posx += 1;
            }

            if (!isValidCoord(posx, posy)) {
                break;
            }

            if (map[posy][posx] == '#') {
                posx = oldx;
                posy = oldy;

                this.dir = (this.dir + 1) % dirs.length;
            } else {
                if (map[posy][posx] != 'X') {
                    map[posy][posx] = 'X';
                    count++;
                }
            }

            //printMap(map);
        }

        return count;
    }

    public void printMap(char[][] map, Step obs) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char c = map[y][x];
                if (x == posx && y == posy) {
                    c = this.dirs[this.dir];
                }
                if (obs.x == x && obs.y == y) {
                    c = 'O';
                }

                System.out.print(c);
            }
            System.out.println();
        }

        System.out.println();
        System.out.println();
    }


    // 1722 too high
    public Object part2() throws IOException {

        var loopCount = 0;

        for (int i =1 ; i < taken.size(); i++) {
            var map = cloneNap(startMap);


            Step start = taken.get(i - 1);

            Set<Step> seen = new HashSet<>();

            for (int j = 0; j < i - 1; j++) {
                var step = taken.get(j);
                map[step.y][step.x] = dirs[step.dir];
                seen.add(step);
            }

            //printMap(map);

            posx = start.x;
            posy = start.y;
            dir = start.dir;

            var obstacle = taken.get(i);
            map[obstacle.y][obstacle.x] = '#';

            while (true) {
                printMap(map, obstacle);
                var step = new Step(posx, posy, dir);
                if (seen.contains(step)) {
                    printMap(startMap, obstacle);
                    loopCount++;
                    break;
                }

                var oldx = posx;
                var oldy = posy;

                switch (this.dirs[dir]) {
                    case '^' -> posy -= 1;
                    case 'v' -> posy += 1;
                    case '<' ->  posx -= 1;
                    case '>' -> posx += 1;
                }

                if (!isValidCoord(posx, posy)) {
                    break;
                }

                if (map[posy][posx] == '#') {
                    posx = oldx;
                    posy = oldy;

                    this.dir = (this.dir + 1) % dirs.length;
                } else {
                    seen.add(step);
                }
            }
        }


        return loopCount;
    }

    public char[][] cloneNap(char[][] map) {
        var newMap = new char[map.length][map[0].length];

        for (int i = 0; i < map.length; i++) {
            System.arraycopy(map[i], 0, newMap[i], 0, map[i].length);
        }

        return newMap;
    }

    public char[][] getInput() throws IOException {
        var lines = Files.readAllLines(Path.of("inputs", "day06-test.txt"));

        this.height = lines.size();
        this.width = lines.get(0).length();

        var result = new char[height][width];

        for (int y = 0; y < height; y++) {
            String line = lines.get(y);
            for (int x = 0; x < width; x++) {
                var c =  line.charAt(x);

                if ("<^>v".contains(c + "")) {
                    //result[y][x] = 'X';

                    this.posx = x;
                    this.posy = y;

                    for (int i = 0; i < dirs.length; i++) {
                        if (dirs[i] == c) {
                            dir = i;
                        }
                    }
                }
                result[y][x] = c;
            }
        }

        return result;
    }
}
