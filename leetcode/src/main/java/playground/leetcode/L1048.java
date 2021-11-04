package playground.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class L1048 {
    public void run() {
        //String[] words = {"a","b","ba","bca","bda","bdca"};
        //String[] words = {"xbc","pcxbcf","xb","cxbc","pcxbc"};
        String[] words = {"abcd","dbqca"};
        int l = new Solution().longestStrChain(words);
        System.out.println(l);
    }

    class Solution {
        public int longestStrChain(String[] words) {
            // build the tree
            Map<String, Node> nodes = new HashMap<>();
            for(String s: words) {
                Node n = new Node(s);
                nodes.put(s, n);
            }
            for(String i: words) {
                Node n = nodes.get(i);
                for(String j: words) {
                    if (n.isPredecessorOf(j)) {
                        n.addSuccessor(nodes.get(j));
                    }
                }
            }
            int maxDepth = Integer.MIN_VALUE;
            for(Node n: nodes.values()) {
                int depth = n.maxDepth();
                if (depth > maxDepth) {
                    maxDepth = depth;
                }
            }
            return maxDepth + 1;
        }
    }

    class Node {
        public String word;
        public List<Node> successors;
        public Node(String word) {
            this.word = word;
            this.successors = new ArrayList<>();
        }
        @Override
        public String toString() {
            String s = word + " → {";
            for(int i = 0; i < successors.size(); i ++) {
                s += successors.get(i).word;
                if (i < successors.size() - 1) {
                    s += ", ";
                }
            }
            s += "}";
            return s;
        }
        public boolean isPredecessorOf(String that) {
            if (word.length() + 1 != that.length()) {
                return false;
            }
            boolean mustMatch = false;
            int i = 0;
            int j = 0;
            while(i < word.length()) {
                if (word.charAt(i) == that.charAt(j)) {
                    i ++;
                    j ++;
                    continue;
                }
                else if (mustMatch) {
                    return false;
                }
                else {
                    // i contains the index to the first different character
                    mustMatch = true;
                    j ++;
                }
            }
            return true;
        }
        public void addSuccessor(Node n) {
            successors.add(n);
        }
        public int maxDepth() {
            return maxDepth(0);
        }
        private int maxDepth(int crt) {
            if (successors.isEmpty()) {
                return crt;
            }
            int max = Integer.MIN_VALUE;
            for(Node s: successors) {
                int d = s.maxDepth(crt + 1);
                if (d > max) {
                    max = d;
                }
            }
            return max;
        }
    }
}
