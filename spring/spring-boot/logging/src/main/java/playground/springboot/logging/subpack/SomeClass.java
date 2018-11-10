package playground.springboot.logging.subpack;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SomeClass {

    public void run() {

        log.debug(this + " is running");

        throw new IllegalStateException("we are in an illegal state");
    }
}
