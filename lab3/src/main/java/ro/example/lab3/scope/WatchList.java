package ro.example.lab3.scope;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ro.example.lab3.model.Movie;

import java.util.ArrayList;
import java.util.List;

// Lista de filme pe care un utilizator vrea sa le vada.
@Component
@SessionScope
public class WatchList {

    private final List<Movie> movies = new ArrayList<>();

    public WatchList() {
        System.out.println("   [Spring] WatchList created");
    }

    public void add(Movie movie) {
        movies.add(movie);
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
