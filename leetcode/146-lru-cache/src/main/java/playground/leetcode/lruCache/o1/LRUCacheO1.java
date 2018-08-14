package playground.leetcode.lruCache.o1;

import java.util.HashMap;
import java.util.Map;

/**
 * An implementation where both put(key, value) and get(key) are O(1)
 */
public class LRUCacheO1 {

    private Map<Integer, Payload> cache;
    private LRUList list;
    private int capacity;

    public LRUCacheO1(int capacity) {

        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.list = new LRUList();
    }

    public int get(int key) {

        Payload p = cache.get(key);

        if (p == null) {

            return -1;
        }

        list.setMRU(p);
        return p.getValue();

    }

    public void put(int key, int value) {

        Payload p = cache.get(key);

        if (p != null) {

            p.setValue(value);
            list.setMRU(p);
        }
        else {

            //
            // new entry
            //

            if (cache.size() == capacity) {

                // delete the least recently used

                p = list.removeLRU();
                cache.remove(p.getKey());
            }

            p = new Payload(key, value);
            cache.put(key, p);
            list.setMRU(p);
        }
    }
}
