package ro.example.lab5.model;

import java.util.List;

// Criteriile unei cautari complexe, trimise in body la POST /api/movies/search.
// Ambele campuri sunt optionale.
public class MovieSearch {

    private List<String> genres;
    private Double maxPrice;

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
}
