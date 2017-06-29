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

package io.novaordis.playground.wildfly.infinispan.hotrodclient;

import io.novaordis.playground.wildfly.infinispan.hotrodclient.commands.Command;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/29/16
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        Runtime runtime = new Runtime();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //noinspection TryFinallyCanBeTryWithResources
        try {

            while (true) {

                System.out.print("> ");
                String line = br.readLine();

                if (line.trim().length() == 0) {
                    continue;
                }

                try {
                    execute(line, runtime);
                }
                catch(UserErrorException e) {
                    Console.error(e.getMessage());
                }
                catch(ExitException e) {
                    System.exit(0);
                }
            }
        }
        finally {
            br.close();
        }
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private static void execute(String line, Runtime runtime) throws Exception {

        if (line.toLowerCase().startsWith("exit")) {
            throw new ExitException();
        }

        String commandName = line.replaceAll(" .*$", "").toLowerCase();
        String restOfTheLine = commandName.equals(line) ? "" : line.substring(commandName.length() + 1);
        String commandSimpleClassName = Character.toUpperCase(commandName.charAt(0)) + commandName.substring(1);
        String packageName = Main.class.getPackage().getName();
        String commandClassName = packageName + ".commands." + commandSimpleClassName;

        Class c;

        try {

            c = Class.forName(commandClassName);
        }
        catch (ClassNotFoundException e) {

            Console.error("unknown command \"" + commandName + "\"");
            return;
        }

        Command command = (Command)c.newInstance();
        command.setRuntime(runtime);

        command.execute(restOfTheLine);
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
