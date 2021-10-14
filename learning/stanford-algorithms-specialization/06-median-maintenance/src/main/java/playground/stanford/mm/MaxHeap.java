package playground.stanford.mm;

/**
 * We rely on the fact that the numbers are always positive, so we delegate the implementation to a Min Heap while
 * negating the numbers.
 */
public class MaxHeap {

    private MinHeap minHeap;

    public MaxHeap() {
        this.minHeap = new MinHeap();
    }

    public void insert(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("argument must be positive");
        }
        minHeap.insert(-1 * i);
    }

    public int removeMax() {
        return -1 * minHeap.removeMin();
    }

    public int maximum() {
        return -1 * minHeap.minimum();
    }

    public int size() {
        return minHeap.size();
    }

    public void dump() {
        minHeap.dump();
    }
}
