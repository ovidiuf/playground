package playground.leetcode.lruCache;

public class Main {

    public static void main(String[] args) {

        LRUCache c = new LRUCache(2);

        c.put(1, 1);
        c.put(2, 2);

        System.out.println(c.get(1));

        c.put(3, 3);

        System.out.println(c.get(2));

        c.put(4, 4);

        System.out.println(c.get(1));

        System.out.println(c.get(3));

        System.out.println(c.get(4));

    }
}
