package ro.example.lab5.service;

import org.springframework.stereotype.Service;
import ro.example.lab5.model.Screening;

import java.util.List;

@Service
public class ScreeningService {

    private final List<Screening> screenings = List.of(
            new Screening(1L, 1L, "18:00", "Sala 1", 40),
            new Screening(2L, 1L, "21:00", "Sala 2", 12),
            new Screening(3L, 2L, "19:30", "Sala 1", 0),
            new Screening(4L, 4L, "17:00", "Sala 3", 25),
            new Screening(5L, 5L, "16:00", "Sala 2", 30));

    public List<Screening> findByMovie(Long movieId) {
        return screenings.stream()
                .filter(screening -> screening.getMovieId().equals(movieId))
                .toList();
    }

    // Intoarce null daca proiectia nu exista SAU daca apartine altui film.
    public Screening findById(Long movieId, Long screeningId) {
        return screenings.stream()
                .filter(screening -> screening.getId().equals(screeningId))
                .filter(screening -> screening.getMovieId().equals(movieId))
                .findFirst()
                .orElse(null);
    }
}
