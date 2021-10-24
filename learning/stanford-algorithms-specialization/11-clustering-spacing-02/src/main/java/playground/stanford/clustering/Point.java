package playground.stanford.clustering;

public class Point {

    // 0-based ID, coincides with the index of this point in the point array
    public int id;
    public int content; // only the last three bytes are used
    // the leader's label
    public int leader;

    public Point(int id, int bitCount, String s) {
        this.id = id;
        s = s.replace(" ", "");
        assert s.length() == bitCount;
        this.content = Integer.parseInt(s, 2);
    }

    @Override
    public String toString() {
        return "" + id + "→" + leader;
    }
}
