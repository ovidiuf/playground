package playground.stanford.scc;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class Kosaraju {

    /**
     * Counts the total number of nodes processed so far in the reverse DFS routine.
     * sed by the reverse DFS routine to compute the "finishing time".
     */
    private int t;

    /**
     *  Keeps track of the most recent vertex from which the DFS was initiated during the second pass (the leader).
     */
    private int leader;

    private final long t0;
    private int processedSoFar;
    private final int progressReportChunkSize;

    public Kosaraju() {
        t = 0;
        leader = -1;
        processedSoFar = 0;
        t0 = System.currentTimeMillis();
        progressReportChunkSize = 10000;
    }

    public void run(Graph g) {
        firstPass(g);
        g.reorderByFinishingTime();
        secondPass(g);
        displaySccs(g);
    }

    /**
     * Label nodes with "finishing time" of the reverse DFS.
     */
    private void firstPass(Graph g) {
        for(int i = g.size() - 1; i >= 0; i --) {
            if (!g.getVertex(i).isExplored()) {
                reverseDfs(g, i);
            }
        }
        System.out.println("first pass completed");
    }

    private void secondPass(Graph g) {
        for(int i = g.size() - 1; i >= 0; i --) {
            if (!g.getVertex(i).isExplored()) {
                leader = i;
                dfs(g, i);
            }
        }
        System.out.println("second pass completed");
    }

    /**
     * A depth-first search where we follow the directed arcs in reverse order.
     * @param s start vertex, 0-based
     */
    private void reverseDfs(Graph g, int s) {
        // mark s as explored
        g.getVertex(s).setExplored();
        // for every edge incident on s ("reverse") (p, s)
        List<Integer> vertices = g.getEdgesIncidentTo(s);
        for(int p: vertices) {
            // if p unexplored
            if (g.getVertex(p).isExplored()) {
                continue;
            }
            reverseDfs(g, p);
        }
        g.getVertex(s).setFinishingTime(t);
        t ++;
        reportProgress(g);
    }

    /**
     * A standard depth-first search, used during the second pass.
     * @param s start vertex, 0-based
     */
    private void dfs(Graph g, int s) {
        // mark s as explored
        g.getVertex(s).setExplored();
        // TODO
        g.getVertex(s).setLeader(leader);
        List<Integer> vertices = g.getEdgesIncidentFrom(s);
        for(int p: vertices) {
            // if p unexplored
            if (g.getVertex(p).isExplored()) {
                continue;
            }
            dfs(g, p);
        }
    }

    private void displaySccs(Graph g) {
        List<Scc> sccs = g.getSccs();
        Collections.sort(sccs);
        for(int i = sccs.size() - 1; i >= 0; i --) {
            Scc scc = sccs.get(i);
            System.out.print(scc);
            if (i > 0) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    private void reportProgress(Graph g) {
        processedSoFar ++;
        int remainingToProcess = g.size() - processedSoFar;
        if (remainingToProcess % progressReportChunkSize == 0) {
            long elapsedMillis = System.currentTimeMillis() - t0;
            long remainingMillis = (long)(((double)elapsedMillis / processedSoFar) * remainingToProcess);
            double remainingHours =  (double)remainingMillis / 1000 / 3600;
            System.out.printf("remaining to process " + remainingToProcess + " nodes in estimated %2.2f hours, ETA: %s\n",
                    remainingHours, new SimpleDateFormat("yy/MM/dd hh:mm a").format(System.currentTimeMillis() + remainingMillis));
        }
    }
}
