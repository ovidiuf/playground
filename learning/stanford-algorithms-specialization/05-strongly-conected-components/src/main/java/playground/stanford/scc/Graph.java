package playground.stanford.scc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Graph {

    // classic adjacency list, each Vertex contains its outgoing edges.
    private final List<Vertex> adj;

    /**
     * File storage details: The file contains the edges of a directed graph. Vertices are labeled as positive integers
     * from 1 to 875714. Every row indicates an edge, the vertex label in first column is the tail and the vertex label
     * in second column is the head (recall the graph is directed, and the edges are directed from the first column
     * vertex to the second column vertex). So for example, the 11^{th} row looks like : "2 47646". This just means that
     * the vertex with label 2 has an outgoing edge to the vertex with label 47646.
     */
    public Graph(File f) throws Exception {
        this.adj = new ArrayList<>();
        load(f);
    }

    @SuppressWarnings("unused")
    public int size() {
        return adj.size();
    }

    public Vertex getVertex(int i) {
        return adj.get(i);
    }

    /**
     * @param head 0-based head vertex returned edges are incident to.
     * @return an array of tail vertices that correspond to the edges incident to the given head vertex. The result
     *         may be empty but never null.
     */
    public List<Integer> getEdgesIncidentTo(int head) {
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < adj.size(); i ++) {
            for(Integer h: adj.get(i).getHeadsForOutgoingEdges()) {
                if (h == head) {
                    result.add(i);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * @param tail 0-based tail vertex returned edges are incident from.
     * @return an array of head vertices that correspond to the edges incident from the given tail vertex. The result
     *         may be empty but never null.
     */
    public List<Integer> getEdgesIncidentFrom(int tail) {
        return new ArrayList<>(adj.get(tail).getHeadsForOutgoingEdges());
    }

    public void reorderByFinishingTime() {
        //
        // we do it in linear time by allocating a double amount of space for both vertices and edges
        //
       Vertex[] adj2 = new Vertex[adj.size()];
       for(Vertex oldVertex : adj) {
           Vertex newVertex = new Vertex();
           adj2[oldVertex.finishingTime] = newVertex;
           // rewrite the adjacency list of the newly created vertex using the finishing time of the old adjacencies
           for(int oldVertexAdjIndex : oldVertex.adjacencyList) {
               newVertex.addHead(getVertex(oldVertexAdjIndex).finishingTime);
           }
       }
       adj.clear();
       Collections.addAll(adj, adj2);
        System.out.println("reordering by finishing time completed");
    }

    public List<Scc> getSccs() {
        Map<Integer, Scc> sccs = new HashMap<>();
        for(Vertex v: adj) {
            Integer leader = v.getLeader();
            if (leader == null) {
                throw new IllegalStateException("vertex " + v + " has not been annotated with a leader");
            }
            Scc scc = sccs.get(leader);
            if (scc == null) {
                scc = new Scc(leader);
                sccs.put(leader, scc);
            }
            scc.incrementCount();
        }
        List<Scc> result = new ArrayList<>(sccs.values());
        System.out.println("computed SCCs");
        return result;
    }

    public void dump() {
        for(int i = 0; i < size(); i ++) {
            System.out.println(i + ": " + getVertex(i));
        }
    }

    private void load(File f) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader(f));
        String line;
        long lineNumber = 1;
        int currentVertex = -1;
        while((line = r.readLine()) != null) {
            int i = line.indexOf(' ');
            if (i == -1) {
                throw new RuntimeException("line " + lineNumber + " does not contain space");
            }
            String s = line.substring(0, i).trim();
            int tail = Integer.parseInt(s) - 1; // zero-based vertex labeling
            s = line.substring(i + 1).trim();
            int head = Integer.parseInt(s) - 1; // zero-based vertex labeling
            if (currentVertex != tail) {
                // new vertex
                if (tail < currentVertex) {
                    throw new IllegalArgumentException("regression at line " + lineNumber + ", current vertex " + currentVertex + " tail " + tail);
                }
                if (tail - currentVertex > 1) {
                    // there are vertices with no emergent arcs (sink vertices), add empty adjacency lists
                    for(int j = 0; j < tail - currentVertex - 1; j ++) {
                        adj.add(new Vertex());
                    }
                }
                currentVertex = tail;
                adj.add(new Vertex());
                assert adj.size() == currentVertex + 1;
            }
            adj.get(currentVertex).addHead(head);
            lineNumber ++;
        }
        r.close();
        System.out.println("graph loaded in memory");
    }

    public static class Vertex {

        // contains a list of heads corresponding to the outgoing edges
        private final List<Integer> adjacencyList;
        private boolean explored;
        private int finishingTime;
        // null if the leader has not been set up
        private Integer leader;

        public Vertex() {
            this.adjacencyList = new ArrayList<>();
            this.explored = false;
            this.finishingTime = -1;
            this.leader = null;
        }

        /**
         * @return a list of heads corresponding to the outgoing edges.
         */
        public List<Integer> getHeadsForOutgoingEdges() {
            return adjacencyList;
        }

        /**
         * Adds to the adjacency list the head corresponding to an outgoing edge.
         */
        public void addHead(int h) {
            adjacencyList.add(h);
        }

        public void setExplored() {
            this.explored = true;
        }

        public boolean isExplored() {
            return explored;
        }

        public void setFinishingTime(int t) {
            this.finishingTime = t;
        }

        public void setLeader(int l) {
            this.leader = l;
        }

        /**
         * @return null if the leader has not been set up.
         */
        public Integer getLeader() {
            return leader;
        }

        @Override
        public String toString() {
            String s = explored ? "EXPLORED" : "UNEXPLORED";
            if (finishingTime >= 0) {
                s += ", t=" + finishingTime;
            }
            if (leader != null) {
                s += ", leader=" + leader;
            }
            s += ", [";
            for(Iterator<Integer> i = adjacencyList.iterator(); i.hasNext(); ) {
                //noinspection StringConcatenationInLoop
                s += i.next();
                if (i.hasNext()) {
                    s += ", ";
                }
            }
            s += "]";
            return s;
        }
    }
}
