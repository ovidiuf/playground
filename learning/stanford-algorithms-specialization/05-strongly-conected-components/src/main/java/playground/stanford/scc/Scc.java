package playground.stanford.scc;

public class Scc implements Comparable<Scc>{

    private final int leader;
    private int count;

    public Scc(int leader) {
        this.leader = leader;
        this.count = 0;
    }

    public void incrementCount() {
        count ++;
    }

    @Override
    public int compareTo(Scc that) {
        return count - that.count;
    }

    @Override
    public String toString() {
        return "[" + leader + ", " + count + "]";
    }
}
