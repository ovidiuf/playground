package io.novaordis.playground.jee.ejb.mdb;

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

    // Static ----------------------------------------------------------------------------------------------------------

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
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------
}
