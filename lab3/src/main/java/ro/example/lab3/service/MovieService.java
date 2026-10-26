package ro.example.lab3.service;

import org.springframework.stereotype.Service;
import ro.example.lab3.model.Movie;

import java.util.List;

@Service
public class MovieService {

    private final List<Movie> movies = List.of(
            new Movie(1L, "Dune", "Sci-Fi", 35),
            new Movie(2L, "Oppenheimer", "Drama", 32),
            new Movie(3L, "Barbie", "Comedy", 30),
            new Movie(4L, "Inside Out", "Animation", 25));

    public List<Movie> getAllMovies() {
        return movies;
    }

    public Movie findById(Long id) {
        return movies.stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown movie: " + id));
    }
}
