package io.novaordis.playground.SWIM.swimUtil.iteratee;

import swim.util.Input;
import swim.util.Iteratee;

public class Main {

    public static void main(String[] args) {

        iterateeFullyConsumesInput();

        inputRunsTemporarilyOutOfContent();
    }

    private static void iterateeFullyConsumesInput() {

        Iteratee<String> lineParser = Iteratee.LINE; // a LineParser

        //
        // by default, the "Can-Consume" state and "Continuation" state are semantically equivalent
        //

        assert(lineParser.isCont());

        String s = "AB";

        Input stringInput = Input.string(s, true);

        Iteratee<String> i = lineParser.run(stringInput);

        assert(i.isDone());
        assert(!i.isCont());
        assert(!i.isError());
        assert(i.equals(lineParser));

        String result = i.bind();

        assert(s.equals(result));

    }

    private static void inputRunsTemporarilyOutOfContent() {

        Iteratee<String> lineParser = Iteratee.LINE; // a LineParser

        //
        // by default, the "Can-Consume" state and "Continuation" state are semantically equivalent
        //

        assert(lineParser.isCont());

        String s = "AB";

        InputThatRunsOutOfContent inputThatRunsOutOfContent = new InputThatRunsOutOfContent(s, 1);

        Iteratee<String> i = lineParser.run(inputThatRunsOutOfContent);

        //
        // the original iteratee is in a continuation state ...
        //

        assert(!lineParser.isDone());
        assert(lineParser.isCont());
        assert(!lineParser.isError());

        //
        // lineParser is in a continuation state, nothing to return
        //
        assert(null == lineParser.bind());

        //
        // ... while the returned iteratee is "done" and contains the partial result
        //

        assert(i.isDone());
        assert(!i.isCont());
        assert(!i.isError());

        String partialResult = i.bind();
        assert("A".equals(partialResult));

        //
        // "resume" data flow on the Input
        //

        inputThatRunsOutOfContent.resume();

        //
        // resume the iteratee run
        //

        Iteratee<String> i2 = lineParser.run(inputThatRunsOutOfContent);


        assert(lineParser.isDone());
        assert(!lineParser.isCont());
        assert(!lineParser.isError());
        assert(lineParser.equals(i2));

        String partialResult2 = i2.bind();
        assert("B".equals(partialResult2));
    }

}
