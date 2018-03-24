/*
 * Copyright (c) 2016 Nova Ordis LLC
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

package io.novaordis.playground.java.java8.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

import static io.novaordis.playground.java.java8.lambda.Color.GREEN;
import static io.novaordis.playground.java.java8.lambda.Color.RED;
import static io.novaordis.playground.java.java8.lambda.Color.YELLOW;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 11/27/16
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        List<Apple> apples = new ArrayList<>(Arrays.asList(
                new Apple(RED, 150),
                new Apple(RED, 200),
                new Apple(GREEN, 120),
                new Apple(GREEN, 90),
                new Apple(GREEN, 125),
                new Apple(YELLOW, 120),
                new Apple(YELLOW, 210),
                new Apple(YELLOW, 150)));

        comparatorExample(apples);

        runnableExample();

        callableExample();

        cyclicBarrierExample();
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private static void comparatorExample(List<Apple> apples) {

        apples.sort((Apple a1, Apple a2) -> a1.getWeight() - a2.getWeight());

        System.out.println(apples);

    }

    private static void runnableExample() throws Exception {

        final CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> { System.out.println("from a different thread"); latch.countDown(); }).start();

        latch.await();
    }

    private static void callableExample() throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(1);

        final String arg = "something";

        Future<String> future = executor.submit(() -> arg.toUpperCase());

        String result = future.get();

        System.out.println("callable result: " + result);

        executor.shutdown();

    }

    private static void cyclicBarrierExample() throws Exception {

        int threadCount = 10;

        final CyclicBarrier barrier = new CyclicBarrier(
                threadCount, () -> System.out.print("ALL threads have finished"));

        for(int i = 0; i < threadCount; i ++) {

            new Thread(() ->  {

                int ms = ThreadLocalRandom.current().nextInt(10000);

                try {

                    System.out.println("sleeping " + ms / 1000 + " seconds");
                    Thread.sleep((long) ms);
                }
                catch(Exception e) {

                    // OK
                }

                try {

                    barrier.await();
                }
                catch(Exception e)  {

                    System.out.println("barrier error");

                    // ...
                }
            }, "Thread #" + i).start();
        }
    }


    // Inner classes ---------------------------------------------------------------------------------------------------

}
