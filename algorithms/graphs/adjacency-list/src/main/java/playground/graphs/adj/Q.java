package playground.graphs.adj;

/**
 * A queue. We need it for BFS.
 */
public class Q<T> {
    private final Object[] a;
    int s; // size
    int t; // tail, the location to insert next element

    public Q(int capacity) {
        this.a = new Object[capacity];
        this.s = 0;
        this.t = 0;
    }

    public void enqueue(T e) {
        if (s == a.length) {
            // queue full
            throw new IllegalStateException("queue overflow");
        }
        a[t] = e;
        t = (t + 1) % a.length;
        s++;
    }

    /**
     * @return null on empty queue
     */
    public T dequeue() {
        if (s == 0) {
            // queue empty
            throw new IllegalStateException("queue underflow");
        }
        // compute head
        int h = (a.length + t - s) % a.length;
        @SuppressWarnings("unchecked")
        T e = (T)a[h];
        s--;
        return e;
    }

    public boolean isEmpty() {
        return s == 0;
    }

    public static void unitTests() {
        String s;
        Q<String> q = new Q<>(3);
        boolean b = q.isEmpty();
        assert b;
        try {
            q.dequeue();
            throw new RuntimeException("fault");
        }
        catch(IllegalStateException e) {
            assert "queue underflow".equals(e.getMessage());
        }

        q.enqueue("a");
        s = q.dequeue();
        assert "a".equals(s);

        q.enqueue("b");
        s = q.dequeue();
        assert "b".equals(s);

        q.enqueue("c");
        s = q.dequeue();
        assert "c".equals(s);

        q.enqueue("d");
        s = q.dequeue();
        assert "d".equals(s);

        q.enqueue("x");
        q.enqueue("y");
        q.enqueue("z");
        s = q.dequeue();
        assert "x".equals(s);
        s = q.dequeue();
        assert "y".equals(s);
        s = q.dequeue();
        assert "z".equals(s);
    }
}
