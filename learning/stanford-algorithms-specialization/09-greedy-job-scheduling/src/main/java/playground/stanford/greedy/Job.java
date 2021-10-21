package playground.stanford.greedy;

public class Job {

    public int weight;
    public int length;

    public Job(int weight, int length) {
        this.weight = weight;
        this.length = length;
    }

    @Override
    public String toString() {
        return "(" + weight + ", " + length + ")";
    }
}
