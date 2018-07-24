package playground.swim.maplane.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/24/18
 */
class Util {

    private Util() {
    }

    /**
     * May return an empty list.
     */
    static List<String> toArgList(String spaceSeparatedArguments) {

        String[] a = spaceSeparatedArguments.split( " +");

        List<String> result = new ArrayList<>();

        for(String s: a) {

            if (!s.isEmpty()) {

                result.add(s);
            }
        }

        return result;
    }
}
