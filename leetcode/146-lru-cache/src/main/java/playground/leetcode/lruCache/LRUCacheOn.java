package playground.leetcode.lruCache;

import java.util.HashMap;
import java.util.Map;

/**
 * An implementation where put(key, value) is O(n)
 */
public class LRUCacheOn {
    // TODO what if it goes over?

    private int clock;

    // <key - [ts, key]>
    private Map<Integer, int[]> cache;
    private int capacity;

    public LRUCacheOn(int capacity) {

        this.capacity = capacity;
        cache = new HashMap<>();
        this.clock = 0;
    }

    public int get(int key) {

        int[] payload = cache.get(key);

        if (payload == null) {

            return -1;
        }

        payload[0] = clock++;

        return payload[1];
    }

    /**
     * This is O(n). We can do better.
     */
    public void put(int key, int value) {

        if (value < 0) {

            throw new IllegalArgumentException("negative value");
        }

        int[] payload = cache.get(key);

        if (payload != null) {

            payload[0] = clock ++;
            payload[1] = value;
        }
        else {

            if (cache.size() == capacity) {

                evictLRU();
            }

            payload = new int[] {clock ++, value};

            cache.put(key, payload);
        }
    }

    private void evictLRU() {

        int lruKey = -1;
        int minTs = Integer.MAX_VALUE;

        for(int k: cache.keySet()) {

            int[] payload = cache.get(k);

            if (payload[0] < minTs) {

                minTs = payload[0];
                lruKey = k;
            }
        }

        System.out.println("key " + lruKey + " evicted");
        cache.remove(lruKey);
    }

}
