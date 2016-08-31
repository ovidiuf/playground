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
import org.jboss.as.cli.Util;
import org.jboss.as.cli.operation.impl.DefaultCallbackHandler;
import org.jboss.as.cli.parsing.ParserUtil;
import org.jboss.as.cli.parsing.operation.OperationFormat;
import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;

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

        //noinspection ConstantConditions
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

        String command = "/:read-attribute(name=release-version";

        //
        // two alternative ways of executing the command, see below for advantages and disadvantages:
        //

        executeCommandWithContextHandle(commandContext, command);

        executeCommandLowLevel(commandContext, command);

    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    /**
     * Parses, executes and displays the command result at stdout. Does not give access to the result, it just renders
     * it to stdout.
     */
    private static void executeCommandWithContextHandle(CommandContext commandContext, String command)
            throws Exception {

        commandContext.handle(command);
    }

    /**
     * Better approach, builds the operation by parsing the command, executes the operation and exposes the result.
     */
    private static void executeCommandLowLevel(CommandContext commandContext, String command) throws Exception {

        boolean validate = true;
        //noinspection ConstantConditions
        DefaultCallbackHandler parsedCommand = new DefaultCallbackHandler(validate);
        ParserUtil.parse(command, parsedCommand);

        if (parsedCommand.getFormat() != OperationFormat.INSTANCE ) {

            //
            // we got this from the CLI code, what is "INSTANCE"?
            //

            throw new RuntimeException("NOT YET IMPLEMENTED");
        }

        ModelNode request = parsedCommand.toOperationRequest(commandContext);

        //
        // set the address
        //

        ModelControllerClient client = commandContext.getModelControllerClient();

        ModelNode result = client.execute(request);

        if(Util.isSuccess(result)) {

            System.out.println("success: " + result);

        } else {

            String failureDescription = Util.getFailureDescription(result);
            System.out.println("failure: " + failureDescription);
        }
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
