package ro.example.lab2.exercise1.repository;

import org.springframework.stereotype.Component;
import ro.example.lab2.exercise1.model.Movie;

import java.util.List;

// Programul nu se schimba (imutabil): poate fi folosit de toata aplicatia.
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

    public Movie findByTitle(String title) {
        return movies.stream()
                .filter(movie -> movie.title().equalsIgnoreCase(title))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown movie: " + title));
    }
}
