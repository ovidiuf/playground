package playground.leetcode.lruCache;

import playground.leetcode.lruCache.o1.LRUCacheO1;

public class Main {

    public static void main(String[] args) {

        lruCacheO1_2();
    }

    private static void lruCacheOn() {

        LRUCacheOn c = new LRUCacheOn(2);

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

    private static void lruCacheO1() {

        LRUCacheO1 c = new LRUCacheO1(2);

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

    private static void lruCacheO1_2() {

        LRUCacheO1 c = new LRUCacheO1(3);

        c.put(1, 1);
        c.put(2, 2);
        c.put(3, 3);
        c.put(4, 4);

        System.out.println(c.get(4));
        System.out.println(c.get(3));
        System.out.println(c.get(2));
        System.out.println(c.get(1));

        c.put(5, 5);

        System.out.println(c.get(1));
        System.out.println(c.get(2));
        System.out.println(c.get(3));
        System.out.println(c.get(4));
        System.out.println(c.get(5));
    }


}
