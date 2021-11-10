package playground.bt;

/**
 * A binary tree node.
 */
public class N {
    public int id; // unique 0-based id
    public N p; // parent, may be null for root
    public N l; // left child
    public N r; // right child
    public String k; // key

    public N(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("invalid id " + id);
        }
        this.id = id;
    }

    /**
     *
     * @return the descendant, if exists, or null. We assume unique IDs.
     */
    public N forId(int id) {
        if (this.id == id) {
            return this;
        }
        N result = null;
        if (l != null) {
            result = l.forId(id);
        }
        if (result != null) {
            return result;
        }
        if (r != null) {
            result = r.forId(id);
        }
        return result;
    }

    @Override
    public String toString() {
        String parent = "";
        if (p != null) {
            parent = "←" + p.id;
        }
        String children = "";
        if (l != null || r != null) {
            children = "(" + (l == null ? "-":l.id) + "," + (r == null ? "-":r.id) + ")";
        }
        String key = "";
        if (k != null) {
            key = "[" + k + "]";
        }
        return id + parent + children + key;
    }
}
