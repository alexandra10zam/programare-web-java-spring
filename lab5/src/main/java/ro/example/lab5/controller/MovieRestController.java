package ro.example.lab5.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.example.lab5.model.Movie;
import ro.example.lab5.model.MoviePatch;
import ro.example.lab5.model.MovieSearch;
import ro.example.lab5.service.MovieService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieRestController {

    private static final String API_KEY = "cinema-admin";

    private final MovieService movieService;

    public MovieRestController(MovieService movieService) {
        this.movieService = movieService;
    }

    // ===== Exercitiul 1 + 3 - Query params si header in raspuns =====

    // GET /api/movies?genre=Sci-Fi&page=0&size=2
    // genre este optional; page si size au valori implicite.
    @GetMapping
    public ResponseEntity<List<Movie>> getMovies(@RequestParam(required = false) String genre,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        List<Movie> movies = movieService.findPage(genre, page, size);
        int total = movieService.count(genre);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(total))
                .body(movies);
    }

    // ===== Exercitiul 3 - ETag =====

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
        Movie movie = movieService.findById(id);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .eTag(String.valueOf(movie.getVersion()))
                .body(movie);
    }

    // ===== Din Laboratorul 4 (complet) =====

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        if (movie.getTitle() == null || movie.getTitle().isBlank() || movie.getPrice() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Movie saved = movieService.add(movie);
        // Headerul Location spune clientului unde gaseste resursa creata.
        return ResponseEntity.created(URI.create("/api/movies/" + saved.getId())).body(saved);
    }

    // PUT inlocuieste toata resursa: campurile care lipsesc din body devin null / 0.
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        Movie updated = movieService.update(id, movie);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // ===== Exercitiul 2 + 3 - PATCH, cu If-Match =====

    @PatchMapping("/{id}")
    public ResponseEntity<Movie> patchMovie(@PathVariable Long id,
                                            @RequestHeader(value = "If-Match", required = false) String ifMatch,
                                            @RequestBody MoviePatch patch) {
        Movie movie = movieService.findById(id);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        // Clientul a citit o versiune veche: altcineva a modificat filmul intre timp.
        if (ifMatch != null && !ifMatch.replace("\"", "").equals(String.valueOf(movie.getVersion()))) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
        Movie patched = movieService.patch(id, patch);
        return ResponseEntity.ok()
                .eTag(String.valueOf(patched.getVersion()))
                .body(patched);
    }

    // ===== Exercitiul 2 - Cautare complexa cu body =====

    // POST /api/movies/search - criteriile sunt prea complexe pentru query params.
    @PostMapping("/search")
    public List<Movie> searchMovies(@RequestBody MovieSearch search) {
        return movieService.search(search);
    }

    // ===== Exercitiul 3 - Header in cerere =====

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id,
                                            @RequestHeader(value = "X-Api-Key", required = false) String apiKey) {
        if (!API_KEY.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (!movieService.delete(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
