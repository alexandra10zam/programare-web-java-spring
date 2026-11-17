package ro.example.lab6.exception;

// Aruncata cand clientul cere mai multe locuri decat mai sunt la proiectie.
public class NotEnoughSeatsException extends RuntimeException {

    public NotEnoughSeatsException(String message) {
        super(message);
    }
}
