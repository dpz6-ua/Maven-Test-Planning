package ppss.exceptions;

public class FailedNotifyException extends Exception {
    public FailedNotifyException() {}
    public FailedNotifyException(String message) {
      super(message);
    }
}
