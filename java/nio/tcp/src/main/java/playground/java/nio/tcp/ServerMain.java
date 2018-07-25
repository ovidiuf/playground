package playground.java.nio.tcp;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/24/18
 */
public class ServerMain {

    public static final int PORT = 9002;

    public static void main(String[] args) throws Exception {

        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        InetSocketAddress address = new InetSocketAddress(PORT);
        ServerSocket ss = ssc.socket();
        ss.bind(address);

        SelectionKey key = ssc.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("TCP server bound to port " + PORT);

        while(true) {

            int selectedKeyCount = selector.select();

            System.out.println(selectedKeyCount + " keys selected");

            Set<SelectionKey> selectedKeys = selector.selectedKeys();

            for(Iterator<SelectionKey> i = selectedKeys.iterator(); i.hasNext(); ) {

                //
                // figure out what kind of I/O event was selected
                //

                SelectionKey k = i.next();

                if ((k.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {

                    //
                    // new connection
                    //

                    System.out.println("new connection");

                    //
                    // remove the key from the set
                    //
                    i.remove();

                    ServerSocketChannel c = (ServerSocketChannel)k.channel();
                    SocketChannel sc = c.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);

                }
                else if ((k.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {

                    //
                    // remove the key from the set
                    //

                    i.remove();

                    //
                    // read the data
                    //

                    SocketChannel sc = (SocketChannel)k.channel();

                    //
                    // TODO: Do I need to do that?
                    //
                    sc.configureBlocking(false);

                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    int bytesRead = sc.read(buffer);

                    if (bytesRead == -1) {

                        //
                        // TCP connection is closed
                        //

                        System.out.println("TCP connection closed");

                        //
                        // unregister the channel, by canceling the key. If we don't we'll always get a selection event
                        // on a closed channel
                        //

                        k.cancel();

                    }
                    else {

                        buffer.flip();

                        byte[] content = new byte[bytesRead];

                        buffer.get(content, 0, bytesRead);

                        System.out.println(new String(content));
                    }

                }
            }
        }


    }

}
