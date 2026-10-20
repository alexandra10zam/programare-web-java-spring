package ro.example.cinema.repository;

import org.springframework.stereotype.Component;
import ro.example.cinema.model.Movie;

import java.util.List;

// The program never changes during the day (immutable): it can be shared by everyone.
@Component
public class MovieCatalog {

    private final List<Movie> movies = List.of(
            new Movie("Dune", 35),
            new Movie("Oppenheimer", 32),
            new Movie("Barbie", 30),
            new Movie("Inside Out", 25));

    public MovieCatalog() {
        System.out.println("   [Spring] MovieCatalog created");
    }

    public List<Movie> findAll() {
        return movies;
    }

    public Movie findByTitle(String title) {
        return movies.stream()
                .filter(movie -> movie.title().equalsIgnoreCase(title))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown movie: " + title));
    }
}
