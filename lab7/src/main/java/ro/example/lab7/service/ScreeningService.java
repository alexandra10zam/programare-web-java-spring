package ro.example.lab7.service;

import org.springframework.stereotype.Service;
import ro.example.lab7.model.Screening;
import ro.example.lab7.repository.ScreeningRepository;

import java.util.List;

@Service
public class ScreeningService {

    private final MovieService movieService;
    private final ScreeningRepository screeningRepository;

    public ScreeningService(MovieService movieService, ScreeningRepository screeningRepository) {
        this.movieService = movieService;
        this.screeningRepository = screeningRepository;
    }

    // 404 daca filmul nu exista (getById arunca exceptia), altfel proiectiile lui.
    public List<Screening> getByMovie(Long movieId) {
        movieService.getById(movieId);
        return screeningRepository.findByMovie(movieId);
    }
}
