package playground.standford.graphs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * A graph represented as an adjacency list
 */
@SuppressWarnings("ManualArrayCopy")
public class AdjacencyListGraph implements Graph {

    private final LinkedList<Integer>[] adj;
    private int nodeCount; // we keep the node count separate to allow for contractions in place

    /**
     * Loads the graph from the specified file.
     */
    public AdjacencyListGraph(File f) {
        if (f == null) {
            throw new IllegalArgumentException("null file");
        }
        if (!f.isFile()) {
            throw new IllegalArgumentException("file " + f + " does not exist or cannot be read");
        }
        List<LinkedList<Integer>> tmpAdj = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] toks = line.split("[ *|\t]");
                Integer vertex = null;
                for(String tok: toks) {
                    // there is redundancy in the file, we explicitly store the vertex as the first element in the line,
                    // ensure it is correct
                    if (vertex == null) {
                        vertex = Integer.parseInt(tok);
                        if (tmpAdj.size() != vertex - 1) {
                            throw new IllegalStateException("we're expecting vertex " + tmpAdj.size() + " but we got " + vertex);
                        }
                        tmpAdj.add(new LinkedList<>());
                    }
                    else {
                        tmpAdj.get(tmpAdj.size() - 1).add(Integer.parseInt(tok) - 1);
                    }
                }
            }
        }
        catch(IOException e) {
            throw new IllegalStateException(e);
        }
        //noinspection unchecked
        adj = new LinkedList[tmpAdj.size()];
        for(int i = 0; i < adj.length; i ++) {
            adj[i] = tmpAdj.get(i);
        }
        nodeCount = adj.length;
    }

    @Override
    public void display() {
        for(int i = 0; i < nodeCount; i ++) {
            System.out.print(i + ": ");
            for(Integer v: adj[i]) {
                System.out.print(v + " ");
            }
            System.out.println();
        }
    }

    @Override
    public int randomizedKargerContraction() {
        // contract the graph until we reach two nodes
        while(nodeCount > 2) {
            Edge e = pickRandomEdge();
            contract(e);
        }
        int firstVertexEdgeCount = adj[0].size();
        int secondVertexEdgeCount = adj[1].size();
        // they should be equal
        if (firstVertexEdgeCount != secondVertexEdgeCount) {
            throw new IllegalStateException("first vertex edge count: " + firstVertexEdgeCount + ", " +
                    "second vertex edge count: " + secondVertexEdgeCount + ", they should be equal");
        }
        return firstVertexEdgeCount;
    }

    @Override
    public String toString() {
        return "AdjacencyListGraph[" + adj.length + "]";
    }

    private Edge pickRandomEdge() {
        Random r = new Random();
        // pick a random node
        int node = r.nextInt(nodeCount);
        // pick a random edge for that node
        LinkedList<Integer> edges = adj[node];
        int edgeCount = edges.size();
        int edgeIndex = r.nextInt(edgeCount);
        int index = 0;
        for (Integer edge : edges) {
            if (index == edgeIndex) {
                return new Edge(node, edge);
            }
            index++;
        }
        throw new IllegalStateException("shouldn't have gotten here");
    }

    /**
     * Contract in place and adjust the nodeCount.
     */
    private void contract(Edge e) {
        int smallestNode = e.smallest();
        int largestNode = e.largest();
        // the largest becomes the smallest, so scan the adjacency lists and replace the largest with the smallest;
        // also, decrement with 1 everything larger than the largest, because the largest disappears
        for(int i = 0; i < nodeCount; i ++) {
            LinkedList<Integer> oldList = adj[i];
            LinkedList<Integer> newList = new LinkedList<>();
            for(Integer v: oldList) {
                if (v > largestNode) {
                    newList.add(v - 1);
                }
                else if (v == largestNode) {
                    newList.add(smallestNode);
                }
                else {
                    newList.add(v);
                }
            }
            // swap the old list with the new list and let old list to be GCed
            adj[i] = newList;
        }
        // merge the adjacency list for smallest and largest
        adj[smallestNode] = mergeAdjacencyLists(adj[smallestNode], adj[largestNode], smallestNode);
        // compact everything above the largest
        for(int i = largestNode; i < nodeCount - 1; i ++) {
            adj[i] = adj[i + 1];
        }
        nodeCount--;
    }

    /**
     * Merge adjacency lists, eliminating the edge that contracts, and also self loops. Allocate a new list.
     */
    private LinkedList<Integer> mergeAdjacencyLists(LinkedList<Integer> a, LinkedList<Integer> b, int smallest) {
        LinkedList<Integer> newList = new LinkedList<>();
        for(Integer i: a) {
            if (i == smallest) {
                continue;
            }
            newList.add(i);
        }
        for(Integer i: b) {
            if (i == smallest) {
                continue;
            }
            newList.add(i);
        }
        return newList;
    }
}
