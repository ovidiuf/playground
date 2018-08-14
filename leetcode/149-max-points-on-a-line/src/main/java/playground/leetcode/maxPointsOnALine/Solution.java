package playground.leetcode.maxPointsOnALine;

import java.util.*;

public class Solution {

    public int maxPoints(Point[] points) {

        if (points.length == 1) {

            return 1;
        }

        Set<Line> lines = new HashSet<>();

        //
        // handle lines defined by distinct points
        //

        for(Point p1: points) {

            for(Point p2: points) {

                if (p1.equals(p2)) {

                    continue;
                }

                lines.add(new Line(p1, p2));
            }
        }

        System.out.println(lines.size() + " lines");

        for(Line l: lines) {

            for(Point p: points) {

                l.addPoint(p);
            }
        }

        int max = 0;

        for(Line l: lines) {

            if (l.pointCount() > max) {

                max = l.pointCount();
            }
        }

        //
        // handle lines defined by identical points
        //

        Map<Point, Integer> m = new HashMap<>();

        for(Point p: points) {

            Integer i = m.get(p);

            if (i == null) {

                m.put(p, 1);
            }
            else {

                m.put(p, i + 1);
            }
        }

        for(Integer i: m.values()) {

            if (i > max) {

                max = i;
            }
        }

        return max;

    }


}
