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
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

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

        //
        // Comparator
        //

        apples.sort((Apple a1, Apple a2) -> a1.getWeight() - a2.getWeight());

        System.out.println(apples);

        //
        // Runnable
        //

        final CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> { System.out.println("from a different thread"); latch.countDown(); }).start();

        latch.await();

        //
        // Callable
        //

        Callable
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
