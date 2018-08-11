package playground.dsa.queue;

/**
 * A queue of ints that uses just n-1 storage elements for queue element storage, and
 * it can tell when it is full without maintaining an additional boolean variable.
 */
public class QueueNMinusOne implements Queue {

    private int[] storage;

    // this is the index of the queue head element
    private int head;

    // this is the index of the element where the tail *would* go
    private int tail;

    public QueueNMinusOne(int capacity) {

        this.storage = new int[capacity];
        this.head = 0;
        this.tail = 0;
    }

    @Override
    public void enqueue(int i) throws QueueOverflowException {

        //
        // verify whether by enqueueing this element the tail will overlap with the head
        // if they do, it means the queue is full
        //

        if ((tail + 1) % storage.length == head) {

            throw new QueueOverflowException();
        }

        storage[tail] = i;

        tail = (tail + 1) % storage.length;
    }

    @Override
    public int dequeue() throws QueueUnderflowException {

        if (head == tail) {

            throw new QueueUnderflowException();
        }

        int result = storage[head];

        head = (head + 1) % storage.length;

        return result;
    }

    @Override
    public boolean isEmpty() {

        return head == tail;
    }

    @Override
    public boolean isFull() {

        return (tail + 1) % storage.length == head;
    }

    @Override
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
        return sb.toString();
    }
}
