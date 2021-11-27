package playground.stanford.tsp;

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("PatternVariableCanBeUsed")
public class FastSubset implements Subset {

    private final int n;
    private int id;
    // each bit represents presence of the corresponding element in the set:
    // if bit 0 is set to 1, that means 1 is present in the set
    // if bit 1 is set to 1, that means 2 is present in the set
    // ...
    // if bit 24 is set to 1, that means 25 is present in the set
    private long set;
    private int size;

    public FastSubset(int n, int... elements) {
        this.n = n;
        this.id = -1;
        this.set = 0L;
        this.size = 0;
        if (elements == null) {
            throw new IllegalArgumentException("null elements");
        }
        for (int e : elements) {
            if (e <= 0 || e > n) {
                throw new IllegalArgumentException(e + " is not a valid element");
            }
            long mask = 1L << (e - 1);
            if ((set & mask) != 0) {
                // the element already exists in set
                throw new IllegalArgumentException(e + " is being added twice");
            }
            set |= mask;
            size ++;
        }
    }
    private FastSubset(int n, long set, int size) {
        this.n = n;
        this.id = -1;
        this.set = set;
        this.size = size;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Subset add(int e) {
        if (e <= 0 || e > n) {
            throw new IllegalArgumentException("invalid element: " + e);
        }
        long mask = 1L << (e - 1);
        if ((set & mask) != 0) {
            // element already present
            return null;
        }
        return new FastSubset(n, set | mask, size + 1);
    }

    /**
     * TODO what happens if all elements are removed.
     */
    @Override
    public Subset remove(int e) {
        long mask = 1L << (e - 1);
        if ((set & mask) == 0) {
            // element does not exist
            return this;
        }
        return new FastSubset(n, set ^ mask, size - 1);
    }

    @Override
    public Iterator<Integer> elements() {
        return new Iterator<>() {
            int next = 0;
            @Override
            public boolean hasNext() {
                // if the element indicated by next exists, leave it unchanged and return true
                if(((1L << next) & set) != 0) {
                    // valid element
                    return true;
                }
                // position on the next valid element
                while(next < n) {
                    next ++;
                    if(((1L << next) & set) != 0) {
                        // valid element
                        return true;
                    }
                }
                return false;
            }

            /**
             * Will always return the elements in sorted order, from small to large.
             */
            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                next ++;
                return next;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(id).append("]{");
        boolean first = true;
        for(int i = 0; i < n; i ++) {
            if((set & (1L << i)) != 0) {
                if (first) {
                    first = false;
                }
                else {
                    sb.append(",");
                }
                sb.append(i + 1);
            }
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FastSubset)) {
            return false;
        }
        FastSubset that = (FastSubset)o;
        return set == that.set;
    }

    @Override
    public int hashCode() {
        return (int)(17 * set);
    }
}
