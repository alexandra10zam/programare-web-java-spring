package ro.example.lab3.model;

public class Movie {

    private Long id;
    private String title;
    private String genre;
    private double price;

    public Movie(Long id, String title, String genre, double price) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public double getPrice() {
        return price;
    }
}
