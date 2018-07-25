package playground.java.nio.tcp;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/25/18
 */
public class ServerCommandLineLoop implements Runnable {

    private CommandLine commandLine;

    private SocketChannel clientSocketChannel;

    ServerCommandLineLoop(CommandLine commandLine) {

        this.commandLine = commandLine;
    }

    public void run() {

        while(true) {

            try {

                String command = commandLine.readLine();

                command = command.trim();

                if (command.isEmpty()) {

                    //
                    // keep looping
                    //

                    continue;
                }

                if ("exit".equals(command)) {

                    commandLine.info("server is exiting");
                    System.exit(0);
                }
                else {

                    //
                    // sent the content to the client, if connected
                    //

                    if (clientSocketChannel == null) {

                        commandLine.info("client not connected, won't send");
                    }
                    else {

                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        buffer.clear();
                        buffer.put(command.getBytes());
                        buffer.flip();

                        while (buffer.hasRemaining()) {

                            clientSocketChannel.write(buffer);
                        }
                    }
                }
            }
            catch(Exception e) {

                e.printStackTrace();
            }
        }
    }

    public synchronized void setClientSocketChannel(SocketChannel clientSocketChannel) {

        this.clientSocketChannel = clientSocketChannel;
    }
}
