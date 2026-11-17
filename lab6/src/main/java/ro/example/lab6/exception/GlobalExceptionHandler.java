package ro.example.lab6.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.example.lab6.model.ErrorResponse;

import java.util.stream.Collectors;

// TODO 3.1: Adnotati clasa cu @RestControllerAdvice.
//   Metodele din aceasta clasa vor trata exceptiile aruncate de ORICE controller.
public class GlobalExceptionHandler {

    // TODO 3.2: Adnotati metoda cu @ExceptionHandler(ResourceNotFoundException.class) si returnati:
    //   ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage())
    // TODO 4.1: Schimbati tipul returnat in ResponseEntity<ErrorResponse> si adaugati parametrul
    //   HttpServletRequest request. Body-ul devine:
    //   new ErrorResponse(404, "Not Found", ex.getMessage(), request.getRequestURI())
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return null;
    }

    // TODO 3.3: Adnotati metoda cu @ExceptionHandler(NotEnoughSeatsException.class)
    //   si returnati 409 Conflict cu mesajul exceptiei (HttpStatus.CONFLICT).
    // TODO 4.1: La fel ca mai sus: ResponseEntity<ErrorResponse>, cu 409 si "Conflict".
    public ResponseEntity<String> handleNotEnoughSeats(NotEnoughSeatsException ex) {
        return null;
    }

    // TODO 4.2: Adnotati metoda cu @ExceptionHandler(MethodArgumentNotValidException.class).
    //   Construiti mesajul din erorile fiecarui camp:
    //   String message = ex.getBindingResult().getFieldErrors().stream()
    //           .map(error -> error.getField() + ": " + error.getDefaultMessage())
    //           .collect(Collectors.joining("; "));
    //   si returnati 400 Bad Request cu new ErrorResponse(400, "Bad Request", message, request.getRequestURI())
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return null;
    }

    // TODO 4.3 (Bonus): Adnotati metoda cu @ExceptionHandler(HttpMessageNotReadableException.class)
    //   si returnati 400 Bad Request cu mesajul "Body-ul cererii nu este un JSON valid".
    public ResponseEntity<ErrorResponse> handleInvalidJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return null;
    }
}
