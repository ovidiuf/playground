package playground.swim.recon;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/17/18
 */
public class UserErrorException extends Exception {

    public UserErrorException(String msg) {
        super(msg);
    }

    public UserErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
