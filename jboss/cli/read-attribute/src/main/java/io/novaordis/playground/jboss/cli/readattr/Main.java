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

package io.novaordis.playground.jboss.cli.readattr;

import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandContextFactory;
import org.jboss.as.cli.CommandLineException;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 8/26/16
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        String controllerHost = null;
        int controllerPort = -1;
        String username = null;
        char[] password = null;
        boolean disableLocalAuth = false;
        boolean initConsole = false;
        int connectionTimeout = -1;

        CommandContext commandContext = CommandContextFactory.getInstance().newCommandContext(
                controllerHost, controllerPort, username, password, disableLocalAuth, initConsole, connectionTimeout);

        try {
            commandContext.connectController();
        }
        catch(CommandLineException e) {

            System.out.println("failed to connect controller");
            e.printStackTrace();
            return;
        }

        //
        // send a "read attribute" command
        //
        // /:read-attribute(name=release-version)
        //

        commandContext.handle(":read-attribute(name=release-version");

//        ModelNode request = new ModelNode();
//
//        //
//        // set the adress
//        //
//
//        ModelNode address = new ModelNode();
//        address.setEmptyList();
//
//        //
//        // set the operation
//        //
//
//        String operationName = "read-attribute";
//        request.get(Util.OPERATION).set(operationName);
//
//        //
//        // set the operation arguments
//        //
//
//        String propertyName = "name";
//        String propertyValue = "release-version";
//        final ModelNode propertyValueNode = ArgumentValueConverter.DEFAULT.fromString(commandContext, propertyValue);
//        request.get(propertyName).set(propertyValueNode);
//
//        commandContext.set("OP_REQ", request);

    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
