package playground.leetcode.maxPointsOnALine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Util {

    public static Point[] loadInput(String[] args) throws Exception {

        if (args.length == 0) {

            throw new RuntimeException("specify an input file");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(args[0]))));

        String line = br.readLine();

        List<Point> points = new ArrayList<>();

        // [1,1],[2,2],[3,3]

        int crt = 0;

        while(crt < line.length()) {

            int i = line.indexOf('[', crt);

            if (i == -1) {

                break;
            }

            int j = line.indexOf(']', i);

            if (j == -1) {

                throw new RuntimeException("unbalanced [...]");
            }

            points.add(handlePair(line, i + 1, j));

            crt = j + 1;
        }


        br.close();

        Point[] result = new Point[points.size()];
        points.toArray(result);
        return result;
    }

    private static Point handlePair(String s, int from, int to) {

        String ss = s.substring(from, to);

        int i = ss.indexOf(',');

        if (i == -1) {

            throw new RuntimeException("illegal pair " + ss);
        }

        int x = Integer.parseInt(ss.substring(0, i));
        int y = Integer.parseInt(ss.substring(i + 1));

        return new Point(x, y);
    }

    public static void toCsv(Point[] points) throws Exception {

        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("./sample.csv")));

        bw.write("x, y\n");

        for(Point p: points) {

            bw.write(p.x + ", " + p.y + "\n");
        }

        bw.close();
    }
}
