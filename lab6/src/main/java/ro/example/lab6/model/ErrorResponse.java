package ro.example.lab6.model;

import java.time.LocalDateTime;

// Corpul tuturor raspunsurilor de eroare ale API-ului (Exercitiul 4).
// Are aceleasi campuri ca raspunsul implicit al Spring Boot, prezentat la curs.
public class ErrorResponse {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
