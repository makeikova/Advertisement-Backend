package by.bsu.advertisement.service.exception.jwt;

public class JWTTokenMissingException extends RuntimeException {

    public JWTTokenMissingException() {

    }

    public JWTTokenMissingException(String message) {
        super(message);
    }

    public JWTTokenMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public JWTTokenMissingException(Throwable cause) {
        super(cause);
    }
}
