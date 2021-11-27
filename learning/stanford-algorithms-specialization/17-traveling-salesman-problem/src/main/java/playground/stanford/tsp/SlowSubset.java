package playground.stanford.tsp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The internal collection of elements is maintained in sorted order
 */
@SuppressWarnings({"unused", "PatternVariableCanBeUsed"})
public class SlowSubset implements Subset {
    // zero-based index of the subset, this is what we use in A[S,j]
    public int id = -1;
    // 1-based components - vertex IDs. Always maintained in sorted order.
    public int[] elements;
    private final int n;

    /**
     * Non-id instance constructor.
     * The elements *must* be provided in sorted order. The instance will reuse the storage, will NOT make an
     * internal copy.
     */
    public SlowSubset(int[] elements, int n) {
        this.n = n;
        this.elements = elements;
    }

    public SlowSubset(int id, int[] elements, int n) {
        this(elements, n);
        this.id = id;
    }

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int size() {
        return this.elements.length;
    }

    @Override
    public SlowSubset add(int element) {
        assert 1 <= element;
        assert element <= n;
        int[] newElements = new int[elements.length + 1];
        boolean copy = false;
        for(int i = 0; i < newElements.length; i ++) {
            if (copy) {
                newElements[i] = elements[i - 1];
            }
            else if (i == elements.length) {
                break;
            }
            else if (elements[i] < element) {
                newElements[i] = elements[i];
            }
            else if (elements[i] == element) {
                // element already exists, return null
                return null;

            }
            else {
                // element < crt, insert it
                newElements[i] = element;
                copy = true;
            }
        }
        if (!copy) {
            newElements[elements.length] = element;
        }
        return new SlowSubset(newElements, n);
    }

    @Override
    public SlowSubset remove(int element) {
        for(int i = 0; i < elements.length; i ++) {
            if (elements[i] == element) {
                int[] ne = new int[elements.length - 1];
                System.arraycopy(elements, 0, ne, 0, i);
                System.arraycopy(elements, i + 1, ne, i, elements.length - i - 1);
                return new SlowSubset(ne, n);
            }
        }
        return this;
    }

    @Override
    public Iterator<Integer> elements() {
        List<Integer> l = new ArrayList<>();
        for(int i: elements) {
            l.add(i);
        }
        return l.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SlowSubset)) {
            return false;
        }
        SlowSubset that = (SlowSubset)o;
        if (elements.length != that.elements.length) {
            return false;
        }
        for(int i = 0; i < elements.length; i ++) {
            if (elements[i] != that.elements[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int r = 17;
        for(int e: elements) {
            r += 7 * e;
        }
        return r;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(id).append("]{");
        for(int i = 0; i < elements.length; i ++) {
            sb.append(elements[i]);
            if (i < elements.length - 1) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
