package io.novaordis.playground;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {


    //private []Integer test =


    public static void main(String[] args) throws Exception {


        MyHashMapImpl map = new MyHashMapImpl();
//        map.put(5, 25);
//        System.out.println(map.get(5));
//        map.put(5, 36);
//        System.out.println(map.get(5));
//        map.put(8, 64);
//        System.out.println(map.get(8));

//        for(int i = 0; i < 100; i ++) {
//
//            System.out.println("key: " + i + " hash: " + map.getBucket(map.getHashCode(i)));
//        }

        map.put(1, 10);
        map.put(16, 20);

        System.out.println(map.get(1));

    }


    private static int logicalAnd(int i, int size) {

        return i & (size - 1);
    }

    static interface MyHashMap {

        public Object get(Object key);
        public void put (Object key, Object Val);
    }

    static class MyHashMapImpl implements MyHashMap {

        Object[] keys;
        Object[] values;
        int size = 16;

        public MyHashMapImpl() {
            keys = new Object[size];
            values = new Object[size];
        }

        public int getHashCode(int hash) {

            hash ^= (hash >>> 20) ^ (hash >>> 12);
            return hash ^ (hash >>> 7) ^ (hash >>> 4);

        }

        int getBucket(int hash) {
            return hash & (this.size - 1);
        }


        @Override
        public Object get(Object key) {
            int hash = getHashCode(key.hashCode());
            int bucket = getBucket(hash);
            return values[bucket];
        }

        @Override
        public void put(Object key, Object val) {
            int bucket = getBucket(getHashCode(key.hashCode()));
            keys[bucket] = key;
            values[bucket] = val;
        }
    }

}
