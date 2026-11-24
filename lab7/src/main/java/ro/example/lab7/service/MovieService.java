package ro.example.lab7.service;

import org.springframework.stereotype.Service;
import ro.example.lab7.exception.ResourceNotFoundException;
import ro.example.lab7.model.Movie;
import ro.example.lab7.repository.MovieRepository;

import java.util.List;

// Complet: logica de business. Datele vin acum din PostgreSQL, prin MovieRepository.
@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getMovies(String genre) {
        return genre == null ? movieRepository.findAll() : movieRepository.findByGenre(genre);
    }

    public Movie getById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filmul " + id + " nu exista"));
    }

    public Movie create(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie update(Long id, Movie movie) {
        if (movieRepository.update(id, movie) == 0) {
            throw new ResourceNotFoundException("Filmul " + id + " nu exista");
        }
        movie.setId(id);
        return movie;
    }

    public void delete(Long id) {
        if (movieRepository.deleteById(id) == 0) {
            throw new ResourceNotFoundException("Filmul " + id + " nu exista");
        }
    }
}
