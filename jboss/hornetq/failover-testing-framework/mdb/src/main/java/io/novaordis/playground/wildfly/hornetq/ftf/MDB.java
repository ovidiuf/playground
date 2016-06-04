package io.novaordis.playground.wildfly.hornetq.ftf;

import io.novaordis.playground.wildfly.hornetq.ftf.common.MessageInfo;
import io.novaordis.playground.wildfly.hornetq.ftf.common.MessageRecordingFacility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Message;
import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.jms.TextMessage;

/**
 * @author <a href="mailto:ovidiu@novardis.com">Ovidiu Feodorov</a>
 * @version <tt>$Revision: 1.2 $</tt>
 */
@MessageDriven(activationConfig =
        {
                @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
                @ActivationConfigProperty(propertyName="destination", propertyValue="queue/novaordis"),
//                @ActivationConfigProperty(propertyName="dLQMaxResent", propertyValue="10"),
//                @ActivationConfigProperty(propertyName="maxSession", propertyValue="10"),
//                @ActivationConfigProperty(propertyName="providerAdapterJNDI", propertyValue="java:/DefaultJMSProvider"),
//                @ActivationConfigProperty(propertyName="messageSelector", propertyValue="Persistent IS TRUE")
        })
//@PoolClass(value=StrictMaxPool.class, maxSize=1, timeout=10000)
//@ResourceAdapter(value="jms-ra.rar")
public class MDB implements MessageListener
{
    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(MDB.class);

    public static final String OUTPUT_FILE_PROPERTY_NAME="playground.failover.testing.framework.output.file";

    // Static ----------------------------------------------------------------------------------------------------------

    private static final MessageRecordingFacility mrf;

    static {

        String outputFile = System.getProperty(OUTPUT_FILE_PROPERTY_NAME);

        if (outputFile == null) {
            throw new RuntimeException(OUTPUT_FILE_PROPERTY_NAME + " not set");
        }

        try {
            mrf = new MessageRecordingFacility(outputFile);
            mrf.setAutoflush(true);

            log.info(mrf + " initialized");
        }
        catch(Exception e) {
            throw new RuntimeException("failed to initialize Message Recording Facility", e);
        }
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    public void onMessage(Message message)
    {

        String s = "> " + message;

        try {

            if (message instanceof TextMessage) {
                s += " " + ((TextMessage) message).getText();
            }
        }
        catch(JMSException e) {

            s += " extracting text from message produced an error: " + e.getMessage();
        }

        System.out.println(s);

        mrf.getQueue().add(new MessageInfo(message));
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------
}
