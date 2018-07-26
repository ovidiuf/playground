package playground.java.nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/24/18
 */
public class ClientMain {

    public static void main(String[] args) throws Exception {

        CommandLine c = new CommandLine();
        c.start();

        //
        // Use a selector to receive data asynchronously
        //

        final Selector selector = Selector.open();

        //
        // Create the SocketChannel
        //

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", ServerMain.PORT));
        socketChannel.configureBlocking(false);

        c.info("socket connected");

        //
        // Register the ServerSocketChannel with the selector
        //

        socketChannel.register(selector, SelectionKey.OP_READ);

        //
        // Create a selector thread that will be notified on asynchronous data arrival
        //

        new Thread(() -> {

            while(true) {

                try {

                    selector.select();

                    Set<SelectionKey> selectedKeys = selector.selectedKeys();

                    for(Iterator<SelectionKey> i = selectedKeys.iterator(); i.hasNext(); ) {

                        SelectionKey k = i.next();

                        if ((k.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {

                            //
                            // New data available, read it
                            //

                            //
                            // Remove the key from the set
                            //

                            i.remove();

                            SocketChannel sc = (SocketChannel)k.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            int bytesRead = sc.read(buffer);

                            if (bytesRead == -1) {

                                //
                                // TCP connection was closed
                                //

                                c.info(ServerMain.TIMESTAMP_FORMAT.format(new Date()) + ": TCP connection closed");

                                //
                                // Unregister the channel, by canceling the key. If we don't do this, data availability
                                // events for zero-length data will keep popping up.
                                //

                                k.cancel();

                            }
                            else {

                                //
                                // Read data
                                //

                                buffer.flip();
                                byte[] content = new byte[bytesRead];
                                buffer.get(content, 0, bytesRead);

                                c.info(ServerMain.TIMESTAMP_FORMAT.format(new Date()) + ": " + new String(content));
                            }
                        }
                        else {

                            c.info("unexpected selection key " + k);
                        }
                    }
                }
                catch(IOException e) {

                    e.printStackTrace();
                }
            }
        }, "Selector Thread").start();

        //
        // Enter the command line loop, this is where we write data on the socket
        //

        while(true) {

            String line = c.readLine();

            if (line.startsWith("exit")) {

                break;
            }

            //
            // Send data
            //

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            buffer.put(line.getBytes());
            buffer.flip();

            while(buffer.hasRemaining()) {

                socketChannel.write(buffer);
            }
        }

        socketChannel.close();

        c.info("socket channel closed");

        System.exit(0);
    }
}
