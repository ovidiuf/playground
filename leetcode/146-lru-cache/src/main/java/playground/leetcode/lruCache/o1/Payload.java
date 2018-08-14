package playground.leetcode.lruCache.o1;

public class Payload {

    int key;
    int value;
    Payload prev;
    Payload next;

    public Payload(int key, int value) {

        this.key = key;
        this.value = value;
    }

    public int getKey() {

        return key;
    }

    public int getValue() {

        return value;
    }

    public void setValue(int value) {

        this.value = value;
    }

    @Override
    public String toString() {

        return key + " - " + value;
    }
}
