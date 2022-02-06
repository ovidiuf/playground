package playground.smoke.model;

import java.util.UUID;

public class Status {

    private final String id;
    private final long timestamp;

    public Status() {
        this.id = UUID.randomUUID().toString();
        this.timestamp = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
