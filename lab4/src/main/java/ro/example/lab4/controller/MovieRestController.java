package ro.example.lab4.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.example.lab4.model.Movie;
import ro.example.lab4.service.MovieService;

import java.net.URI;
import java.util.List;

// @RestController = @Controller + @ResponseBody: ce intoarcem devine JSON, nu numele unui template.
@RestController
@RequestMapping("/api/movies")
public class MovieRestController {

    private final MovieService movieService;

    public MovieRestController(MovieService movieService) {
        this.movieService = movieService;
    }

    // ===== Exercitiul 1 - Resurse si GET =====

    // GET /api/movies si GET /api/movies?genre=Sci-Fi
    @GetMapping
    public List<Movie> getMovies(@RequestParam(required = false) String genre) {
        if (genre != null) {
            return movieService.findByGenre(genre);
        }
        return movieService.findAll();
    }

    // GET /api/movies/1 -> 200 OK, GET /api/movies/99 -> 404 Not Found (Exercitiul 3)
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
        Movie movie = movieService.findById(id);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movie);
    }

    // ===== Exercitiul 2 - CRUD =====

    // POST /api/movies -> 201 Created + header Location, sau 400 Bad Request (Exercitiul 3)
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        if (movie.getTitle() == null || movie.getTitle().isBlank() || movie.getPrice() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Movie saved = movieService.add(movie);
        return ResponseEntity.created(URI.create("/api/movies/" + saved.getId())).body(saved);
    }

    // PUT /api/movies/1 -> 200 OK, sau 404 Not Found (Exercitiul 3)
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        Movie updated = movieService.update(id, movie);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/movies/1 -> 204 No Content, sau 404 Not Found (Exercitiul 3)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        if (!movieService.delete(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
