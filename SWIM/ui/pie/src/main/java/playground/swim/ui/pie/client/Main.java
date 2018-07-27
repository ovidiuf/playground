package playground.swim.ui.pie.client;

import recon.Attr;
import recon.Record;
import recon.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import swim.api.ValueDownlink;
import swim.client.SwimClient;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/23/18
 */
public class Main {


    private static final Pattern ALL_METRICS_PATTERN = Pattern.compile("^ *([0-9]+|-) +([0-9]+|-) +([0-9]+|-) *$");
    private static final Pattern INDIVIDUAL_METRIC_PATTERN = Pattern.compile("^ *([1234]): +([0-9]+) *$");

    public static void main(String[] args) throws Exception {

        String hostUri = "ws://localhost:9031";
        String nodeUri = "pie-service/1";
        String laneUri = "pie-value-lane";

        final CommandLine commandLine = new CommandLine();

        final SwimClient swimClient = new SwimClient();

        swimClient.start();

        ValueDownlink<Value> link =
                swimClient.
                        downlinkValue().
                        hostUri(hostUri).
                        nodeUri(nodeUri).
                        laneUri(laneUri);

        link.didConnect(() -> commandLine.info("link to " + hostUri + "/" + nodeUri + "/" + laneUri + " connected"));

        link.open();

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

                    executeCommand(commandLine, command, link);
                }
            }
            catch(UserErrorException e) {

                commandLine.error(e.getMessage());
            }
        }
    }

    private static void executeCommand(CommandLine commandLine, String command, ValueDownlink<Value> link)
            throws UserErrorException{

        Integer[] metrics = null;

        Matcher m = ALL_METRICS_PATTERN.matcher(command);

        if (m.matches()) {

            metrics = new Integer[3];

            for(int i = 0; i < 3; i ++) {

                String s = m.group(i + 1);

                if (!"-".equals(s)) {

                    metrics[i] = Integer.parseInt(s);
                }
            }
        }
        else {

            m = INDIVIDUAL_METRIC_PATTERN.matcher(command);

            if (m.matches()) {

                metrics = new Integer[3];

                String s = m.group(1);

                int metricId = Integer.parseInt(s);

                s = m.group(2);

                metrics[metricId - 1] = Integer.parseInt(s);
            }
        }

        if (metrics == null) {

            throw new UserErrorException("cannot understand the command line");
        }

        sendData(link, metrics);
    }

    private static void sendData(ValueDownlink<Value> link, Integer[] metrics) {

        Value oldRecord = link.get();

        Record record = Record.of();

        for(int i = 0; i < 3; i ++) {

            String name = "metric" + (i + 1);

            Integer m = metrics[i];

            if (m == null) {

                //
                // send the old value
                //

                if (!oldRecord.isAbsent()) {

                    Value v = oldRecord.getAttr(name);
                    m = v.intValue();
                }
            }

            record.add(Attr.of(name, m));
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
                        "  <metric1-value|-> <metric2-value|-> <metric3-value|-> - send all three integer metrics simultaneously.\n" +
                        "    To send less than a full set, use '-' for missing metrics.\n" +
                        "\n" +
                        "  <1-based-metricID:> <value>\n" +
                        "\n";

        commandLine.multiLineOutput(help);
    }
}
