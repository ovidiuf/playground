package playground.time.iso8601;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Main {

    public static void main(String[] args) {

        Date date = new Date();

        SimpleDateFormat ISO8601_LOCAL_TIME_ZONE = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");

        System.out.println(ISO8601_LOCAL_TIME_ZONE.format(date));

        SimpleDateFormat ISO8601_UTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");

        TimeZone z = TimeZone.getTimeZone("UTC");

        ISO8601_UTC.setTimeZone(z);

        System.out.println(ISO8601_UTC.format(date));
    }
}
