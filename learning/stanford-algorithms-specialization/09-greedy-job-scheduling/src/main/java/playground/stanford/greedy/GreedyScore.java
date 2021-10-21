package playground.stanford.greedy;

public interface GreedyScore extends Comparable<GreedyScore> {
    double getGreedyScore();
    Job getJob();
}
