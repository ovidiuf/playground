package playground.stanford.huffman;

public class Main {

    public static void main(String[] args) throws Exception {
        //String fileName = "./data/test1.txt";
        String fileName = "./data/huffman.txt";
        Alphabet a = new Alphabet(fileName);
        Node tree = new Huffman().computeTree(a);
        computeMinAndMaxLeafDepth(tree);
        System.out.println("max depth: " + maxLeafDepth + ", min depth: " + minLeafDepth);
    }

    private static int minLeafDepth;
    private static int maxLeafDepth;

    private static void computeMinAndMaxLeafDepth(Node n) {
        minLeafDepth = Integer.MAX_VALUE;
        maxLeafDepth = Integer.MIN_VALUE;
        walk(n, 0);
    }

    private static void walk(Node n, int depth) {
        if (n.s != null) {
            // we're a leaf
            assert n.leftChild == null;
            assert n.rightChild == null;
            if (depth < minLeafDepth) {
                minLeafDepth = depth;
            }
            if (depth > maxLeafDepth) {
                maxLeafDepth = depth;
            }
            return;
        }
        // we're an internal node
        assert n.leftChild != null;
        assert n.rightChild != null;
        walk(n.leftChild, depth + 1);
        walk(n.rightChild, depth + 1);
    }
}
