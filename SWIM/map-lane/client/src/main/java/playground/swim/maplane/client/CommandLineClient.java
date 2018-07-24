package playground.swim.maplane.client;

import recon.Value;

import java.io.IOException;
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
    private String serviceTypeName;
    private String mapLaneName;
    private SwimClient swimClient;

    private Map<String, MapDownlink> downlinksPerServiceId;

    private CommandLine commandLine;

    CommandLineClient(String hostUri, String serviceTypeName, String mapLaneName) {

        this.hostUri = hostUri;
        this.serviceTypeName = serviceTypeName;
        this.mapLaneName = mapLaneName;

        this.downlinksPerServiceId = new HashMap<>();

        this.swimClient = new SwimClient();

        this.commandLine = new CommandLine();

        swimClient.start();
    }

    void run() {

        commandLineLoop();

        swimClient.stop();
    }

    private void open(String commaSeparatedArguments) throws UserErrorException {

        final String serviceId = Util.getArg(0, commaSeparatedArguments, 1);

        if (downlinksPerServiceId.containsKey(serviceId)) {

            throw new UserErrorException("downlink to " + toMapLaneUri(serviceId) + " already open");
        }

        MapDownlink downlink =
                swimClient.
                        downlinkMap().
                        hostUri(hostUri).
                        nodeUri(toNodeUri(serviceId)).
                        laneUri(mapLaneName);


        //
        // register various callbacks
        //

        //noinspection unchecked
        downlink.
                didLink(() -> {

                    commandLine.info("service " + serviceTypeName + "/" + serviceId + "'s map lane link linked on thread " +
                            Thread.currentThread().getName());
                }).
                didUnlink(() -> {
                    commandLine.info("service " + serviceTypeName + "/" + serviceId + "'s map lane link unlinked on thread " +
                            Thread.currentThread().getName());
                }).
                didConnect(() -> {
                    commandLine.info("service " + serviceTypeName + "/" + serviceId + "'s map lane link connected on thread " +
                            Thread.currentThread().getName());
                }).
                didDisconnect(() -> {
                    commandLine.info("service " + serviceTypeName + "/" + serviceId + "'s map lane link disconnected on thread " +
                            Thread.currentThread().getName());
                }).
                didClear(() -> {
                    commandLine.info("service " + serviceTypeName + "/" + serviceId + "'s map lane link cleared on thread " +
                            Thread.currentThread().getName());
                }).
                didClose(() -> {
                    commandLine.info("service " + serviceTypeName + "/" + serviceId + "'s map lane link closed on thread " +
                            Thread.currentThread().getName());
                }).
                didDrop((int i) -> {
                    commandLine.info("service " + serviceTypeName + "/" + serviceId + "'s map lane link dropped (" + i + ") on thread " +
                            Thread.currentThread().getName());
                }).
                didFail((Throwable t) -> {
                    commandLine.info("service " + serviceTypeName + "/" + serviceId + "'s map lane link failed (" + t + ") on thread " +
                            Thread.currentThread().getName());
                }).
                didReceive((Value body) -> {
                    commandLine.info("service " + serviceTypeName + "/" + serviceId + "'s map lane link received body " + body + " on thread " +
                            Thread.currentThread().getName());
                }).
                didRemove((Object key, Object oldValue) -> {
                    commandLine.info("service " + serviceTypeName + "/" + serviceId + "'s map lane link removed key " +
                            ((Value)key).stringValue() + ", old value: " + ((Value)oldValue).stringValue() +
                            " on thread " + Thread.currentThread().getName());
                }).
                didSync(() -> {
                    commandLine.info("service " + serviceTypeName + "/" + serviceId + "'s map lane link synced");
                }).
                didUpdate((Object key, Object newValue, Object oldValue) -> {
                    commandLine.info("service " + serviceTypeName + "/" + serviceId + "'s map lane link updated key " +
                            ((Value)key).stringValue() + ", " + ((Value)oldValue).stringValue() +
                            " replaced by " + ((Value)newValue).stringValue() +
                            " on thread " + Thread.currentThread().getName());
                }).
                didTake((int upper) -> {
                    commandLine.info("service " + serviceTypeName + "/" + serviceId + "'s map lane link did take (" + upper + ") on thread " + Thread.currentThread().getName());
                });

        //
        // open the link
        //

        downlink.open();

        commandLine.info("downlink to " + toMapLaneUri(serviceId) + " opening initiated");

        //
        // remember it locally
        //

        downlinksPerServiceId.put(serviceId, downlink);
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

        try {

            commandLine.start();

            boolean keepRunning = true;

            while(keepRunning) {

                String line = commandLine.readLine();

                try {

                    keepRunning = processCommandLine(line);
                }
                catch(UserErrorException e) {

                    commandLine.error(e.getMessage());
                }
            }
        }
        catch(IOException e) {

            commandLine.error("command line loop failed");
        }
        finally {

            commandLine.close();
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

        return serviceTypeName + "/" + serviceId;
    }

}
