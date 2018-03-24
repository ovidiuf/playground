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

//        List<Apple> result;
//
//        result = filter(apples,
//                (Apple apple) -> YELLOW.equals(apple.getColor()) && apple.getWeight() > 200);
//
//        System.out.println(result);
//
//        result = filter(apples,
//                (Apple apple) -> apple.getWeight() <= 150);
//
//
//        System.out.println(result);
//
//        //
//        // block-style lambdas
//        //
//
//        ApplePrettyPrint p = (Apple a) -> {
//
//            return a.getColor() + " (" + a.getWeight() + " grams)";
//        };
//
//        prettyPrint(apples, p);
//
//        ApplePrettyPrint2 p2 = (Apple a) -> {
//            System.out.println("the apple is " + a.getColor());
//            System.out.println("the apple weighs " + a.getWeight() + " grams");
//        };
//
//        prettyPrint2(apples, p2);

//        final List<String> shared = new ArrayList<>();
//
//        FunctionalInterfaceWhoseMethodReturnsVoid l = s -> shared.add(s);



//
//        //
//        // expression-style lambdas
//        //
//
//        ApplePrettyPrint p3 = (Apple a) -> a.getColor() + " (" + a.getWeight() + " GRAMS)";
//
//        prettyPrint(apples, p3);
//
//        testFunctionalInterfaceWithDefaultMethods(apples, a -> { System.out.println(a); });
//
//        EmptyParameterList l = () -> System.out.println("something");
//
//
//        ExceptionLambda l2 =
//                s -> {
//
//                    if ("blah".equals(s)) {
//
//                        throw new Exception("not this");
//                    }
//
//                    return s.toUpperCase();
//                };


        SomeFunctionalInterface l = (String s, Integer i) -> s + " " + i.toString();
        SomeFunctionalInterface l2 = (s, i) -> s + " " + i.toString();

    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private static List<Apple> filter(List<Apple> apples, ApplePredicate predicate) {

        List<Apple> result = new ArrayList<>();

        for(Apple a: apples) {

            if (predicate.isAcceptable(a)) {

                result.add(a);
            }
        }

        return result;
    }

    private static void prettyPrint(List<Apple> apples, ApplePrettyPrint pp) {

        List<Apple> result = new ArrayList<>();

        for(Apple a: apples) {

            System.out.println(pp.prettyPrint(a));
        }
    }

    private static void prettyPrint2(List<Apple> apples, ApplePrettyPrint2 pp) {

        List<Apple> result = new ArrayList<>();

        for(Apple a: apples) {

            pp.prettyPrint(a);
        }
    }

    private static void testFunctionalInterfaceWithDefaultMethods(List<Apple> apples, HasDefaultMethods hdm) {

        for(Apple a: apples) {

            hdm.something(a);
        }
    }

    public static Callable<String> fetch() {

        return () -> "something";
    }



    // Inner classes ---------------------------------------------------------------------------------------------------

}
