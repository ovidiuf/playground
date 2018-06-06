package io.novaordis.playground.SWIM.recon.annotations.model;

import recon.ReconName;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 6/5/18
 */
@ReconName("A")
public class A {

    @ReconName("b")
    private boolean b;

    @ReconName("s")
    private String s;

    @ReconName("i")
    private int i;

    public A(String s, int i, boolean b) {

        this.s = s;
        this.i = i;
        this.b = b;
    }

    public boolean isB() {
        return b;
    }

    public String getS() {
        return s;
    }

    public int getI() {
        return i;
    }
}
