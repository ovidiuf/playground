package playground.bt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Trees {

    /**
     * Each line in the text file has the following structure:
     * <node_id> <left_child_id|- > <right_child_id|- > [payload]
     * where ids are 0-based integers. Each node must be specified once, if a duplicate is found, that's an error.
     * # designate comment lines
     * If a node is referred from a parent definition but not found anywhere else, it is considered a valid leaf.
     * id - - is also a valid leaf.
     */
    public static N load(String fileName) throws Exception {
        Map<Integer, N> tmp = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;
            while((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty() || line.charAt(0) == '#') {
                    continue;
                }
                String[] tok = line.split(" ");
                int nid = Integer.parseInt(tok[0]);
                N n = tmp.get(nid);
                // if a node is found, and it has no parent, it means it was a duplicate, which is an error
                if (n != null) {
                    if (n.p == null) {
                        throw new IllegalStateException("duplicate node " + nid + " declaration at line " + lineNumber);
                    }
                }
                else {
                    n = new N(nid);
                    tmp.put(nid, n);
                }
                // left child
                if (!tok[1].equals("-")) {
                    // has left child
                    int lid = Integer.parseInt(tok[1]);
                    N l = tmp.get(lid);
                    if (l != null) {
                        // already in the map, if it already has a parent, it was claimed as child already and this is
                        // an error
                        if (l.p != null) {
                            throw new IllegalStateException("node " + lid + " has two parents: " + nid + ", and " + l.p.id + " at line " + lineNumber);
                        }
                    }
                    else {
                        l = new N(lid);
                        tmp.put(lid, l);
                    }
                    // connect left children to parent
                    n.l = l;
                    l.p = n;
                }
                // right child
                if (!tok[2].equals("-")) {
                    // has right child
                    int rid = Integer.parseInt(tok[2]);
                    N r = tmp.get(rid);
                    if (r != null) {
                        // already in the map, if it already has a parent, it was claimed as child already and this is
                        // an error
                        if (r.p != null) {
                            throw new IllegalStateException("node " + rid + " has two parents: " + nid + ", and " + r.p.id + " at line " + lineNumber);
                        }
                    }
                    else {
                        r = new N(rid);
                        tmp.put(rid, r);
                    }
                    // connect right child to parent
                    n.r = r;
                    r.p = n;
                }
                // optionally payload
                if (tok.length > 3) {
                    n.k = tok[3];
                }
            }
        }
        // return the root
        for(N n: tmp.values()) {
            if (n.p == null) {
                return n;
            }
        }
        throw new IllegalStateException("found no root");
    }

    public static void preOrder(N r) {
        if (r == null) {
            return;
        }
        System.out.println(r);
        preOrder(r.l);
        preOrder(r.r);
    }

    public static void postOrder(N r) {
        if (r == null) {
            return;
        }
        postOrder(r.l);
        postOrder(r.r);
        System.out.println(r);
    }

    public static void inOrder(N r) {
        if (r == null) {
            return;
        }
        inOrder(r.l);
        System.out.println(r);
        inOrder(r.r);
    }

    public static R minLeafDepth(N r) {
        if (r.p != null) {
            throw new IllegalArgumentException("not a root");
        }
        return minLeafDepth(r, 0);
    }

    private static R minLeafDepth(N n, int depth) {
        if (n == null) {
            return new R(Integer.MAX_VALUE, -1);
        }
        if (n.l == null && n.r == null) {
            // I am a leaf
            return new R(depth, n.id);
        }
        R rl = minLeafDepth(n.l, depth + 1);
        R rr = minLeafDepth(n.r, depth + 1);
        if (rl.depth < rr.depth) {
            return rl;
        }
        return rr;
    }

    public static R maxLeafDepth(N r) {
        if (r.p != null) {
            throw new IllegalArgumentException("not a root");
        }
        return maxLeafDepth(r, 0);
    }

    private static R maxLeafDepth(N n, int depth) {
        if (n == null) {
            return new R(Integer.MIN_VALUE, -1);
        }
        if (n.l == null && n.r == null) {
            // I am a leaf
            return new R(depth, n.id);
        }
        R rl = maxLeafDepth(n.l, depth + 1);
        R rr = maxLeafDepth(n.r, depth + 1);
        if (rl.depth > rr.depth) {
            return rl;
        }
        return rr;
    }

    /**
     * Result: node id and depth
     */
    static class R {
        public int depth;
        public int nodeId;
        public R(int d, int id) {
            this.depth = d;
            this.nodeId = id;
        }
        @Override
        public String toString() {
            return "node " + nodeId + " with depth " + depth;
        }
    }
}
