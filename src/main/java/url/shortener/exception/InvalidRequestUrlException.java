package url.shortener.exception;

public class InvalidRequestUrlException extends RuntimeException {
    public InvalidRequestUrlException(String message) {
        super(message);
    }
}
