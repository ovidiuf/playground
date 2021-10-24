package playground.stanford.clustering;

@SuppressWarnings({"AssertionCanBeIf", "AssertWithSideEffects", "DuplicatedCode"})
public class Main {

    public static void main(String[] args) throws Exception {
        int k = 4;
        String fileName = "./data/clustering1.txt";
        //String fileName = "./data/test1.txt";
        UnionFind u = new UnionFind(new Graph(fileName));
        //testUnionFind(u);
        MaximizeKClusteringSpacing m = new MaximizeKClusteringSpacing();
        int maximumSpacing = m.run(k, u);
        System.out.println("maximum spacing of " + k + " clusters: " + maximumSpacing);
    }

    private static void testUnionFind(UnionFind u) {
        assert 5 == u.getClusterCount();
        assert 0 == u.find(0); assert 1 == u.clusterSize(0);
        assert 1 == u.find(1); assert 1 == u.clusterSize(1);
        assert 2 == u.find(2); assert 1 == u.clusterSize(2);
        assert 3 == u.find(3); assert 1 == u.clusterSize(3);
        assert 4 == u.find(4); assert 1 == u.clusterSize(4);

        assert 3 == u.union(2, 3);
        assert 4 == u.getClusterCount();
        assert 0 == u.find(0); assert 1 == u.clusterSize(0);
        assert 1 == u.find(1); assert 1 == u.clusterSize(1);
        assert 3 == u.find(2); assert -1 == u.clusterSize(2);
        assert 3 == u.find(3); assert 2 == u.clusterSize(3);
        assert 4 == u.find(4); assert 1 == u.clusterSize(4);

        try {
            u.union(2, 4);
            throw new RuntimeException();
        }
        catch(IllegalArgumentException e) {
            assert "cluster 2 does not exist".equals(e.getMessage());
        }

        assert 3 == u.union(1, 3);
        assert 3 == u.getClusterCount();
        assert 0 == u.find(0); assert 1 == u.clusterSize(0);
        assert 3 == u.find(1); assert -1 == u.clusterSize(1);
        assert 3 == u.find(2); assert -1 == u.clusterSize(2);
        assert 3 == u.find(3); assert 3 == u.clusterSize(3);
        assert 4 == u.find(4); assert 1 == u.clusterSize(4);

        assert 0 == u.union(4, 0);
        assert 2 == u.getClusterCount();
        assert 0 == u.find(0); assert 2 == u.clusterSize(0);
        assert 3 == u.find(1); assert -1 == u.clusterSize(1);
        assert 3 == u.find(2); assert -1 == u.clusterSize(2);
        assert 3 == u.find(3); assert 3 == u.clusterSize(3);
        assert 0 == u.find(4); assert -1 == u.clusterSize(4);

        assert 3 == u.union(0, 3);
        assert 1 == u.getClusterCount();
        assert 3 == u.find(0); assert -1 == u.clusterSize(0);
        assert 3 == u.find(1); assert -1 == u.clusterSize(1);
        assert 3 == u.find(2); assert -1 == u.clusterSize(2);
        assert 3 == u.find(3); assert 5 == u.clusterSize(3);
        assert 3 == u.find(4); assert -1 == u.clusterSize(4);
    }
}
