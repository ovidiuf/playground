package playground.stanford.greedy;

public class WeightLengthDifference implements GreedyScore {

    private final Job job;
    private final int weightLengthDifference;

    public WeightLengthDifference(Job job) {
        this.job = job;
        this.weightLengthDifference = job.weight - job.length;
    }

    @Override
    public double getGreedyScore() {
        return weightLengthDifference;
    }

    @Override
    public Job getJob() {
        return job;
    }

    @Override
    public int compareTo(GreedyScore that) {
        // Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater
        // than the specified object.
        int result = weightLengthDifference - (int)that.getGreedyScore();
        if (result != 0) {
            return result;
        }
        return job.weight - that.getJob().weight;
    }

    @Override
    public String toString() {
        return job.toString();
    }
}
