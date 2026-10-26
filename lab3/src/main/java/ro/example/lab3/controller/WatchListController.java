package ro.example.lab3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ro.example.lab3.scope.RequestInfo;
import ro.example.lab3.scope.VisitCounter;
import ro.example.lab3.scope.WatchList;
import ro.example.lab3.service.MovieService;

// Controller-ul este SINGLETON. Bean-urile din pachetul scope sunt injectate in el.
@Controller
public class WatchListController {

    private final MovieService movieService;
    private final WatchList watchList;
    private final RequestInfo requestInfo;
    private final VisitCounter visitCounter;

    public WatchListController(MovieService movieService, WatchList watchList,
                               RequestInfo requestInfo, VisitCounter visitCounter) {
        this.movieService = movieService;
        this.watchList = watchList;
        this.requestInfo = requestInfo;
        this.visitCounter = visitCounter;
    }

    @GetMapping("/watchlist")
    public String showWatchList(Model model) {
        model.addAttribute("visits", visitCounter.increment());
        model.addAttribute("requestId", requestInfo.getId());
        model.addAttribute("watchList", watchList.getMovies());
        model.addAttribute("movies", movieService.getAllMovies());
        return "watchlist";
    }

    @PostMapping("/watchlist")
    public String addToWatchList(@RequestParam Long movieId) {
        watchList.add(movieService.findById(movieId));
        return "redirect:/watchlist";
    }
}
