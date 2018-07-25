package playground.java.nio.tcp;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/24/18
 */
public class ClientMain {

    public static void main(String[] args) throws Exception {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", ServerMain.PORT));

        System.out.println("socket connected");

        String s = "something";

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        buffer.put(s.getBytes());
        buffer.flip();

        while(buffer.hasRemaining()) {

            socketChannel.write(buffer);
        }

        System.out.println("data sent");

        socketChannel.close();

        System.out.println("socket channel closed");

    }
}
