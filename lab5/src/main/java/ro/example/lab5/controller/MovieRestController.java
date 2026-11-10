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

    // ===== Exercitiul 1 - Query params =====

    // TODO 1.4: Adaugati paginarea cu doi parametri optionali, cu valori implicite:
    //   @RequestParam(defaultValue = "0") int page
    //   @RequestParam(defaultValue = "10") int size
    //   Inlocuiti tot corpul metodei cu: return movieService.findPage(genre, page, size);
    // TODO 3.1: Schimbati tipul returnat in ResponseEntity<List<Movie>> si trimiteti in raspuns
    //   headerul X-Total-Count cu numarul total de filme, movieService.count(genre):
    //   return ResponseEntity.ok().header("X-Total-Count", String.valueOf(total)).body(movies);
    @GetMapping
    public List<Movie> getMovies(@RequestParam(required = false) String genre) {
        if (genre != null) {
            return movieService.findByGenre(genre);
        }
        return movieService.findAll();
    }

    // TODO 3.3: Trimiteti versiunea filmului ca ETag. Inlocuiti ultimul return cu:
    //   return ResponseEntity.ok().eTag(String.valueOf(movie.getVersion())).body(movie);
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
        Movie movie = movieService.findById(id);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movie);
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

    // ===== Exercitiul 2 - Request body =====

    // TODO 2.1: Adnotati metoda cu @PatchMapping("/{id}"), parametrul id cu @PathVariable
    //   si parametrul patch cu @RequestBody.
    //   Apelati movieService.patch(id, patch): null -> 404 Not Found, altfel 200 OK cu filmul.
    //   Apoi completati metoda patch din MovieService (TODO 2.2).
    // TODO 3.4: Adaugati parametrul @RequestHeader(value = "If-Match", required = false) String ifMatch.
    //   La inceputul metodei: cautati filmul cu movieService.findById(id) (null -> 404).
    //   Daca ifMatch nu este null si ifMatch.replace("\"", "") este diferit de
    //   String.valueOf(movie.getVersion()) -> ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build()
    public ResponseEntity<Movie> patchMovie(Long id, MoviePatch patch) {
        return null;
    }

    // TODO 2.3: Adnotati metoda cu @PostMapping("/search") si parametrul search cu @RequestBody.
    //   Returnati movieService.search(search).
    public List<Movie> searchMovies(MovieSearch search) {
        return List.of();
    }

    // ===== Exercitiul 3 - Headere =====

    // TODO 3.2: Doar administratorii pot sterge filme. Adaugati parametrul
    //   @RequestHeader(value = "X-Api-Key", required = false) String apiKey.
    //   Daca API_KEY.equals(apiKey) este false -> ResponseEntity.status(HttpStatus.FORBIDDEN).build()
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        if (!movieService.delete(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
