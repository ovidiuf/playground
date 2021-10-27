package playground.stanford.huffman;

public class Symbol implements Comparable<Symbol> {

    // 0-based integer for the original symbol, or null for a synthetic symbol
    String id;
    int weight;
    // the components of a synthetic symbol
    Symbol a, b;

    public Symbol(String id, int weight) {
        this.id = id;
        this.weight = weight;
    }

    /**
     * Synthetic symbol resulted from combining two other symbols. The weight is automatically combined.
     */
    public Symbol(Symbol a, Symbol b) {
        id = null;
        this.a = a;
        this.b = b;
        this.weight = a.weight + b.weight;
    }

    @Override
    public int compareTo(Symbol that) {
        return this.weight - that.weight;
    }

    @Override
    public String toString() {
        if (id != null) {
            String s = id;
            int i = Integer.parseInt(id);
            if (i < 6) {
                s = "" + (char)(i + 65);
            }
            return s + "[" + weight + "]";
        }
        else {
            return a.toString().replaceAll("\\[.*$", "") + b.toString().replaceAll("\\[.*$", "") + "[" + weight + "]";
        }
    }
}
