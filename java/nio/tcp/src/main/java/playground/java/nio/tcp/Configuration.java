package playground.java.nio.tcp;

import java.text.DateFormat;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/26/18
 */
public class Configuration {

    public static final String DEFAULT_ADDRESS = "localhost";
    public static final int DEFAULT_PORT = 9002;

    private String address;
    private int port;

    public Configuration(String[] args) throws UserErrorException {

        this.address = DEFAULT_ADDRESS;
        this.port = DEFAULT_PORT;

        for(int i = 0; i < args.length; i ++) {

            String crt = args[i];

            if ("-a".equals(crt)) {

                address = args[++i];
            }
            else if ("-p".equals(crt)) {

                port = Integer.parseInt(args[++i]);
            }
        }
    }

    public String getAddress() {

        return address;
    }

    public int getPort() {

        return port;
    }
}
