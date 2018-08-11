package playground.dsa.queueUsingTwoStacks;

import playground.dsa.stack.Stack;
import playground.dsa.stack.StackOverflowException;
import playground.dsa.stack.StackUnderflowException;

/**
 * A queue of ints that uses all n storage elements for queue element storage.
 */
public class QueueUsingTwoStacks implements Queue {

    private Stack active;
    private Stack aux;
    private Order order;

    public QueueUsingTwoStacks(int capacity) {

        this.active = new Stack(capacity);
        this.aux = new Stack(capacity);
        this.order = Order.LIFO;
    }

    @Override
    public void enqueue(int i) throws QueueOverflowException {

        if (Order.FIFO.equals(order)) {

            flip();
        }

        try {

            active.push(i);
        }
        catch(StackOverflowException e) {

            throw new QueueOverflowException();
        }

    }

    @Override
    public int dequeue() throws QueueUnderflowException {

        if (Order.LIFO.equals(order)) {

            flip();
        }

        try {

            return active.pop();
        }
        catch(StackUnderflowException e) {

            throw new QueueUnderflowException();
        }
    }

    @Override
    public boolean isEmpty() {

        return active.isEmpty();
    }

    @Override
    public boolean isFull() {

        return active.isFull();
    }

    @Override
    public String dump() {

        return "NOT IMPLEMENTED";
    }

    private void flip() {

        try {

            while (!active.isEmpty()) {

                aux.push(active.pop());
            }

            if (Order.FIFO.equals(order)) {

                order = Order.LIFO;
            }
            else {

                order = Order.FIFO;
            }

            Stack tmp = active;
            active = aux;
            aux = tmp;
        }
        catch (Exception e) {

            throw new IllegalStateException(e);
        }
    }
}
