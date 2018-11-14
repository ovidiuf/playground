package playground.json.jackson.jsonFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BusinessEvent {

    private String type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yy HH:mm:ssZ")
    private Date timestamp;

    private BusinessPayload payload;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public BusinessPayload getPayload() {
        return payload;
    }

    public void setPayload(BusinessPayload payload) {
        this.payload = payload;
    }

}
