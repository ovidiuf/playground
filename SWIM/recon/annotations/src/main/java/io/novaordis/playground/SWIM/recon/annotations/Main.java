package io.novaordis.playground.SWIM.recon.annotations;

import recon.Form;
import recon.Value;

import io.novaordis.playground.SWIM.recon.annotations.model.A;

public class Main {

    public static void main(String[] args) {

        A a = new A("blah", 10, true);

        Value value = Form.forClass(A.class).mold(a);

        String recon = value.toRecon();

        System.out.println(recon);
    }
}
