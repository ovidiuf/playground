/*
 * Copyright (c) 2018 Nova Ordis LLC
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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/23/18
 */
public class A {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    private static Integer staticInt = 10;

    // Attributes ------------------------------------------------------------------------------------------------------

    private Integer memberInt = 20;

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    public void executeSomething() throws Exception {


        ExecutorService s = Executors.newFixedThreadPool(1);

        int localInt = 30;

        CountDownLatch latch = new CountDownLatch(1);

        s.submit(() -> {

            System.out.println("static variable after increment: " + (++staticInt));
            System.out.println("member variable after increment: " + (++ memberInt));
            System.out.println("local variable:  " + localInt);

            latch.countDown();
        });

        latch.await();

        s.shutdown();

        System.out.println("static variable: " + staticInt);
        System.out.println("member variable: " + memberInt);


    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
