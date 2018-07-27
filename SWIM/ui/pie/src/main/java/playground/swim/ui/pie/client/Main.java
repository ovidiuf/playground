package playground.swim.ui.pie.client;

import recon.Attr;
import recon.Record;
import recon.Value;

import swim.api.ValueDownlink;
import swim.client.SwimClient;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/23/18
 */
public class Main {

    public static void main(String[] args) throws Exception {

        String hostUri = "ws://localhost:9031";
        String nodeUri = "pie-service/1";
        String laneUri = "test-lane-0";
        String laneUri2 = "test-lane-1";

        final CommandLine commandLine = new CommandLine();

        final SwimClient swimClient = new SwimClient();

        swimClient.start();

        ValueDownlink<Value> link =
                swimClient.
                        downlinkValue().
                        hostUri(hostUri).
                        nodeUri(nodeUri).
                        laneUri(laneUri);

        link.didConnect(() -> commandLine.info("link to " + laneUri + " connected"));

        link.open();

        ValueDownlink<Value> link2 =
                swimClient.
                        downlinkValue().
                        hostUri(hostUri).
                        nodeUri(nodeUri).
                        laneUri(laneUri2);

        link2.didConnect(() ->  commandLine.info("link to " + laneUri2 + " connected"));

        link2.open();

        while(true) {

            try {

                String command = commandLine.readLine();
                command = command.trim();

                if (command.isEmpty()) {

                    commandLine.info("");
                }
                else if ("exit".equals(command)) {

                    swimClient.stop();
                    break;
                }
                else if ("help".equals(command)) {

                    displayHelp(commandLine);
                }
                else {

                    executeCommand(command, link, link2);
                }
            }
            catch(UserErrorException e) {

                commandLine.error(e.getMessage());
            }
        }
    }

    private static void executeCommand(String command, ValueDownlink<Value> link, ValueDownlink<Value> link2)
            throws UserErrorException{

        String[] tokens = command.split(" ");

        Integer laneId = null;
        Integer metric1 = null;
        Integer metric2 = null;
        Integer metric3 = null;

        for(String t: tokens) {

            if (t.isEmpty()) {

                continue;
            }

            if (laneId == null) {

                laneId = Integer.parseInt(t);
            }
            else if (metric1 == null) {

                metric1 = Integer.parseInt(t);
            }
            else if (metric2 == null) {

                metric2 = Integer.parseInt(t);
            }
            else if (metric3 == null) {

                metric3 = Integer.parseInt(t);
            }
        }

        if (laneId == null) {

            return;
        }

        ValueDownlink<Value> currentLink;

        if (laneId == 0) {

            currentLink = link;
        }
        else if (laneId == 1) {

            currentLink = link2;
        }
        else {

            throw new UserErrorException("unknown lane ID (" + laneId + "), valid values are 0 and 1");
        }

        sendData(currentLink, metric1, metric2, metric3);
    }

    private static void sendData(ValueDownlink<Value> link, Integer metric1, Integer metric2, Integer metric3) {


        Record record = Record.of();

        if (metric1 != null) {

            record.add(Attr.of("metric1", metric1));
        }

        if (metric2 != null) {

            record.add(Attr.of("metric2", metric2));
        }

        if (metric3 != null) {

            record.add(Attr.of("metric3", metric3));
        }

        link.set(Value.of(record));
    }

    private static void displayHelp(CommandLine commandLine) {

        String help =
                "\n" +
                        "Commands:\n" +
                        "\n" +
                        "  help - this help\n" +
                        "\n" +
                        "  exit - close the client and exit\n" +
                        "\n" +
                        "  <lane-id> <metric1> [metric2] [metric3] - send up to three integer metrics to the\n" +
                        "         lane whose numeric id is specified as the first argument\n" +
                        "\n";

        commandLine.multiLineOutput(help);
    }
}
