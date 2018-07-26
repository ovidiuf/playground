package playground.java.nio.tcp;

import java.io.IOException;
import java.net.SocketOption;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/25/18
 */
class Util {

    /**
     * @return socket configuration as human readable string
     */
    static String reportSocketConfig(SocketChannel socketChannel) {

        StringBuilder sb = new StringBuilder();

        Set<SocketOption<?>> supportedOptions = socketChannel.supportedOptions();

        for(Iterator<SocketOption<?>> i = supportedOptions.iterator(); i.hasNext(); ) {

            SocketOption<?> o = i.next();

            sb.append(o.name());
            sb.append("=");

            try {

                sb.append(socketChannel.getOption(o));
            }
            catch(IOException e) {

                sb.append("?");
            }

            if (i.hasNext()) {

                sb.append(", ");
            }
        }

        return sb.toString();
    }

    static void reportSocketConfiguration(String line, CommandLine commandLine, SocketChannel socketChannel) {

        if (!line.startsWith("config")) {

            throw new IllegalArgumentException("the line should start with 'config'");
        }

        if (socketChannel == null) {

            commandLine.info("no active connection");
        }
        else {

            commandLine.info(reportSocketConfig(socketChannel));
        }
    }

    static void set(String line, CommandLine commandLine, SocketChannel socketChannel) throws IOException {

        if (socketChannel == null) {

            commandLine.info("no active connection");
            return;
        }

        if (line.startsWith("set-keep-alive")) {

            line = line.substring("set-keep-alive".length()).trim().toLowerCase();

            if ("on".equals(line) || "true".equals(line)) {

                socketChannel.socket().setKeepAlive(true);
            }
            else if ("off".equals(line) || "false".equals(line)) {

                socketChannel.socket().setKeepAlive(false);
            }
            else {

                throw new IllegalArgumentException("invalid set-keep-alive argument '" + line + "'");
            }

        }
        else {

            throw new IllegalArgumentException("unknown set command '" + line + "'");
        }
    }

}
