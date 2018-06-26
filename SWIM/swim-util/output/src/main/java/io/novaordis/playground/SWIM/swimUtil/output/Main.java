package io.novaordis.playground.SWIM.swimUtil.output;

import java.nio.ByteBuffer;

import swim.util.Input;
import swim.util.Output;

public class Main {

    public static void main(String[] args) {

        Input i = Input.string("something", true);

        Output<ByteBuffer> o = Output.utf8Buffer();

        while(!i.isDone()) {

            char h = (char)i.head();

            i.step();

            if (o.isFull()) {

                throw new RuntimeException("Output is full, what now?");
            }

            if (o.isDone()) {

                throw new RuntimeException("Output is done, what now?");
            }

            o.write(h);
        }

        ByteBuffer byteBuffer = o.bind();

        System.out.println(new String(byteBuffer.array()));
    }
}
