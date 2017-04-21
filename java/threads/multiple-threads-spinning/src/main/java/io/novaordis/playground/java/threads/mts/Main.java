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

package io.novaordis.playground.java.threads.mts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 4/21/17
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        Configuration c = new Configuration(args);

        Random r = new Random(System.currentTimeMillis());

        int threadCount = c.getThreadCount();

        final CountDownLatch latch = new CountDownLatch(threadCount + 1);

        for(int i = 0; i < c.getThreadCount(); i ++) {

            long seed = r.nextLong();

            new Thread(new Runnable() {

                private Random random = new Random(seed);

                @Override
                public void run() {

                    System.out.println(Thread.currentThread().getName() + " started");

                    //noinspection InfiniteLoopStatement
                    while(true) {

                        //
                        // do some busy then sleep in sequence, forever
                        //

                        busyWork();

                        try {

                            Thread.sleep(random.nextInt(21));
                        }
                        catch(InterruptedException e) {

                            System.err.println(Thread.currentThread().getName() + " was interrupted, resuming ...");
                        }
                    }
                }

                private void busyWork() {

                    //noinspection MismatchedQueryAndUpdateOfCollection
                    List<Integer> randoms = new ArrayList<Integer>();

                    for(int i = 0; i < 10000; i ++) {

                        int r = random.nextInt();
                        randoms.add(r);
                    }
                }

            }, "Experimental Thread #" + i).start();
        }

        //
        // the main thread waits until all "business" threads exit, or the program is Ctrl-C
        //

        latch.await();

        System.out.println("exiting ...");
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
