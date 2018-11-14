package playground.json.jackson.fullDataBinding;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BusinessPayload {

    private Date expiresAt;

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }
}
