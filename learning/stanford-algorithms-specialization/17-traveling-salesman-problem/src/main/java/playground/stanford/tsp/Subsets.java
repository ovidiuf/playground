package playground.stanford.tsp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class Subsets {

    public int n;
    private int indexCounter = 0; // this is also at any moment the subset count
    // each map contains subsets of the same size, and the index of the map in the "subset" array is the size of
    // the subset - 1. The only subset with size 1 is on position 0.
    private final Map<Subset,Subset>[] subsets;
    private final List<Subset> subsetList;

    public Subsets(int n) {
        this.n = n;
        //noinspection unchecked
        this.subsets = new HashMap[n];
        this.subsetList = new ArrayList<>();
        Subset s = new FastSubset(n, 1);
        s.setId(indexCounter ++);
        this.subsets[0] = new HashMap<>();
        this.subsets[0].put(s, s);
        subsetList.add(s);
        initSubsetsLargerThanOne();
        // ensure that indices are contiguous
        int subsetCount = 0;
        for (Map<Subset,Subset> subsetsSameLength : subsets) {
            subsetCount += subsetsSameLength.size();
        }
        assert indexCounter == subsetCount;
    }

    /**
     * @param subsetIndex zero-based subset index
     */
    public Subset get(int subsetIndex) {
        return subsetList.get(subsetIndex);
    }

    /**
     * @return the subset count
     */
    public int size() {
        return indexCounter;
    }

    public Map<Subset, Subset> getSubsetsOfSize(int m) {
        return this.subsets[m - 1];
    }

    /**
     * @exception IllegalArgumentException on invalid subset.
     */
    public int getIndexOfSubsetWithoutSpecifiedElement(Subset s, int e) {
        Subset s2 = s.remove(e);
        Map<Subset, Subset> subsetsOfSameSize = subsets[s2.size() - 1];
        Subset s3 = subsetsOfSameSize.get(s2);
        if (s3 != null) {
            return s3.id();
        }
       throw new IllegalArgumentException("subset derived from " + s + " without " + e + " not found");
    }

    public int getIndexOf(Subset s) {
        Map<Subset, Subset> subsetsOfSameSize = subsets[s.size() - 1];
        Subset ss = subsetsOfSameSize.get(s);
        if (ss != null) {
            return ss.id();
        }
        throw new IllegalArgumentException("subset " + s + " not found");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("n: ").append(n).append(", subsets: ").append(indexCounter).append("\n");
        for(int i = 0; i < n; i ++) {
            sb.append("subsets with size ").append(i + 1).append("(").append(subsets[i].size()).append("):\n");
            for(Subset s: subsets[i].keySet()) {
                sb.append("   ").append(s).append("\n");
            }
        }
        return sb.toString();
    }

    private void initSubsetsLargerThanOne() {
        for (int size = 2; size <= n; size ++) {
            initSubsetsOfSize(size);
        }
    }

    private void initSubsetsOfSize(int s) {
        assert 2 <= s;
        assert s <= n;
        Map<Subset, Subset> sameSizeSubsetMap = new HashMap<>();
        // we recursively build subsets using subsets of immediately smaller size
        long possibilities = (long)subsets[s - 2].size() * (n - 1);
        this.t0 = System.currentTimeMillis();
        System.out.println("initializing subsets of size " + s + ", must consider " + possibilities + " possibilities ...");
        long counter = 0;
        for(Subset ss: subsets[s - 2].keySet()) {
            for(int i = 2; i <= n; i ++) {
                counter ++;
                report(possibilities, counter);
                Subset newSs = ss.add(i);
                if (newSs == null) {
                    // i already exists in subset
                    continue;
                }
                if (!sameSizeSubsetMap.containsKey(newSs)) {
                    newSs.setId(indexCounter ++);
                    sameSizeSubsetMap.put(newSs, newSs);
                    subsetList.add(newSs);
                }
            }
        }
        subsets[s - 1] = sameSizeSubsetMap;
        System.out.println("subsets of size " + s + " completed at " + DF.format(System.currentTimeMillis()));
    }

    private long t0;
    private static final SimpleDateFormat DF = new SimpleDateFormat("MM/dd hh:mm:ss a");

    private void report(long possibilities, long counter) {
        if (counter % 1000000 == 0) {
            long t1 = System.currentTimeMillis();
            double remainingMs = (double)(t1 - t0)/counter * (possibilities - counter);
            System.out.println(counter + ", estimated completion: " + DF.format(t1 + (long)remainingMs));
        }
    }
}
