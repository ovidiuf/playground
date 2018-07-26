package playground.java.nio.tcp;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/24/18
 */
public class ServerMain {

    static final int PORT = 9002;

    static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("MM/dd/yy HH:mm:ss");

    public static void main(String[] args) throws Exception {

        final CommandLine c = new CommandLine();

        c.start();

        //
        // command line loop
        //

        final ServerCommandLineLoop commandLineLoop = new ServerCommandLineLoop(c);

        new Thread(commandLineLoop, "Command Line Loop").start();

        //
        // Main selector multiplexer. We use the main thread as selector thread.
        //

        Selector selector = Selector.open();

        //
        // The ServerSocketChannel used to accept new TCP connections
        //

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress(PORT);
        ServerSocket ss = serverSocketChannel.socket();
        ss.bind(address);

        //
        // Register the ServerSocketChannel with the selector
        //

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        c.info(TIMESTAMP_FORMAT.format(new Date()) + ": TCP server bound to port " + PORT);

        //
        // The main event loop
        //

        while(true) {

            //
            // This call blocks until at least one I/O event occurs
            //

            selector.select();

            Set<SelectionKey> selectedKeys = selector.selectedKeys();

            for(Iterator<SelectionKey> i = selectedKeys.iterator(); i.hasNext(); ) {

                //
                // Figure out what kind of I/O event was selected
                //

                SelectionKey k = i.next();

                if ((k.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {

                    //
                    // New connection
                    //

                    c.info(TIMESTAMP_FORMAT.format(new Date()) + ": new TCP connection");

                    //
                    // Remove the key from the set
                    //

                    i.remove();

                    //
                    // Retrieve the SocketChannel for the new connection, make it non-blocking, and register
                    // it with the same selector so now we can handle incoming data events on the same event
                    // loop
                    //

                    ServerSocketChannel ssc = (ServerSocketChannel)k.channel();
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);

                    //
                    // Pass the channel to the command line loop subsystem to send data back on it
                    //

                    commandLineLoop.setClientSocketChannel(sc);
                }
                else if ((k.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {

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

                        c.info(TIMESTAMP_FORMAT.format(new Date()) + ": TCP connection closed");

                        //
                        // remove the socket channel from the command line loop
                        //

                        commandLineLoop.setClientSocketChannel(null);

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
                        byte[] contentBuffer = new byte[bytesRead];
                        buffer.get(contentBuffer, 0, bytesRead);

                        String content = new String(contentBuffer);

                        c.info(TIMESTAMP_FORMAT.format(new Date()) + ": " + content);
                    }
                }
                else {

                    c.info("unexpected selection key " + k);
                }
            }
        }
    }
}
