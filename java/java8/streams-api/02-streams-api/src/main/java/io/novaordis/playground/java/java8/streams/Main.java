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

package io.novaordis.playground.java.java8.streams;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 11/27/16
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

//    public enum StringType {
//
//        EMPTY,
//        SHORT,
//        LONG
//    }

    public static void main(String[] args) throws Exception {

        main2();
//
//        Map<StringType, List<String>> map;
//
//        try(Stream<String> stream = Files.lines(new File("/Users/ovidiu/tmp/test.txt").toPath())) {
//
//            //
//            // group the line by the string type
//            //
//
//            map = stream.collect(Collectors.groupingBy(s -> {
//
//                if (s.isEmpty()) {
//
//                    return StringType.EMPTY;
//                }
//
//                if (s.length() <= 5) {
//
//                    return StringType.SHORT;
//                }
//
//                return StringType.LONG;
//            }));
//
//            for(StringType t: StringType.values()) {
//
//                System.out.println(t + ": " + map.get(t));
//
//            }
//        }
//        catch(IOException e) {
//
//            // ...
//        }


//        Trader raoul = new Trader("Raoul", "Cambridge");
//        Trader mario = new Trader("Mario", "Milan");
//        Trader alan = new Trader("Alan", "Cambridge");
//        Trader brian = new Trader("Brian", "Cambridge");
//
//        List<Transaction> transactions = Arrays.asList(
//
//                new Transaction(brian, 2011, 300),
//                new Transaction(raoul, 2012, 1000),
//                new Transaction(raoul, 2011, 400),
//                new Transaction(mario, 2012, 710),
//                new Transaction(mario, 2012, 700),
//                new Transaction(alan, 2012, 950)
//        );


//        transactions.
//                stream().
//                filter(t -> t.getYear() == 2011).
//                sorted(Comparator.comparing(Transaction::getValue)).
//                forEach(System.out::println);


//        transactions.
//                stream().
//                map(t -> t.getTrader().getLocation()).
//                distinct().
//                forEach(System.out::println);


//        transactions.
//                stream().
//                map(Transaction::getTrader).
//                filter(t -> "Cambridge".equals(t.getLocation())).
//                distinct().
//                sorted(Comparator.comparing(Trader::getName)).
//                forEach(System.out::println);

//        Optional<String> s = transactions.
//                stream().
//                map(t -> t.getTrader().getName()).
//                distinct().
//                sorted().
//                reduce((partial, next) -> partial + ", " + next);
//
//        System.out.println(s);

//        boolean b = transactions.stream().anyMatch(t -> "Milan".equals(t.getTrader().getLocation()));
//
//        System.out.println(b);

//        transactions.
//                stream().
//                filter(t -> "Cambridge".equals(t.getTrader().getLocation())).
//                map(Transaction::getValue).
//                forEach(System.out::println);

//        System.out.println(transactions.
//                stream().
//                map(Transaction::getValue).
//                max(Integer::compareTo));

//        System.out.println(transactions.
//                stream().
//                min(Comparator.comparing(Transaction::getValue)));

    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private static void main2() throws Exception {


        String[] a = new String[10];

        for(String i: a) {

            System.out.println(i);
        }
    }

    // Inner classes ---------------------------------------------------------------------------------------------------


}
