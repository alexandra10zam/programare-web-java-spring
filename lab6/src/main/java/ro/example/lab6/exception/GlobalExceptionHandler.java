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

// Un singur loc care trateaza erorile pentru TOATE controller-ele: cross-cutting concern, ca un aspect.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404: filmul sau proiectia nu exista.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(404, "Not Found", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // 409: cererea e corecta ca forma, dar intra in conflict cu starea resursei (nu mai sunt locuri).
    @ExceptionHandler(NotEnoughSeatsException.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughSeats(NotEnoughSeatsException ex, HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(409, "Conflict", ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    // 400: un camp din body nu respecta constrangerile de validare (@Valid a esuat).
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        ErrorResponse body = new ErrorResponse(400, "Bad Request", message, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 400: body-ul nu este un JSON valid (Bonus).
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(400, "Bad Request", "Body-ul cererii nu este un JSON valid", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
