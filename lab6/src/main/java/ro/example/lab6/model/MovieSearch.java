package ro.example.lab6.model;

import java.util.List;

// Criteriile cautarii POST /api/movies/search (din Laboratorul 5).
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
