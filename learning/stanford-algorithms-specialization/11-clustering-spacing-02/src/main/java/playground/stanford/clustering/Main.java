package playground.stanford.clustering;

public class Main {

    public static void main(String[] args) throws Exception {

        String fileName = "./data/clustering_big.txt";
        //String fileName = "./data/test1.txt";
        PointSet s = new PointSet(fileName);
        long t = 0L;
        for(int i = 0; i < s.distanceDistribution.length; i ++) {
            System.out.println("distance " + i + ": " + s.distanceDistribution[i]);
            t += s.distanceDistribution[i];
        }
        System.out.println("total distances: " + t);
        s.mergeClusters();
    }
}
