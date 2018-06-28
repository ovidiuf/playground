package io.novaordis.playground.SWIM.swimUtil.iteratee;

import swim.util.Input;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 6/27/18
 */
public class InputThatRunsOutOfContent extends Input {

    private String fullContent;
    private int next;
    private int indexAtWhichWeSimulateTemporaryOutage;

    InputThatRunsOutOfContent(String fullContent, int indexAtWhichWeSimulateTemporaryOutage) {

        this.fullContent = fullContent;
        this.next = 0;
        this.indexAtWhichWeSimulateTemporaryOutage = indexAtWhichWeSimulateTemporaryOutage;
    }

    @Override
    public boolean isEmpty() {

        return next == indexAtWhichWeSimulateTemporaryOutage || next >= fullContent.length();
    }

    @Override
    public int head() {

        if (isEmpty()) {

            throw new UnsupportedOperationException();
        }

        return fullContent.codePointAt(next);
    }

    @Override
    public void step() {

        if (isEmpty()) {

            throw new UnsupportedOperationException();
        }

        next ++;
    }

    @Override
    public Input clone() {

        throw new UnsupportedOperationException();
    }

    /**
     * "Resume" delivery that was temporarily interrupted by "outage".
     */
    public void resume() {

        indexAtWhichWeSimulateTemporaryOutage = -1;
    }
}
