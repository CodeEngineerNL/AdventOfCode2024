package nl.codeengineer.aoc.aoc2024;

import nl.codeengineer.aoc.AocSolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day06 implements AocSolver {

    char[][]startMap;


    int width;
    int height;

    int startX;
    int startY;

    int startDir;

    char[] dirs = {'^', '>', 'v', '<'};

    record Step(int x, int y, int dir){}

    List<Step> taken = new ArrayList<>();

    public boolean isValidCoord(int x, int y) {
        return (x >= 0 && x < width) && (y >= 0 && y < height);
    }

    public Object part1() throws IOException {
        var map = getInput();
        this.startMap = cloneNap(map);

        int posx = startX;
        int posy = startY;
        int dir = startDir;

        var count = 1;

        map[posy][posx] = 'X';

        while (true) {
            var step = new Step(posx, posy, dir);

            var oldx = posx;
            var oldy = posy;

            switch (this.dirs[dir]) {
                case '^' -> posy -= 1;
                case 'v' -> posy += 1;
                case '<' ->  posx -= 1;
                case '>' -> posx += 1;
            }

            if (!isValidCoord(posx, posy)) {
                taken.add(step);
                break;
            }

            if (map[posy][posx] == '#') {
                posx = oldx;
                posy = oldy;

                dir = (dir + 1) % dirs.length;
            } else {
                taken.add(step);
                if (map[posy][posx] != 'X') {
                    map[posy][posx] = 'X';
                    count++;
                }
            }
        }

        return count;
    }

    public void printMap(char[][] map, Step obs) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char c = map[y][x];
                if (obs != null && obs.x == x && obs.y == y) {
                    c = 'O';
                }

                System.out.print(c);
            }
            System.out.println();
        }

        System.out.println();
    }

    public Object part2() throws IOException {

        record MyPoint(int x, int y) {}
        Set<MyPoint> obstacles = new HashSet<>();

        var loopCount = 0;

        for (int i =1 ; i < taken.size(); i++) {
            var map = cloneNap(startMap);

            Step start = taken.getFirst();

            Set<Step> seen = new HashSet<>();

            int posx = start.x;
            int posy = start.y;
            int dir = start.dir;

            var obstacle = new MyPoint(taken.get(i).x, taken.get(i).y);
            if (obstacles.contains(obstacle)) {
                continue;
            }
            obstacles.add(obstacle);

            map[obstacle.y][obstacle.x] = '#';

            while (true) {
                var step = new Step(posx, posy, dir);
                if (seen.contains(step)) {
                    obstacles.add(obstacle);
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

                    dir = (dir + 1) % dirs.length;
                    map[posy][posx] = dirs[dir];
                } else {
                    seen.add(step);
                }
                map[posy][posx] = dirs[dir];
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
        var lines = Files.readAllLines(Path.of("inputs", "day06.txt"));

        this.height = lines.size();
        this.width = lines.get(0).length();

        var result = new char[height][width];

        for (int y = 0; y < height; y++) {
            String line = lines.get(y);
            for (int x = 0; x < width; x++) {
                var c =  line.charAt(x);

                if ("<^>v".contains(c + "")) {
                    this.startX = x;
                    this.startY= y;

                    for (int i = 0; i < dirs.length; i++) {
                        if (dirs[i] == c) {
                            this.startDir = i;
                        }
                    }
                }

                result[y][x] = c;
            }
        }

        return result;
    }
}
