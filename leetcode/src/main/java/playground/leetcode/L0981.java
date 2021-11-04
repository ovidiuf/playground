package playground.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class L0981 {
    public void run() {
        TimeMap obj = new TimeMap();
        obj.set("foo","bar",1);
        String param_2 = obj.get("foo",1); System.out.println(param_2);
        param_2 = obj.get("foo",3); System.out.println(param_2);
        obj.set("foo","bar2",4);
        param_2 = obj.get("foo",4); System.out.println(param_2);
        param_2 = obj.get("foo",5); System.out.println(param_2);

    }

    class TimeMap {
        private List<Key>[] a;
        public TimeMap() {
            this.a = new List[500001]; // TODO check if 9997 is prime
        }

        public void set(String key, String value, int timestamp) {
            int bi = Math.abs(Objects.hash(key) % a.length);
            List<Key> l = a[bi];
            if (l == null) {
                l = new ArrayList<Key>();
                a[bi] = l;
            }
            for(Key k: l) {
                if(k.k.equals(key)) {
                    k.add(value, timestamp);
                    return;
                }
            }
            l.add(new Key(key, value, timestamp));
        }

        public String get(String key, int timestamp) {
            int bi = Math.abs(Objects.hash(key) % a.length);
            List<Key> l = a[bi];
            if (l == null) {
                return "";
            }
            for(Key k: l) {
                if (k.k.equals(key)) {
                    return k.get(timestamp);
                }
            }
            return "";
        }
    }

    class Key {
        public String k;
        public List<TimedValue> vs;
        public Key(String k, String v, int initTs) {
            this.k = k;
            this.vs = new ArrayList<>();
            this.vs.add(new TimedValue(v, initTs));
        }
        // TODO: if ts <= existing timestamp, throw exception, for the time being
        // we trust is correct
        public void add(String value, int ts) {
            this.vs.add(new TimedValue(value, ts));
        }
        public String get(int ts) {
            TimedValue c = binarySearch(0, vs.size(), ts);
            if (c == null) {
                return "";
            }
            return c.v;
        }

        /**
         * @param j first index outside the range
         */
        private TimedValue binarySearch(int i, int j, int ts) {
            int m = (i + j)/2;
            if (m >= vs.size()) {
                return vs.get(vs.size() - 1);
            }
            if (vs.get(m).ts == ts) {
                return vs.get(m);
            }
            if (m == i) {
                // base case
                TimedValue tv = vs.get(m);
                if (tv.ts < ts)  {
                    return tv;
                }
                if (m == 0) {
                    return null;
                }
                return vs.get(m - 1);
            }
            if (vs.get(m).ts > ts) {
                return binarySearch(i, m, ts);
            }
            return binarySearch(m + 1, j, ts);
        }
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    class TimedValue {
        public String v;
        public int ts;
        public TimedValue(String v, int ts) {
            this.v = v;
            this.ts = ts;
        }
    }
}
