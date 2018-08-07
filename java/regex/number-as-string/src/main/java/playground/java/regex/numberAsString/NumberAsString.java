package playground.java.regex.numberAsString;

import java.util.regex.*;

public class NumberAsString {

    private static final Pattern[] NUMBER_PATTERNS = new Pattern[] {

            Pattern.compile("[+|-]?[0-9]+"),                          // integer

            Pattern.compile("[+|-]?[0-9]+\\.[0-9]*"),                 // float 1
            Pattern.compile("[-|+]?[0-9]+(\\.[0-9]*)?e[+|-]?[0-9]+"), // e notation 1

            Pattern.compile("[+|-]?[0-9]*\\.[0-9]+"),                 // float 2
            Pattern.compile("[+|-]?[0-9]*\\.[0-9]+e[+|-]?[0-9]+"),    // e notation 2
    };

    public static boolean isNumber(String s) {

        if (s == null) {

            return false;
        }

        s = s.trim();

        for(Pattern p: NUMBER_PATTERNS) {

            Matcher m = p.matcher(s);

            if (m.matches()) {

                return true;
            }
        }

        return false;
    }
}
