package playground.java.jmx.notifications;

import javax.management.Notification;
import javax.management.NotificationListener;

public class ChangeListener implements NotificationListener {

   @Override public void handleNotification(Notification notification, Object handback) {

      System.out.println("received notification: " + notification + ", handback: " + handback);

   }
}
