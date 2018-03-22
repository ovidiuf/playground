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
import java.util.function.Predicate;

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

        List<Apple> result;

        result = filter(apples,
                (Apple apple) -> YELLOW.equals(apple.getColor()) && apple.getWeight() > 200);

        System.out.println(result);

        result = filter(apples,
                (Apple apple) -> apple.getWeight() <= 150);

        System.out.println(result);
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private static <T> List<T> filter(List<T> items, Predicate<T> predicate) {

        List<T> result = new ArrayList<>();

        for(T i : items) {

            if (predicate.test(i)) {

                result.add(i);
            }
        }

        return result;
    }



    // Inner classes ---------------------------------------------------------------------------------------------------

}
