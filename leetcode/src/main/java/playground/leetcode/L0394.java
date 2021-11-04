package playground.leetcode;

public class L0394 {
    public void run() {
        //String s = "3[a]2[bc]";
        //String s = "3[a2[c]]";
        //String s = "2[abc]3[cd]ef";
        String s = "abc3[cd]xyz";
        Solution sol = new Solution();
        System.out.println(sol.decodeString(s));
    }
}

@SuppressWarnings({"StringConcatenationInLoop", "ForLoopReplaceableByWhile"})
class Solution {

    int i;
    int recursiveDepth;

    public String decodeString(String s) {
        i = 0;
        recursiveDepth = 0;
        return decodeInternal(s);
    }

    public String decodeInternal(String s) {
        int k = 0;
        String enc = "";
        String r = "";
        Mode mode = Mode.DEC;
        for(; i < s.length();) {
            char c = s.charAt(i);
            if (mode.equals(Mode.DEC)) {
                if (isNonDigit(c)) {
                    r += c;
                }
                else if (isDigit(c)) {
                    mode = Mode.K;
                    k = 10 * k + ((int)c)- 48;
                }
                else {
                    throw new IllegalStateException("TODO");
                }
            }
            else if (mode.equals(Mode.K)) {
                if (isDigit(c)) {
                    k = 10 * k + ((int)c)- 48;
                }
                else if (c == '[') {
                    mode = Mode.ENC;
                }
                else {
                    throw new IllegalStateException("TODO");
                }
            }
            else {
                // we're in ENC mode
                if (isDigit(c)) {
                    // recursive call
                    this.recursiveDepth ++;
                    enc += decodeInternal(s);
                }
                else if (c == ']') {
                    // produce decoded string and reset all
                    for(int j = 0; j < k; j ++) {
                        r += enc;
                    }
                    mode = Mode.DEC;
                    enc = "";
                    k = 0;
                    if (recursiveDepth > 0) {
                        recursiveDepth --;
                        return r;
                    }
                }
                else {
                    // TODO we could detect malformed strings but we don't
                    enc += c;
                }
            }
            i ++;
        }
        return r;
    }

    public static boolean isNonDigit(char c) {
        return ((int)c < 48 || (int)c > 57) && c != '[' && c != ']';
    }
    public static boolean isDigit(char c) {
        return 48 <= (int)c && (int)c <= 57;
    }
}

enum Mode {
    DEC,
    K,
    ENC,
}

