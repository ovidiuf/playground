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

package io.novaordis.playground.java.java8.streams;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/29/18
 */
public class Transaction {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private Trader trader;
    private int year;
    private int value;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Transaction(Trader trader, int year, int value) {

        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public Trader getTrader() {

        return trader;
    }

    public int getYear() {

        return year;
    }

    public int getValue() {

        return value;
    }

    @Override
    public String toString() {

        return "$" + value + " by " + trader + " in " + year;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
