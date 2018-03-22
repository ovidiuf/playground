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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.novaordis.playground.java.java8.streams.Main.Currency.EURO;
import static io.novaordis.playground.java.java8.streams.Main.Currency.USD;
import static io.novaordis.playground.java.java8.streams.Main.Currency.YUAN;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 11/27/16
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        List<Transaction> transactions = new ArrayList<>(Arrays.asList(
                new Transaction(USD, 10),
                new Transaction(EURO, 20),
                new Transaction(YUAN, 30),
                new Transaction(USD, 40),
                new Transaction(EURO, 50),
                new Transaction(YUAN, 60)
        ));


        Map<Currency, List<Transaction>> grouping =
                transactions.
                        parallelStream().
                        filter((Transaction t) -> t.amount >= 50).
                        collect(Collectors.groupingBy(Transaction::getCurrency));

        System.out.println(grouping);

    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

    public enum Currency {

        USD,
        EURO,
        YUAN
    }

    private static class Transaction {

        private Currency currency;
        private int amount;

        public Transaction(Currency currency, int amount) {

            this.currency = currency;
            this.amount = amount;
        }

        public Currency getCurrency() {

            return currency;
        }

        @Override
        public String toString() {

            return amount + " " + currency;
        }
    }


}
