package playground.stanford.greedy;

public class WeightLengthRatio implements GreedyScore {

    private final Job job;
    private final double weightLengthRatio;

    public WeightLengthRatio(Job job) {
        this.job = job;
        this.weightLengthRatio = ((double)job.weight)/job.length;
    }

    @Override
    public double getGreedyScore() {
        return weightLengthRatio;
    }

    @Override
    public Job getJob() {
        return job;
    }

    @Override
    public int compareTo(GreedyScore that) {
        // Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater
        // than the specified object.
        double diff = weightLengthRatio - that.getGreedyScore();
        if (diff < 0) {
            return -1;
        }
        else if (diff == 0d) {
            return 0;
        }
        else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return job + "{" + weightLengthRatio + "}";
    }
}
