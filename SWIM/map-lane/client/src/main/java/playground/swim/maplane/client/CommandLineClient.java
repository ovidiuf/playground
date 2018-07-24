package playground.swim.maplane.client;

import recon.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import swim.api.MapDownlink;
import swim.client.SwimClient;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/24/18
 */
class CommandLineClient {

    private String hostUri;
    private String serviceName;
    private String mapLaneName;
    private SwimClient swimClient;

    private Map<String, MapDownlink> downlinksPerServiceId;

    CommandLineClient(String hostUri, String serviceName, String mapLaneName) {

        this.hostUri = hostUri;
        this.serviceName = serviceName;
        this.mapLaneName = mapLaneName;

        this.downlinksPerServiceId = new HashMap<>();

        this.swimClient = new SwimClient();

        swimClient.start();
    }

    void run() {

        commandLineLoop();

        swimClient.stop();
    }

    private void open(String commaSeparatedArguments) throws UserErrorException {

        String serviceId = Util.getArg(0, commaSeparatedArguments, 1);

        if (downlinksPerServiceId.containsKey(serviceId)) {

            throw new UserErrorException("downlink to " + toMapLaneUri(serviceId) + " already open");
        }

        MapDownlink downlink =
                swimClient.
                        downlinkMap().
                        hostUri(hostUri).
                        nodeUri(toNodeUri(serviceId)).
                        laneUri(mapLaneName);


        downlink.open();

        //
        // remember it
        //

        downlinksPerServiceId.put(serviceId, downlink);

        System.out.println("> downlink to " + toMapLaneUri(serviceId) + " opened");

//        link.didLink(() -> {
//
//            System.out.println("linked");
//
//            link.put(1L, Value.of("blah"));
//        });

    }

    private void put(String spaceSeparatedArguments) throws UserErrorException {

        if (downlinksPerServiceId.isEmpty()) {

            throw new UserErrorException("no open downlink available, use 'open <service-id>' first");
        }

        List<String> args = Util.toArgList(spaceSeparatedArguments);

        String serviceId = null;
        String key;
        String value;

        if (args.size() < 2 || args.size() > 3) {

            throw new UserErrorException("invalid put command, use 'put [service-id] <key> <value>'");
        }

        if (args.size() == 2) {

            key = args.get(0);
            value = args.get(1);
        }
        else {

            serviceId = args.get(0);
            key = args.get(1);
            value = args.get(2);

        }

        if (downlinksPerServiceId.size() > 1 && serviceId == null) {

            throw new UserErrorException(
                    "more than one downlink currently opened, use 'put <service-id> <key> <value>' syntax");
        }

        MapDownlink downlink =
                serviceId == null ?
                        downlinksPerServiceId.values().iterator().next() :
                        downlinksPerServiceId.get(serviceId);

        if (downlink == null) {

            throw new UserErrorException("no downlink opened for service ID \"" + serviceId + "\"");
        }

        downlink.put(Value.of(key), Value.of(value));
    }

    private void close(String spaceSeparatedArguments) throws UserErrorException {

        if (downlinksPerServiceId.isEmpty()) {

            throw new UserErrorException("no downlinks opened");
        }

        List<String> args = Util.toArgList(spaceSeparatedArguments);

        String serviceId = null;

        if (args.size() > 0) {

            serviceId = args.get(0);
        }

        MapDownlink downlink = null;

        if (serviceId == null) {

            if (downlinksPerServiceId.size() > 1) {

                throw new UserErrorException("more than one downlinks opened, specify the corresponding service ID");
            }

            serviceId = downlinksPerServiceId.keySet().iterator().next();
            downlink = downlinksPerServiceId.get(serviceId);
        }
        else {

            downlink = downlinksPerServiceId.get(serviceId);

            if (downlink == null) {

                throw new UserErrorException("no downlink open for service " + serviceId);
            }
        }

        downlink.close();
        downlinksPerServiceId.remove(serviceId);
    }

    private void commandLineLoop() {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            boolean keepRunning = true;

            while(keepRunning) {

                System.out.print("> ");
                String line = br.readLine();

                try {

                    keepRunning = processCommandLine(line);
                }
                catch(UserErrorException e) {

                    System.out.println("> [error]: " + e.getMessage());
                }
            }
        }
        catch(IOException e) {

            System.err.println("> [error]: command line loop failed");
        }
    }

    /**
     * @return true if we should keep the command line loop running, false otherwise.
     */
    private boolean processCommandLine(String line) throws UserErrorException {

        line = line.trim();

        if ("exit".equals(line)) {

            return false;
        }

        if (line.startsWith("open")) {

            open(line.substring("open".length()).trim());
        }
        else if (line.startsWith("put")) {

            put(line.substring("put".length()).trim());
        }
        else if (line.startsWith("close")) {

            close(line.substring("close".length()).trim());
        }
        else if (!line.isEmpty()) {

            throw new UserErrorException("unknown command \"" + line + "\"");
        }

        return true;
    }

    private String toMapLaneUri(String serviceId) {

        return hostUri + "/" + toNodeUri(serviceId) + "/" + mapLaneName;
    }

    private String toNodeUri(String serviceId) {

        return serviceName + "/" + serviceId;
    }

}
