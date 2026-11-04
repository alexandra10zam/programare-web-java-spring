package ro.example.lab4.service;

import org.springframework.stereotype.Service;
import ro.example.lab4.model.Movie;

import java.util.ArrayList;
import java.util.List;

// Serviciul este complet: in acest laborator lucram doar in MovieRestController.
// Datele sunt tinute in memorie, deci se pierd la repornirea aplicatiei.
@Service
public class MovieService {

    private final List<Movie> movies = new ArrayList<>();
    private long nextId = 5;

    public MovieService() {
        movies.add(new Movie(1L, "Dune", "Sci-Fi", 35));
        movies.add(new Movie(2L, "Oppenheimer", "Drama", 32));
        movies.add(new Movie(3L, "Barbie", "Comedy", 30));
        movies.add(new Movie(4L, "Interstellar", "Sci-Fi", 28));
    }

    public List<Movie> findAll() {
        return movies;
    }

    public List<Movie> findByGenre(String genre) {
        return movies.stream()
                .filter(movie -> movie.getGenre().equalsIgnoreCase(genre))
                .toList();
    }

    // Intoarce null daca nu exista un film cu acest id.
    public Movie findById(Long id) {
        return movies.stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Serverul alege id-ul noului film.
    public Movie add(Movie movie) {
        movie.setId(nextId++);
        movies.add(movie);
        return movie;
    }

    // Inlocuieste datele filmului. Intoarce null daca filmul nu exista.
    public Movie update(Long id, Movie data) {
        Movie movie = findById(id);
        if (movie == null) {
            return null;
        }
        movie.setTitle(data.getTitle());
        movie.setGenre(data.getGenre());
        movie.setPrice(data.getPrice());
        return movie;
    }

    // Intoarce true daca filmul a fost sters, false daca nu exista.
    public boolean delete(Long id) {
        return movies.removeIf(movie -> movie.getId().equals(id));
    }
}
