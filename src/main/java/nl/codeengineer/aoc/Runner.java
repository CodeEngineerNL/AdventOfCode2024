package nl.codeengineer.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("java:S106")
public class Runner {
    private static final String PACKAGE = "nl.codeengineer.aoc";

    public static void main(String[] args) throws IOException {
        Runner runner = new Runner();
        runner.runYear("2024", "02");
    }


    public void runYear(String year, String day) throws IOException {
        List<Object> daysToRun = getDaySolversToRun(year, day);

        System.out.printf("| %4s | %20s | %20s | %13s | %13s |%n", "Day", "Part 1", "Part 2", "Time p1", "Time p2");
        System.out.println("--------------------------------------------------------------------------------------");
        for (Object d: daysToRun) {
            AocSolver daySolver = (AocSolver) d;

            //while (true) {
                long start = System.nanoTime();
                Object res1 = daySolver.part1();
                long time1 = System.nanoTime() - start;

                start = System.nanoTime();
                Object res2 = daySolver.part2();
                long time2 = System.nanoTime() - start;

                System.out.printf("|%5s | %20s | %20s | %10.2f ms | %10.2f ms |%n", daySolver.getClass().getSimpleName().replace("Day", ""), res1, res2, time1 / 1000.0 / 1000.0, time2 / 1000.0 / 1000.0);
           //}
        }
        System.out.println("--------------------------------------------------------------------------------------");
    }

    private List<Object> getDaySolversToRun(String year) {
        return getDaySolversToRun(year, "");
    }

    private List<Object> getDaySolversToRun(String year, String day) {
        final String searchDay = day == null ? "" : day;

        var location = PACKAGE.replaceAll("[.]", "/") + "/aoc" + year;

        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(location);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        return reader.lines()
                .filter(line -> line.startsWith("Day" + searchDay) && line.endsWith(".class") && !line.contains("$"))
                .sorted(Comparator.naturalOrder())
                .map(line -> getClass(line, PACKAGE + ".aoc" + year))
                .map(this::getInstance)
                .toList();
    }

    private Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // Should not happen, yeah, I know it will...
        }
        return null;
    }

    private Object getInstance(Class clasz) {
        try {
            return clasz.getConstructors()[0].newInstance();
        } catch (Exception e) {
            // Should not happen, yeah, I know it will...
        }
        return null;
    }


}
