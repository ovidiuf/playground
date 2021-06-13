package playground.java.bytecode;

public class UserErrorException extends Exception {

    public UserErrorException(String s) {
        super(s);
    }

    public UserErrorException(String s, Exception cause) {
        super(s, cause);
    }
}
