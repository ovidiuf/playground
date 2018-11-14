package playground.json.jackson.jsonFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BusinessPayload {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date expiresAt;

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }
}
