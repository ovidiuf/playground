package playground.dsa.queue;

public interface Queue {

    public void enqueue(int i) throws QueueOverflowException;

    public int dequeue() throws QueueUnderflowException;

    public boolean isEmpty();

    public boolean isFull();

    public String dump();
}
