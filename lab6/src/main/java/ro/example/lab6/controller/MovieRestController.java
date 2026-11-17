package ro.example.lab6.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.example.lab6.model.Movie;
import ro.example.lab6.model.MoviePatch;
import ro.example.lab6.model.MovieSearch;
import ro.example.lab6.service.MovieService;

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

    // ===== Exercitiul 1 - Validare =====

    // TODO 1.2: Adaugati @Valid inaintea lui @RequestBody, apoi STERGETI if-ul de mai jos:
    //   validarea o face acum Spring, pe baza adnotarilor din Movie.
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        if (movie.getTitle() == null || movie.getTitle().isBlank() || movie.getPrice() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Movie saved = movieService.add(movie);
        return ResponseEntity.created(URI.create("/api/movies/" + saved.getId())).body(saved);
    }

    // TODO 1.2: Adaugati @Valid si aici. In Laboratorul 5, un PUT doar cu pretul stergea titlul.
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        Movie updated = movieService.update(id, movie);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // ===== Exercitiul 3 - Exceptii in loc de if-uri =====

    // TODO 3.4: Inlocuiti findById si if-ul cu: Movie movie = movieService.getById(id);
    //   getById arunca ResourceNotFoundException, iar GlobalExceptionHandler o transforma in 404.
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

    // ===== Din Laboratorul 5 (complet) =====

    @GetMapping
    public ResponseEntity<List<Movie>> getMovies(@RequestParam(required = false) String genre,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        List<Movie> movies = movieService.findPage(genre, page, size);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(movieService.count(genre)))
                .body(movies);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Movie> patchMovie(@PathVariable Long id,
                                            @RequestHeader(value = "If-Match", required = false) String ifMatch,
                                            @RequestBody MoviePatch patch) {
        Movie movie = movieService.findById(id);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        if (ifMatch != null && !ifMatch.replace("\"", "").equals(String.valueOf(movie.getVersion()))) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
        Movie patched = movieService.patch(id, patch);
        return ResponseEntity.ok()
                .eTag(String.valueOf(patched.getVersion()))
                .body(patched);
    }

    @PostMapping("/search")
    public List<Movie> searchMovies(@RequestBody MovieSearch search) {
        return movieService.search(search);
    }

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
