package ro.example.lab5.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.example.lab5.model.Screening;
import ro.example.lab5.service.MovieService;
import ro.example.lab5.service.ScreeningService;

import java.util.List;

// TODO 1.1: Proiectiile sunt o subresursa a filmului. Adnotati clasa cu
//   @RestController si @RequestMapping("/api/movies/{movieId}/screenings").
public class ScreeningRestController {

    private final MovieService movieService;
    private final ScreeningService screeningService;

    public ScreeningRestController(MovieService movieService, ScreeningService screeningService) {
        this.movieService = movieService;
        this.screeningService = screeningService;
    }

    // TODO 1.2: Adnotati metoda cu @GetMapping si parametrul movieId cu @PathVariable.
    //   Filmul nu exista (movieService.findById(movieId) == null) -> 404 Not Found.
    //   Altfel -> 200 OK cu screeningService.findByMovie(movieId).
    public ResponseEntity<List<Screening>> getScreenings(Long movieId) {
        return null;
    }

    // TODO 1.3: Adnotati metoda cu @GetMapping("/{screeningId}") si AMBII parametri cu @PathVariable.
    //   screeningService.findById(movieId, screeningId): null -> 404 Not Found, altfel 200 OK.
    public ResponseEntity<Screening> getScreening(Long movieId, Long screeningId) {
        return null;
    }
}
