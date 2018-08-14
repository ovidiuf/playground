package playground.leetcode.maxPointsOnALine;

import java.util.ArrayList;
import java.util.List;

public class Line {

    private static double PRECISION = 0.000000000000000000000000000000001d;

    double a;
    double b;

    Integer vertical;

    List<Point> points;

    Line(Point p1, Point p2) {

        this.points = new ArrayList<>();

        if (p1.x == p2.x) {

            vertical = p1.x;
        }
        else {

            a = ((double)(p1.y - p2.y))/(p1.x - p2.x);
            b = ((double)(p1.x * p2.y - p1.y * p2.x))/(p1.x - p2.x);
        }
    }

    /**
     * @returns false if the point does not belong to the line
     **/
    public boolean addPoint(Point p) {

        if (vertical != null) {

            if (p.x != vertical) {

                return false;
            }
        }
        else {

            double y = a * p.x + b;

            double diff = Math.abs(y - (double)p.y);

            if (diff > PRECISION) {

                return false;
            }
        }

        points.add(p);
        return true;
    }

    public int pointCount() {

        return points.size();
    }

    @Override
    public String toString() {

        return "Line " + (vertical != null ? (" vertical " + vertical) : ("" + a + ", " + b)) + ", " + points.size() + " points";
    }

    @Override
    public boolean equals(Object o)  {

        if (this == o) {

            return true;
        }

        if (!(o instanceof Line)) {

            return false;
        }

        Line that = (Line)o;

        if (vertical != null) {

            return vertical.equals(that.vertical);
        }

        return Math.abs(a - that.a) < PRECISION && Math.abs(b - that.b) < PRECISION;
    }

    @Override
    public int hashCode() {

        if (vertical != null) {

            return 17 * vertical;
        }
        else {

            return ((int)(1024 * a)) * 17 + ((int)(2048 * b)) * 11;
        }
    }
}
