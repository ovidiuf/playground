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
public class ShutdownHookIsDaemon {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        ShutdownHook h = new ShutdownHook();
        h.setDaemon(true);

        Runtime.getRuntime().addShutdownHook(h);
        System.out.println("initiating shutdown");
        System.exit(0);
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

    private static class ShutdownHook extends Thread {

        public ShutdownHook() {
            super("Playground Shutdown Hook Thread");
        }

        @Override
        public void run() {

            System.out.println("entering the shutdown hook");

            int sec = 30;

            try {

                System.out.println("sleeping for " + sec + " seconds in shutdown ...");
                Thread.sleep(sec * 1000);
                System.out.println("shutdown hook woke up");

            }
            catch(Exception e) {
                e.printStackTrace();
            }

        }
    }

}
