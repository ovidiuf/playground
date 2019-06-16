package playground.consistent.hashing.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author ovidiu@feodorov.com
 * @since 06/15/2019
 */
class CommandLineLoop {

    private SameAddressSpaceClusterSimulation cluster;

    private BufferedReader br;

    CommandLineLoop(SameAddressSpaceClusterSimulation cluster) {

        this.cluster = cluster;

        br = new BufferedReader(new InputStreamReader(System.in));
    }

    void run() throws Exception {

        System.out.println("entering command loop ...");

        while(true) {

            System.out.print("> ");
            String line = br.readLine().trim();

            try {

                if (line.isEmpty()) {

                    continue;
                }
                else if ("exit".equalsIgnoreCase(line)) {

                    return;
                }
                else if ("info".equalsIgnoreCase(line)) {

                    info();
                }
                else if (line.startsWith("write")) {

                    write(line.substring("write".length()));
                }
                else {

                    throw new UserErrorException("unknown command '" + line + "'");
                }
            }
            catch (UserErrorException e) {

                System.out.println("[error]: " + e.getMessage());
            }
        }
    }

    private void info() {

        System.out.println(cluster.getInfo());
    }

    private void write(String line) throws UserErrorException {

        line = line.trim();

        int spaceIndex = line.indexOf(' ');

        if (spaceIndex == -1) {

            throw new UserErrorException("a <key> <value> pair must be specified");
        }

        String key = line.substring(0, spaceIndex);
        String value = line.substring(spaceIndex + 1).trim();

        //noinspection unchecked
        cluster.getClient().write(key, value);
    }
}
