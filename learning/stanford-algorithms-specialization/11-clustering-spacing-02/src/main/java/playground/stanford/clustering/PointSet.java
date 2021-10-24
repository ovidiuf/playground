package playground.stanford.clustering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A composite data structure that maintains all the points in the set, a heap used to compute the minimum distance
 * at a certain moment, and a union-find to maintain the point clusters.
 */
@SuppressWarnings({"unused", "DuplicatedCode"})
public class PointSet {

    public int size; // the point set size - the number of points
    public final List<Point> points; // the point array, the point ID is also the index in this array
    public long distanceCount = 0L;
    public long timestamp = 0L;
    public UnionFind unionFind;
    public long[] distanceDistribution;
    public List<HammingDistance> zeroDistances;
    public List<HammingDistance> oneDistances;
    public List<HammingDistance> twoDistances;
    public List<HammingDistance> threeDistances;
    public List<HammingDistance> fourDistances;

    public PointSet(String fileName) throws Exception {
        this.points = new ArrayList<>();
        this.distanceDistribution = new long[25]; // we assume that the distances are between 0 and 24
        this.zeroDistances = new ArrayList<>();
        this.oneDistances = new ArrayList<>();
        this.twoDistances = new ArrayList<>();
        this.threeDistances = new ArrayList<>();
        this.fourDistances = new ArrayList<>();
        load(fileName);
    }

    @Override
    public String toString() {
        return "points=" + size;
    }

    /**
     * @param i 0-based vertex label
     */
    public Point getPoint(int i) {
        return points.get(i);
    }

    public void mergeClusters() {
        unionFind = new UnionFind(this);
        System.out.println("merging 0-distant points");
        for(HammingDistance d: zeroDistances) {
            Point p1 = d.p1;
            Point p2 = d.p2;
            int p1Leader = unionFind.find(p1.id);
            int p2Leader = unionFind.find(p2.id);
            unionFind.union(p1Leader, p2Leader);
        }
        System.out.println(unionFind.getClusterCount() + " clusters after merging 0-distant points");
        System.out.println("merging 1-distant points");
        for(HammingDistance d: oneDistances) {
            Point p1 = d.p1;
            Point p2 = d.p2;
            int p1Leader = unionFind.find(p1.id);
            int p2Leader = unionFind.find(p2.id);
            unionFind.union(p1Leader, p2Leader);
        }
        System.out.println(unionFind.getClusterCount() + " clusters after merging 1-distant points");
        System.out.println("merging 2-distant points");
        for(HammingDistance d: twoDistances) {
            Point p1 = d.p1;
            Point p2 = d.p2;
            int p1Leader = unionFind.find(p1.id);
            int p2Leader = unionFind.find(p2.id);
            unionFind.union(p1Leader, p2Leader);
        }
        System.out.println(unionFind.getClusterCount() + " clusters after merging 2-distant points");
        System.out.println("merging 3-distant points");
        for(HammingDistance d: threeDistances) {
            Point p1 = d.p1;
            Point p2 = d.p2;
            int p1Leader = unionFind.find(p1.id);
            int p2Leader = unionFind.find(p2.id);
            unionFind.union(p1Leader, p2Leader);
        }
        System.out.println(unionFind.getClusterCount() + " clusters after merging 3-distant points");
    }

    private void load(String fileName) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            line = br.readLine().trim();
            String[] tok = line.split(" ");
            this.size = Integer.parseInt(tok[0]);
            int bitCount = Integer.parseInt(tok[1]);
            timestamp = System.currentTimeMillis();
            for (int i = 0; i < size; i++) {
                Point p = new Point(i, bitCount, br.readLine().trim());
                points.add(p);
                // for each point, compute the distance for the points seen so far and place it into a MinHeap
                for(int j = 0; j < i; j ++) {
                    HammingDistance d = new HammingDistance(p, points.get(j));
                    processDistance(d);
                }
            }
        }
    }

    private void processDistance(HammingDistance d) {
        assert 0 <= d.distance && d.distance < 25;
        distanceDistribution[d.distance] += 1;
        distanceCount ++;
        report();
        if (d.distance == 0) {
            zeroDistances.add(d);
        }
        else if (d.distance == 1) {
            oneDistances.add(d);
        }
        else if (d.distance == 2) {
            twoDistances.add(d);
        }
        else if (d.distance == 3) {
            threeDistances.add(d);
        }
        else if (d.distance == 4) {
            fourDistances.add(d);
        }
    }

    private void report() {
        if (distanceCount % 10000000 == 0) {
            long t = System.currentTimeMillis();
            long deltaT = t - timestamp;
            timestamp = t;
            System.out.println(distanceCount /10000000 + "  10^7 in " + deltaT + " ms");
        }
    }
}
