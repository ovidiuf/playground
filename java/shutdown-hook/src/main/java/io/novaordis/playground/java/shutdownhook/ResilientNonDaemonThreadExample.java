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

package io.novaordis.playground.java.shutdownhook;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/7/17
 */
public class ResilientNonDaemonThreadExample {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception
    {
        System.out.println("launching an independent, non-daemon thread");

        new ResilientNonDaemonThread().start();

//        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
//        System.out.println("shutdown hook registered");

        Thread.sleep(5000L);

        System.out.println("attempting shutdown");

        System.exit(0);
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

//    private static class ShutdownHook extends Thread
//    {
//        public ShutdownHook()
//        {
//            super("Playground Shutdown Hook Thread");
//        }
//
//        @Override
//        public void run()
//        {
//            System.out.println("entering the shutdown hook");
//
//            int sec = 1;
//
//            try
//            {
//                System.out.println("sleeping for " + sec + " seconds in shutdown ...");
//                Thread.sleep(sec * 1000);
//                System.out.println("shutdown hook woke up");
//
//            }
//            catch(Exception e)
//            {
//                e.printStackTrace();
//            }
//
//        }
//    }

    private static class ResilientNonDaemonThread extends Thread {

        public ResilientNonDaemonThread() {

            super("Independent Thread");
        }

        @Override
        public void run() {

            while(true) {

                try {
                    System.out.println("entering sleep for 1 sec, isDaemon? " + Thread.currentThread().isDaemon() + " ...");
                    Thread.sleep(1000L);
                }
                catch(Exception e) {

                    e.fillInStackTrace();
                }
            }
        }
    }
}
