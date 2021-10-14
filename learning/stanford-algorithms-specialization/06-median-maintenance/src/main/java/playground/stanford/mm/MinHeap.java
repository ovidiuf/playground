package playground.stanford.mm;

public class MinHeap {

    private static final int CAPACITY = 100000;
    private int[] a = new int[CAPACITY];
    private int next = 0; // position of the next inserted element

    public void insert(int x) {
        a[next] = x;
        bubbleUp(next);
        next ++;
    }

    public int removeMin() {
        int result = minimum();
        a[0] = a[next - 1];
        next --;
        bubbleDown(0);
        return result;
    }

    public int minimum() {
        if (next == 0) {
            throw new IllegalStateException("empty heap");
        }
        return a[0];
    }

    public int size() {
        return next;
    }

    public void dump() {
        for(int i = 0; i < next; i ++) {
            System.out.print(a[i]);
            if (i < next - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    /**
     * @param i the index of the element to bubble up if necessary.
     */
    private void bubbleUp(int i) {
        if (i == 0) {
            return;
        }
        int parentIndex = (i - 1)/2;
        if (a[parentIndex] > a[i]) {
            // swap
            int tmp = a[parentIndex];
            a[parentIndex] = a[i];
            a[i] = tmp;
            bubbleUp(parentIndex);
        }
    }

    /**
     * @param i the index of the element to bubble down if necessary.
     */
    private void bubbleDown(int i) {
        int leftChildIndex = 2 * i + 1;
        if (leftChildIndex >= next) {
            // we reached the bottom of the heap
            return;
        }
        int rightChildIndex = 2 * i + 2;
        if (rightChildIndex >= next) {
            // there's only the left child and then the bottom of the heap
            if (a[i] > a[leftChildIndex]) {
                // swap i with the smallest child
                int tmp = a[i];
                a[i] = a[leftChildIndex];
                a[leftChildIndex] = tmp;
                bubbleDown(leftChildIndex);
            }
        }
        else if (a[i] > a[leftChildIndex] || a[i] > a[rightChildIndex]) {
            // figure out the smallest child
            int smallestChildIndex = rightChildIndex;
            if (a[leftChildIndex] < a[rightChildIndex]) {
                smallestChildIndex = leftChildIndex;
            }
            // swap i with the smallest child
            int tmp = a[i];
            a[i] = a[smallestChildIndex];
            a[smallestChildIndex] = tmp;
            bubbleDown(smallestChildIndex);
        }
    }
}
