package playground.standford.graphs;

@SuppressWarnings("unused")
public interface Graph {
    void display();
    /**
     * Karger randomized contraction algorithm: iterate and contract random edges. Will mutate the graph (!).
     * @return the number of crossing edges in the final cut
     */
    int randomizedKargerContraction();
}
