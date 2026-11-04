package ro.example.lab4.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.example.lab4.model.Movie;
import ro.example.lab4.service.MovieService;

import java.net.URI;
import java.util.List;

// TODO 1.1: Adnotati clasa cu @RestController si @RequestMapping("/api/movies").
//           Toate metodele de mai jos vor avea URI-ul de baza /api/movies.
public class MovieRestController {

    private final MovieService movieService;

    public MovieRestController(MovieService movieService) {
        this.movieService = movieService;
    }

    // ===== Exercitiul 1 - Resurse si GET =====

    // TODO 1.2: Adnotati metoda cu @GetMapping si returnati toate filmele (movieService.findAll()).
    // TODO 1.4: Adaugati parametrul @RequestParam(required = false) String genre.
    //           Daca genre nu este null, returnati movieService.findByGenre(genre).
    public List<Movie> getMovies() {
        return List.of();
    }

    // TODO 1.3: Adnotati metoda cu @GetMapping("/{id}") si parametrul id cu @PathVariable.
    //           Returnati movieService.findById(id).
    // TODO 3.1: Schimbati tipul returnat in ResponseEntity<Movie>.
    //           Filmul nu exista -> ResponseEntity.notFound().build()   (404)
    //           Filmul exista    -> ResponseEntity.ok(movie)            (200)
    public Movie getMovie(Long id) {
        return null;
    }

    // ===== Exercitiul 2 - CRUD =====

    // TODO 2.1: Adnotati metoda cu @PostMapping si parametrul movie cu @RequestBody.
    //           Salvati filmul cu movieService.add(movie) si returnati filmul salvat.
    // TODO 3.2: Schimbati tipul returnat in ResponseEntity<Movie>.
    //           Titlu lipsa/gol sau pret <= 0 -> ResponseEntity.badRequest().build()   (400)
    //           Altfel -> ResponseEntity.created(URI.create("/api/movies/" + saved.getId())).body(saved)   (201)
    public Movie createMovie(Movie movie) {
        return null;
    }

    // TODO 2.2: Adnotati metoda cu @PutMapping("/{id}"), parametrul id cu @PathVariable
    //           si parametrul movie cu @RequestBody. Returnati movieService.update(id, movie).
    // TODO 3.3: Schimbati tipul returnat in ResponseEntity<Movie>: 404 daca filmul nu exista, altfel 200.
    public Movie updateMovie(Long id, Movie movie) {
        return null;
    }

    // TODO 2.3: Adnotati metoda cu @DeleteMapping("/{id}") si parametrul id cu @PathVariable.
    //           Stergeti filmul cu movieService.delete(id).
    // TODO 3.4: Schimbati tipul returnat in ResponseEntity<Void>.
    //           Filmul nu exista -> ResponseEntity.notFound().build()    (404)
    //           Filmul a fost sters -> ResponseEntity.noContent().build() (204)
    public void deleteMovie(Long id) {
    }
}
