package playground.dsa.deque;

/**
 * A deque of ints.
 */
public class Deque {

    private int[] storage;
    private int head;
    private int tail;
    private boolean full;

    public Deque(int capacity) {

        this.storage = new int[capacity];
        this.head = 0;
        this.tail = 0;
        this.full = false;
    }

    public void push(int i) throws DequeOverflowException {

        if (full) {

            throw new DequeOverflowException();
        }

        head = head - 1;

        if (head < 0) {

            head = storage.length - 1;
        }

        storage[head] = i;

        if (head == tail) {

            this.full = true;
        }
    }

    public int pop() throws DequeUnderflowException {

        if (head == tail && !full) {

            throw new DequeUnderflowException();
        }

        int result = storage[head];

        head = head + 1;

        if (head == storage.length) {

            head = 0;
        }

        full = false;

        return result;
    }

    public void inject(int i) throws DequeOverflowException {

        if (full) {

            throw new DequeOverflowException();
        }

        storage[tail] = i;

        tail = tail + 1;

        if (tail == storage.length) {

            tail = 0;
        }

        if (head == tail) {

            full = true;
        }
    }

    public int eject() throws DequeUnderflowException {

        if (head == tail && !full) {

            throw new DequeUnderflowException();
        }

        tail = tail - 1;

        if (tail < 0) {

            tail = storage.length - 1;
        }

        full = false;

        return storage[tail];
    }

    public boolean isEmpty() {

        return head == tail && !full;
    }

    public boolean isFull() {

        return full;
    }

    public String dump() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < storage.length; i++) {

            sb.append(storage[i]);
            if (i < storage.length - 1) {

                sb.append(" ");
            }
        }

        sb.append(", head: ").append(head);
        sb.append(", tail: ").append(tail);
        sb.append(", full: ").append(full);
        return sb.toString();

    }
}
