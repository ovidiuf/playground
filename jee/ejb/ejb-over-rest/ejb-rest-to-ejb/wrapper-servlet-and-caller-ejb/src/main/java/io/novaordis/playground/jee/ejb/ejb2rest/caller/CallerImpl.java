/*
 * Copyright (c) 2017 Nova Ordis LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.novaordis.playground.jee.ejb.ejb2rest.caller;

import io.novaordis.playground.jee.ejb.ejb2rest.common.Callee;
import io.novaordis.playground.jee.ejb.ejb2rest.common.Measurements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.concurrent.CountDownLatch;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/1/17
 */
@SuppressWarnings("unused")
@Stateless
public class CallerImpl implements LocalAccessToCaller {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(CallerImpl.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    //@EJB(lookup = "ejb:/callee/CalleeImpl!io.novaordis.playground.jee.ejb.ejb2rest.common.Callee")
    @Inject
    private Callee callee;

    // Constructors ----------------------------------------------------------------------------------------------------

    // LocalAccessToCaller ---------------------------------------------------------------------------------------------

    @Override
    public void triggerRemoteCall() {

        log.info("triggering remote call");

        //sendOneInvocation();

        Measurements.invokeSeriallyInALoop(callee, 100000);

        //invokeConcurrentlyFromMultipleThreadsInALoop(10, 1000);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private void sendOneInvocation() {

        String result = callee.businessMethodA("test");
        log.info("result: " + result);
    }

    private void invokeConcurrentlyFromMultipleThreadsInALoop(int threadCount, final int invocationCountPerThread) {

        log.info("invoking in parallel on " + threadCount + " threads, " + invocationCountPerThread + " invocations per thread ...");

        final CountDownLatch latch = new CountDownLatch(threadCount);

        final long[][] callDuration = new long[threadCount][invocationCountPerThread];

        for(int i = 0; i < threadCount; i ++) {

            new Thread(new Runner(latch, i, invocationCountPerThread, callDuration), "Load Thread #" + i).start();
        }

        try {

            latch.await();
        }
        catch(InterruptedException e) {

            throw new IllegalStateException(e);
        }

        log.info("all threads finished");

        double total = 0d;

        for(int i = 0; i < threadCount; i ++) {

            for(int j = 0; j < invocationCountPerThread; j ++) {

                total += callDuration[i][j];
            }
        }

        log.info("average call duration " + (total / (threadCount * invocationCountPerThread)) + " ms");
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

    private class Runner implements Runnable {

        private CountDownLatch latch;
        private int threadIndex;
        private int invocationCountPerThread;
        private long[][] callDuration;

        public Runner(CountDownLatch latch, int threadIndex, int invocationCountPerThread, long[][] callDuration) {

            this.latch = latch;
            this.threadIndex = threadIndex;
            this.invocationCountPerThread = invocationCountPerThread;
            this.callDuration = callDuration;
        }

        @Override
        public void run() {

            for(int j = 0; j < invocationCountPerThread; j ++) {

                long t0 = System.currentTimeMillis();

                String result = callee.businessMethodA("test");

                long t1 = System.currentTimeMillis();

                callDuration[threadIndex][j] = t1 - t0;
            }

            latch.countDown();

            log.info(Thread.currentThread().getName() + " done");
        }

    }

}
