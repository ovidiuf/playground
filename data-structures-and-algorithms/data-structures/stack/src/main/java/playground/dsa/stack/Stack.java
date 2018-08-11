package playground.dsa.stack;

/**
 * A stack of ints.
 */
public class Stack {

    private int[] storage;
    private int top;

    public Stack(int capacity) {

        this.storage = new int[capacity];
        this.top = 0;
    }

    public void push(int i) throws StackOverflowException {

        if (top == storage.length) {

            throw new StackOverflowException();
        }

        storage[top ++] = i;
    }

    public int pop() throws StackUnderflowException {

        if (top == 0) {

            throw new StackUnderflowException();
        }

        return storage[-- top];
    }

    public boolean isEmpty() {

        return top == 0;
    }

    public boolean isFull() {

        return top == storage.length;
    }


}
