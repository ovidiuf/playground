package playground.consistent.hashing.impl;

import playground.consistent.hashing.NodeAddress;
import playground.consistent.hashing.impl.clustermanager.ClusterView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author ovidiu@feodorov.com
 * @since 06/15/2019
 */
class CommandLineLoop {

    private static final String HELP_FILE_NAME = "HELP.txt";

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
                else if ("nodes".equalsIgnoreCase(line)) {

                    nodes();
                }
                else if (line.startsWith("write")) {

                    write(line.substring("write".length()));
                }
                else if (line.startsWith("add")) {

                    add(line.substring("add".length()));
                }
                else if (line.startsWith("help")) {

                    displayHelp();
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

    private void nodes() {

        ClusterView cv = cluster.getClient().getClusterView();

        //noinspection unchecked
        List<NodeAddress> nodes = cv.getNodeAddresses();

        if (nodes.isEmpty()) {

            System.out.println("no nodes");
        }
        else {

            throw new RuntimeException("NOT YET IMPLEMENTED");
        }
    }

    private void write(String line) throws UserErrorException {

        line = line.trim();

        int spaceIndex = line.indexOf(' ');

        if (spaceIndex == -1) {

            throw new UserErrorException("a <key> <value> pair must be specified");
        }

        String key = line.substring(0, spaceIndex);
        String value = line.substring(spaceIndex + 1).trim();

        try {

            //noinspection unchecked
            cluster.getClient().write(key, value);
        }
        catch(IllegalStateException e) {

            throw new UserErrorException(e.getMessage());
        }
    }

    private void add(String line) throws UserErrorException {

        line = line.trim();

        if (line.isEmpty()) {

            //
            // random node
            //

            throw new RuntimeException("NOT YET IMPLEMENTED: add() node in random location");

        }
        else {

            //
            // node at a specific location on the hash wheel
            //

            String hashWheelLocation = line;

            throw new RuntimeException("NOT YET IMPLEMENTED: add() node at this location in hash wheel: " + hashWheelLocation);
        }

    }

    private void displayHelp() throws UserErrorException {

        ClassLoader cl = CommandLineLoop.this.getClass().getClassLoader();

        InputStream is = cl.getResourceAsStream(HELP_FILE_NAME);

        if (is == null) {

            throw new UserErrorException(HELP_FILE_NAME + " not found in classpath");
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            String line;

            while((line = br.readLine()) != null) {

                System.out.println(line);
            }
        }
        catch(IOException e) {

            throw new UserErrorException("failed to read " + HELP_FILE_NAME);
        }

    }
}
