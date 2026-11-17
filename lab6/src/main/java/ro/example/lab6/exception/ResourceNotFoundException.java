package ro.example.lab6.exception;

// Aruncata cand clientul cere un film sau o proiectie care nu exista.
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
