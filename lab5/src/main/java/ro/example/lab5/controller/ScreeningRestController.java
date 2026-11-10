package ro.example.lab5.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.example.lab5.model.Screening;
import ro.example.lab5.service.MovieService;
import ro.example.lab5.service.ScreeningService;

import java.util.List;

// Subresursa: proiectiile unui film. movieId face parte din URI-ul de baza.
@RestController
@RequestMapping("/api/movies/{movieId}/screenings")
public class ScreeningRestController {

    private final MovieService movieService;
    private final ScreeningService screeningService;

    public ScreeningRestController(MovieService movieService, ScreeningService screeningService) {
        this.movieService = movieService;
        this.screeningService = screeningService;
    }

    // GET /api/movies/1/screenings
    @GetMapping
    public ResponseEntity<List<Screening>> getScreenings(@PathVariable Long movieId) {
        // Daca filmul nu exista, nici proiectiile lui nu exista: 404, nu lista goala.
        if (movieService.findById(movieId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(screeningService.findByMovie(movieId));
    }

    // GET /api/movies/1/screenings/2
    @GetMapping("/{screeningId}")
    public ResponseEntity<Screening> getScreening(@PathVariable Long movieId, @PathVariable Long screeningId) {
        Screening screening = screeningService.findById(movieId, screeningId);
        if (screening == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(screening);
    }
}
