package ro.example.lab3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ro.example.lab3.service.MovieService;

@Controller
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public String showMovies(Model model) {

        // TODO 1.1: Obtineti lista filmelor din MovieService.
        // TODO 1.2: Adaugati lista in Model cu numele "movies".

        return "movies";   // numele template-ului: templates/movies.html
    }
}
