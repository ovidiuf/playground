package playground.dsa.queue;

/**
 * A queue of ints that uses all n storage elements for queue element storage.
 */
public class QueueN {

    private int[] storage;

    // this is the index of the queue head element
    private int head;

    // this is the index of the element where the tail *would* go
    private int tail;

    private boolean full;

    public QueueN(int capacity) {

        this.storage = new int[capacity];
        this.head = 0;
        this.tail = 0;
        this.full = false;
    }

    public void enqueue(int i) throws QueueOverflowException {

        //
        // check if the queue is full
        //

        if (full) {

            throw new QueueOverflowException();
        }

        storage[tail] = i;

        tail = (tail + 1) % storage.length;

        //
        // this is the only time we can determine the queue is full
        //

        if (head == tail) {

            this.full = true;
        }
    }

    public int dequeue() throws QueueUnderflowException {

        if (head == tail && !full) {

            throw new QueueUnderflowException();
        }

        int result = storage[head];

        head = (head + 1) % storage.length;

        full = false;

        return result;
    }

    public boolean isEmpty() {

        return head == tail;
    }

    public boolean isFull() {

        return full;
    }

    public String dump() {

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < storage.length; i ++) {

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
