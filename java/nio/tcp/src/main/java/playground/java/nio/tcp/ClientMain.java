package playground.java.nio.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

        //
        // enter the command line loop
        //

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true) {

            System.out.print("> ");
            String line = br.readLine();

            if (line.startsWith("exit")) {

                break;
            }

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            buffer.put(line.getBytes());
            buffer.flip();

            while(buffer.hasRemaining()) {

                socketChannel.write(buffer);
            }
        }

        socketChannel.close();

        System.out.println("socket channel closed");
    }
}
