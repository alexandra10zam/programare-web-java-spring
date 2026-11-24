package ro.example.lab7.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.example.lab7.model.Screening;
import ro.example.lab7.service.ScreeningService;

import java.util.List;

@RestController
@RequestMapping("/api/movies/{movieId}/screenings")
public class ScreeningRestController {

    private final ScreeningService screeningService;

    public ScreeningRestController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @GetMapping
    public List<Screening> getScreenings(@PathVariable Long movieId) {
        return screeningService.getByMovie(movieId);
    }
}
