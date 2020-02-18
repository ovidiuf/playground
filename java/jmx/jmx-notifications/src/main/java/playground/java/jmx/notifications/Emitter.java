package playground.java.jmx.notifications;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

@SuppressWarnings("WeakerAccess")
public class Emitter extends NotificationBroadcasterSupport {

   private long sequenceNumber = 0;

   private Color c;


   public void changeColor(Color c) {

      Color old = this.c;
      this.c = c;

      Notification n = new AttributeChangeNotification(
            this,
            sequenceNumber++,
            System.currentTimeMillis(),
            "Color changed",
            "Color",
            "Color",
            old, c);

      sendNotification(n);
   }

   @Override
   public MBeanNotificationInfo[] getNotificationInfo() {

      String[] types = new String[]{
            AttributeChangeNotification.ATTRIBUTE_CHANGE
      };
      String name = AttributeChangeNotification.class.getName();
      String description = "An attribute of this MBean has changed";
      MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);
      return new MBeanNotificationInfo[]{info};
   }
}
