package playground.stanford.huffman;

public class Huffman {
    /**
     * Return the binary tree corresponding to the Huffman code for the given alphabet.
     */
    public Node computeTree(Alphabet alphabet) {
        if (alphabet.size() == 2) {
            // base case
            return new Node(new Node(alphabet.removeSymbolWithLowestWeight()),
                    new Node(alphabet.removeSymbolWithLowestWeight()));
        }
        Symbol a = alphabet.removeSymbolWithLowestWeight();
        Symbol b = alphabet.removeSymbolWithLowestWeight();
        // new symbol with combined weight
        Symbol ab = new Symbol(a, b);
        alphabet.insert(ab);
        Node i = computeTree(alphabet);
        // split the leaf ab into a and b
        boolean didSplit = i.splitLeaf(ab);
        assert didSplit; // if we weren't able to split the synthetic symbol, something is wrong
        return i;
    }
}
