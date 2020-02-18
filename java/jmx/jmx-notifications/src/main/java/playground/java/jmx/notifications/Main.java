package playground.java.jmx.notifications;

public class Main {

    public static void main(String[] args) {

        ChangeListener cl = new ChangeListener();

        Emitter e = new Emitter();

        e.addNotificationListener(cl, null, "some handback");

        e.changeColor(Color.RED);
    }
}
