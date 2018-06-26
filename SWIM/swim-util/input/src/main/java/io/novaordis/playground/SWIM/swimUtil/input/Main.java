package io.novaordis.playground.SWIM.swimUtil.input;

import java.nio.ByteBuffer;

import swim.util.Input;

public class Main {

    public static void main(String[] args) {

        byteInputExperiment();

        stringInputExperiment();
    }

    private static void byteInputExperiment() {

        byte[] buffer = "ABC".getBytes();

        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);

        Input input = Input.binary(byteBuffer, true);

        input.step();
        input.step();

        char c = (char)input.head();

        boolean empty = input.isEmpty();
        boolean done = input.isDone();

        input.step();

        empty = input.isEmpty();
        done = input.isDone();

        input.step();

        empty = input.isEmpty();
        done = input.isDone();
    }

    private static void stringInputExperiment() {

        Input input = Input.string("something", true);

        boolean done = input.isDone();

        boolean empty = input.isEmpty();

        int head = input.head();

        input.step();

        done = input.isDone();

        empty = input.isEmpty();

        head = input.head();
    }
}
