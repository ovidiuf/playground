package playground.leetcode;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("StringConcatenationInLoop")
public class L0068 {

//    private final int maxWidth = 16;
//    private final String[] words = new String[] {"This", "is", "an", "example", "of", "text", "justification."};

    private final int maxWidth = 16;
    private final String[] words = new String[] {"What","must","be","acknowledgment","shall","be"};

//    private final int maxWidth = 20;
//    private final String[] words = new String[] {"Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"};

    public void run() {
        List<String> result = fullJustify(words, maxWidth);
        for(String s: result) {
            System.out.println("\"" + s + "\"");
        }
    }

    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        List<String> crtLine = new ArrayList<>();
        int crtLL = 0;
        for(int i = 0; i < words.length; i ++) {
            int candLL = crtLL + words[i].length() + (crtLL == 0 ? 0 : 1);
            if (candLL <= maxWidth) {
                crtLine.add(words[i]);
                crtLL = candLL;
                if (i == words.length - 1) {
                    result.add(commit(crtLine, crtLL, true));
                }
            }
            else {
                // we know that crtLine.length <= maxWidth from
                // the previous iteration
                result.add(commit(crtLine, crtLL, false));
                crtLL = 0;
                crtLine = new ArrayList<>();
                i--;
            }
        }
        return result;
    }

    /**
     * We get a list of words for which the total length + inter-word spacing
     * is <= maxWidth
     **/
    private String commit(List<String> crtLine, int lengthInclInterWordSpacing,
                          boolean finalLine) {
        int extraPadding = maxWidth - lengthInclInterWordSpacing;
        String s = "";
        if (finalLine || crtLine.size() == 1) {
            for(int i = 0; i < crtLine.size(); i ++) {
                s += (i > 0 ? " ": "") + crtLine.get(i);
            }
            for(int i = 0; i < extraPadding; i ++) {
                s += " ";
            }
            return s;
        }
        int eppg = extraPadding / (crtLine.size() - 1);
        // extra spaces to distribute to left gaps (may be 0)
        int eslg = extraPadding - eppg * (crtLine.size() - 1);
        for(int i = 0; i < crtLine.size(); i ++) {
            s +=
                    (i == 0 ? "": " " + spaces(eppg) + (eslg-- > 0 ? " " : "")) +
                            crtLine.get(i);
        }
        return s;
    }

    private String spaces(int count) {
        String s = "";
        for(int i = 0; i < count; i ++) {
            s += " ";
        }
        return s;
    }

}
