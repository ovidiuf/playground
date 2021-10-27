package playground.stanford.huffman;

/**
 * A node in the binary tree. It could be an internal node (no symbol) or a leaf that corresponds to a symbol
 */
public class Node {

    Symbol s; // if s is not null, then leftChild and rightChild must be null, and vice-versa
    Node leftChild;
    Node rightChild;

    /**
     * Constructor for symbol-less internal node.
     */
    public Node(Node leftChild, Node rightChild) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    /**
     * Constructor for a leaf, that corresponds to a symbol of the alphabet.
     */
    public Node(Symbol s) {
        this.s = s;
    }

    /**
     * @param ab a synthetic symbol resulted from the combination of two other symbols
     * @return true if the symbol was found and split, false otherwise
     */
    public boolean splitLeaf(Symbol ab) {
        // ensure it is a synthetic symbol
        assert ab.a != null;
        assert ab.b != null;
        assert ab.id == null;
        if (ab.equals(s)) {
            // symbol found, do split
            assert leftChild == null;
            assert rightChild == null;
            leftChild = new Node(ab.a);
            rightChild = new Node(ab.b);
            this.s = null; // turn this node into a final internal node
            return true;
        }
        if (s != null) {
            // we're another symbol's leaf, could not split
            return false;
        }
        if (leftChild.splitLeaf(ab)) {
            return true;
        }
        return rightChild.splitLeaf(ab);
    }
}
