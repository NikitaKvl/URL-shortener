package url.shortener.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import url.shortener.exception.InvalidRequestUrlException;
import url.shortener.exception.UrlNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({UrlNotFoundException.class})
    public ResponseEntity<Object> handleUrlNotFoundException(UrlNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler({InvalidRequestUrlException.class})
    public ResponseEntity<Object> handleInvalidRequestUrlException(InvalidRequestUrlException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}
