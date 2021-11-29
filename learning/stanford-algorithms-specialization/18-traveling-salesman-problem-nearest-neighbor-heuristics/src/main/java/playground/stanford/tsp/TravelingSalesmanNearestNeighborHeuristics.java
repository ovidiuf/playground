package playground.stanford.tsp;

public class TravelingSalesmanNearestNeighborHeuristics {

    /**
     * @return the heuristically calculated shortest tour length.
     */
    public double run(G g) {
        int n = g.size();
        int remaining = n;
        // mark first vertex as visited
        V s = g.vertex(1);
        s.visited = true;
        double tourLength = 0d;
        V crt = s;
        while(true) {
            // select the closest city that is not visited yet
            double minDistance = Long.MAX_VALUE;
            V nextClosestCity = null;
            for(int i = 1; i <= n; i ++) {
                V v = g.vertex(i);
                if (v.visited) {
                    continue;
                }
                double d = g.distance(crt, v);
                if (d < minDistance) {
                    minDistance = d;
                    nextClosestCity = v;
                }

            }
            if (nextClosestCity == null) {
                // all cities are visited, get out the loop
                break;
            }
            crt = nextClosestCity;
            crt.visited = true;
            remaining --;
            tourLength += minDistance;
            System.out.println(remaining + " cities remaining");

        }
        // close the loop, return to the start vertex
        tourLength += g.distance(crt, s);
        return tourLength;
    }
}
