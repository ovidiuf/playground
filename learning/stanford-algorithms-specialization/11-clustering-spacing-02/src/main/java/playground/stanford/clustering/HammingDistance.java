package playground.stanford.clustering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"EqualsWithItself", "ComparatorResultComparison"})
public class HammingDistance implements Comparable<HammingDistance> {

    public Point p1;
    public Point p2;
    public int distance;

    public HammingDistance(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.distance = hammingDistance(p1.content, p2.content);
    }

    /**
     * Package protected for testing.
     */
    public HammingDistance(int distance) {
        this.p1 = null;
        this.p2 = null;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "<" + (p1 == null ? "-" : p1.id) + "," + (p2 == null ? "-" : p2.id) + ">:" + distance;
    }

    @Override
    public int compareTo(HammingDistance that) {
        return distance - that.distance;
    }

    public static int hammingDistance(int i1, int i2) {
        int setBits = 0;
        int xor = i1 ^ i2;
        while(xor > 0) {
            setBits += xor & 1;
            xor >>= 1;
        }
        return setBits;
    }

    public static void unitTests() {
        Point p1 = new Point(0, 24, "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0");
        Point p2 = new Point(1, 24, "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1");
        HammingDistance d1 = new HammingDistance(p1, p2);
        assert 1 == d1.distance;

        p1 = new Point(0, 24, "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1");
        p2 = new Point(1, 24, "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1");
        HammingDistance d2 = new HammingDistance(p1, p2);
        assert 0 == d2.distance;

        p1 = new Point(0, 24, "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0");
        p2 = new Point(1, 24, "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0");
        HammingDistance d3 = new HammingDistance(p1, p2);
        assert 2 == d3.distance;

        p1 = new Point(0, 24, "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0");
        p2 = new Point(1, 24, "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1");
        HammingDistance d4 = new HammingDistance(p1, p2);
        assert 24 == d4.distance;

        assert d1.compareTo(d1) == 0;
        assert d1.compareTo(d2) == 1;
        assert d2.compareTo(d1) == -1;

        List<HammingDistance> distances = new ArrayList<>();
        distances.add(d4);
        distances.add(d3);
        distances.add(d1);
        distances.add(d2);
        Collections.sort(distances);
        assert distances.get(0).equals(d2);
        assert distances.get(1).equals(d1);
        assert distances.get(2).equals(d3);
        assert distances.get(3).equals(d4);
    }
}
