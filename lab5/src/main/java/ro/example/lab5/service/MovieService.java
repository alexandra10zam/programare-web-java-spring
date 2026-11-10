package ro.example.lab5.service;

import org.springframework.stereotype.Service;
import ro.example.lab5.model.Movie;
import ro.example.lab5.model.MoviePatch;
import ro.example.lab5.model.MovieSearch;

import java.util.ArrayList;
import java.util.List;

// Datele sunt tinute in memorie, deci se pierd la repornirea aplicatiei.
@Service
public class MovieService {

    private final List<Movie> movies = new ArrayList<>();
    private long nextId = 7;

    public MovieService() {
        movies.add(new Movie(1L, "Dune", "Sci-Fi", 35));
        movies.add(new Movie(2L, "Oppenheimer", "Drama", 32));
        movies.add(new Movie(3L, "Barbie", "Comedy", 30));
        movies.add(new Movie(4L, "Interstellar", "Sci-Fi", 28));
        movies.add(new Movie(5L, "Inside Out 2", "Animation", 25));
        movies.add(new Movie(6L, "The Batman", "Action", 33));
    }

    public List<Movie> findAll() {
        return movies;
    }

    public List<Movie> findByGenre(String genre) {
        return movies.stream()
                .filter(movie -> genre.equalsIgnoreCase(movie.getGenre()))
                .toList();
    }

    // Pagina numarul page (de la 0), cu cel mult size filme. genre poate fi null.
    public List<Movie> findPage(String genre, int page, int size) {
        List<Movie> source = genre == null ? movies : findByGenre(genre);
        return source.stream()
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    // Numarul total de filme (pentru genul dat, sau toate daca genre este null).
    public int count(String genre) {
        return genre == null ? movies.size() : findByGenre(genre).size();
    }

    // Intoarce null daca nu exista un film cu acest id.
    public Movie findById(Long id) {
        return movies.stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Movie add(Movie movie) {
        movie.setId(nextId++);
        movie.setVersion(1);
        movies.add(movie);
        return movie;
    }

    // PUT: inlocuieste TOATE datele filmului cu cele primite. Intoarce null daca filmul nu exista.
    public Movie update(Long id, Movie data) {
        Movie movie = findById(id);
        if (movie == null) {
            return null;
        }
        movie.setTitle(data.getTitle());
        movie.setGenre(data.getGenre());
        movie.setPrice(data.getPrice());
        movie.setVersion(movie.getVersion() + 1);
        return movie;
    }

    // PATCH: modifica doar campurile trimise. Intoarce null daca filmul nu exista.
    public Movie patch(Long id, MoviePatch patch) {
        Movie movie = findById(id);
        if (movie == null) {
            return null;
        }
        if (patch.getTitle() != null) {
            movie.setTitle(patch.getTitle());
        }
        if (patch.getGenre() != null) {
            movie.setGenre(patch.getGenre());
        }
        if (patch.getPrice() != null) {
            movie.setPrice(patch.getPrice());
        }
        movie.setVersion(movie.getVersion() + 1);
        return movie;
    }

    // Cautare dupa mai multe genuri si un pret maxim. Criteriile null sunt ignorate.
    public List<Movie> search(MovieSearch search) {
        return movies.stream()
                .filter(movie -> search.getGenres() == null || search.getGenres().stream()
                        .anyMatch(genre -> genre.equalsIgnoreCase(movie.getGenre())))
                .filter(movie -> search.getMaxPrice() == null || movie.getPrice() <= search.getMaxPrice())
                .toList();
    }

    public boolean delete(Long id) {
        return movies.removeIf(movie -> movie.getId().equals(id));
    }
}
