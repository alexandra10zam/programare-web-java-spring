package ro.example.lab6.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.example.lab6.model.Screening;
import ro.example.lab6.service.MovieService;
import ro.example.lab6.service.ScreeningService;

import java.util.List;

// Din Laboratorul 5, scris acum cu exceptii: fara if-uri pentru 404.
// Daca filmul sau proiectia nu exista, getById arunca ResourceNotFoundException.
@RestController
@RequestMapping("/api/movies/{movieId}/screenings")
public class ScreeningRestController {

    private final MovieService movieService;
    private final ScreeningService screeningService;

    public ScreeningRestController(MovieService movieService, ScreeningService screeningService) {
        this.movieService = movieService;
        this.screeningService = screeningService;
    }

    @GetMapping
    public List<Screening> getScreenings(@PathVariable Long movieId) {
        movieService.getById(movieId);
        return screeningService.findByMovie(movieId);
    }

    @GetMapping("/{screeningId}")
    public Screening getScreening(@PathVariable Long movieId, @PathVariable Long screeningId) {
        return screeningService.getById(movieId, screeningId);
    }
}
