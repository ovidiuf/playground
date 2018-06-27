package io.novaordis.playground.SWIM.swimUtil.iteratee;

import swim.util.Input;
import swim.util.Iteratee;

public class Main {

    public static void main(String[] args) {

        Iteratee<String> lineParser = Iteratee.LINE; // a LineParser

        String s = "AB";

        Input stringInput = Input.string(s, true);

        Iteratee<String> iteratee = lineParser.run(stringInput);

        assert(iteratee.isDone());
        assert(!iteratee.isCont());
        assert(!iteratee.isError());

        String result = iteratee.bind();

        assert(s.equals(result));

    }
}
