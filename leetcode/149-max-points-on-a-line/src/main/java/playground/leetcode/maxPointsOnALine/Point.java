package playground.leetcode.maxPointsOnALine;

public class Point {

    int x;
    int y;

    Point() { x = 0; y = 0; }
    Point(int x, int y) { this.x = x; this.y = y; }

    @Override
    public boolean equals(Object o) {

        if (this == o) {

            return true;
        }

        if (!(o instanceof Point)) {

            return false;
        }

        Point that = (Point)o;

        return this.x == that.x && this.y == that.y;
    }

    @Override
    public int hashCode() {

        return 23 * x + 17 * y;
    }

    @Override
    public String toString() {

        return "[" + x + ", " + y + "]";
    }

}
